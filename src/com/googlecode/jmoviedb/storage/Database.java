/*
 * This file is part of JMoviedb.
 * 
 * Copyright (C) Tor Arne Lye torarnelye@gmail.com
 * 
 * JMoviedb is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMoviedb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.jmoviedb.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//import org.apache.derby.client.am.Connection;
//import org.apache.derby.client.am.PreparedStatement;
//import org.apache.derby.client.am.ResultSet;
//import org.apache.derby.client.am.Statement;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.*;
import com.googlecode.jmoviedb.model.movietype.*;

/**
 * This class handles all transactions to and from the DBMS.
 * @author Tor Arne Lye
 *
 */
public class Database /*extends Thread*/ {
	
	private String path;
	
	private int queryCount;
	
	private Connection connection = null;
	
	private PreparedStatement addMovieStatement;
	private PreparedStatement editMovieStatement;
	private PreparedStatement getMovieStatement1;
	private PreparedStatement deleteMovieStatement;
	private PreparedStatement getMovieList;
	private PreparedStatement updatePersonsStatement;
	private PreparedStatement clearActors;
	private PreparedStatement clearDirectors;
	private PreparedStatement clearWriters;
	private PreparedStatement addActor;
	private PreparedStatement addWriter;
	private PreparedStatement addDirector;
	private PreparedStatement getActors;
	private PreparedStatement getDirectors;
	private PreparedStatement getWriters;
	
	//TODO finish the javadoc description
	/**
	 * The default constructor. It opens a new database connection and, if 
	 * @param path the path to keep the database storage files. If database files already
	 * exist, they will be opened. Otherwise, new files are created. 
	 * @throws ClassNotFoundException if the Derby driver cannot be loaded
	 * @throws SQLException if Derby throws an exception
	 */
	public Database(String path) throws ClassNotFoundException, SQLException {
		this.path = path;
		queryCount = 0;
		System.out.println("Creating database @ " + path); 
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		connection = DriverManager.getConnection("jdbc:derby:"+path+";create=true");
		try {
			createTables();
		} catch (SQLException e) {
			/*If the SQLState equals "X0Y32", the exception is thrown because the tables
			 * already exist. This exception is ignored, all others are re-thrown.
			 */
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}
		
		addMovieStatement = connection.prepareStatement("INSERT INTO MOVIE (TYPE, IMDBID, TITLE, CUSTOMTITLE, MOVIEYEAR, RATING, " + 
				"PLOTOUTLINE, TAGLINE, COLOR, RUNTIME) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
		editMovieStatement = connection.prepareStatement("UPDATE MOVIE SET TYPE = ?, IMDBID = ?, TITLE = ?, CUSTOMTITLE = ?, " +
				"MOVIEYEAR = ?, RATING = ?, PLOTOUTLINE = ?, TAGLINE = ?, COLOR = ?, RUNTIME = ?" +
				"WHERE MOVIEID = ?");
		getMovieStatement1 = connection.prepareStatement("SELECT * FROM MOVIE WHERE MOVIEID = ?");
		deleteMovieStatement = connection.prepareStatement("DELETE FROM MOVIE WHERE MOVIEID = ?");
		getMovieList = connection.prepareStatement("SELECT * FROM MOVIE");
//		updatePersonsStatement = connection.prepareStatement("INSERT INTO PERSON VALUES (?, ?) IF NOT EXIST PERSONID = ?"); //TODO doesn't work. check sql syntax
		clearActors = connection.prepareStatement("DELETE FROM MOVIEACTOR WHERE MOVIEID = ?");
		clearDirectors = connection.prepareStatement("DELETE FROM MOVIEDIRECTOR WHERE MOVIEID = ?");
		clearWriters = connection.prepareStatement("DELETE FROM MOVIEWRITER WHERE MOVIEID = ?");
		addActor = connection.prepareStatement("INSERT INTO MOVIEACTOR VALUES (?, ?, ?)");
		addDirector = connection.prepareStatement("INSERT INTO MOVIEDIRECTOR VALUES (?, ?, ?)");
		addWriter = connection.prepareStatement("INSERT INTO MOVIEWRITER VALUES (?, ?, ?)");
//		getActors = connection.prepareStatement("SELECT * FROM MOVIEACTOR JOIN PERSON ON MOVIEACTOR.PERSONID = PERSON.PERSONID HAVING MOVIEID = ?");
//		getDirectors = connection.prepareStatement("SELECT * FROM MOVIEDIRECTOR JOIN PERSON WHERE MOVIEID = ?");
//		getWriters = connection.prepareStatement("SELECT * FROM MOVIEWRITER JOIN PERSON WHERE MOVIEID = ?");
	}
	
	private ResultSet execute(String query, boolean wantResultSet) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = null;
		queryCount++;
		System.out.println(queryCount + ": " + query);
		if(wantResultSet) {
			rs = statement.executeQuery(query);
//			rs.close();
		}
		else
			statement.executeUpdate(query); //TODO exception her når man åpner
		statement.close();
		return rs;
	}

	public void createTables() throws SQLException  {
		String person = "CREATE TABLE PERSON(" +
				"PERSONID CHAR(7) NOT NULL, " +
				"NAME VARCHAR(250), " +
				"PRIMARY KEY(PERSONID)" +
				")";
		String movie = "CREATE TABLE MOVIE(" +
				"MOVIEID INTEGER GENERATED ALWAYS AS IDENTITY, " +
				"TYPE SMALLINT, " +
				"IMDBID CHAR(7), " +
				"TITLE VARCHAR(250), " +
				"CUSTOMTITLE VARCHAR(250), " +
				"MOVIEYEAR SMALLINT, " +
				"RATING SMALLINT, " +
				"PLOTOUTLINE VARCHAR(10000), " +
				"TAGLINE VARCHAR(10000), " +
				"COLOR SMALLINT, " +
				"RUNTIME SMALLINT, " +
				"NOTES VARCHAR(10000), " +
				"VERSION SMALLINT, " +
				"CUSTOMFILMVERSION VARCHAR(100), " +
				"LEGAL SMALLINT, " +
				"SEEN SMALLINT, " +
				"FORMAT SMALLINT, " +
				"DISC SMALLINT, " +
				"VIDEO SMALLINT, " +
				"MYENCODE SMALLINT, " +
				"DVDREGION SMALLINT, " +
				"TVSYSTEM SMALLINT, " +
				"SCENERELEASENAME VARCHAR(250), " +
				"VIDEORESOLUTION SMALLINT, " +
				"VIDEOASPECT SMALLINT, " +
				"COVER BLOB(4M), " + 
				"PRIMARY KEY (MOVIEID)" +
				")";
		String movieActor = "CREATE TABLE MOVIEACTOR(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"CHARACTERDESCRIPTION VARCHAR(250), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " +
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" +
				")";
		String movieDirector = "CREATE TABLE MOVIEDIRECTOR(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " +
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" +
				")";
		String movieWriter = "CREATE TABLE MOVIEWRITER(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " +
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" +
				")";
		String movieGenre = "CREATE TABLE MOVIEGENRE(" +
				"MOVIEID INTEGER NOT NULL, " +
				"GENREID SMALLINT NOT NULL, " + 
				"PRIMARY KEY (MOVIEID, GENREID), " + 
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";
		String movieCountry = "CREATE TABLE MOVIECOUNTRY(" +
				"MOVIEID INTEGER NOT NULL, " +
				"COUNTRYID SMALLINT NOT NULL, " + 
				"PRIMARY KEY (MOVIEID, COUNTRYID), " + 
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";
		String movieLanguage = "CREATE TABLE MOVIELANGUAGE(" +
				"MOVIEID INTEGER NOT NULL, " +
				"LANGUAGEID SMALLINT NOT NULL, " + 
				"PRIMARY KEY (MOVIEID, LANGUAGEID), " + 
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";
		String movieAudio = "CREATE TABLE MOVIEAUDIO(" +
				"MOVIEID INTEGER NOT NULL, " +
				"TRACKNR SMALLINT NOT NULL, " +
				"CODEC SMALLINT, " +
				"LANGUAGEID SMALLINT, " +
				"COMMENTARY SMALLINT, " +
				"PRIMARY KEY (MOVIEID, TRACKNR), " + 
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";

		String movieSubtitle = "CREATE TABLE MOVIESUBTITLE(" +
				"MOVIEID INTEGER NOT NULL, " +
				"TRACKNR SMALLINT NOT NULL, " +
				"FORMAT SMALLINT, " +
				"LANGUAGEID SMALLINT, " +
				"COMMENTARY SMALLINT, " +
				"HEARINGIMPAIRED SMALLINT, " +
				"PRIMARY KEY (MOVIEID, TRACKNR), " + 
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";


		execute(movie, false);
		execute(person, false);
		execute(movieActor, false);
		execute(movieDirector, false);
		execute(movieWriter, false);
		execute(movieGenre, false);
		execute(movieCountry, false);
		execute(movieLanguage, false);
		execute(movieAudio, false);
		execute(movieSubtitle, false);
	}
	
//	public void deleteTables() throws SQLException {
//		execute("DROP TABLE MOVIEACTOR", false);
//		execute("DROP TABLE MOVIEDIRECTOR", false);
//		execute("DROP TABLE MOVIEWRITER", false);
//		execute("DROP TABLE MOVIE", false);
//		execute("DROP TABLE PERSON", false);
//	}
	
//	public void close() throws SQLException {
//		connection.close();
//	}
	
	public void shutdown() {
		try {
			addMovieStatement.close();
			getMovieStatement1.close();
			deleteMovieStatement.close();
			getMovieList.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		// TODO Auto-generated method stub
		
	}

	public void save(Moviedb db) {
		// TODO Auto-generated method stub
		
	}
	
	public HashMap<Integer, AbstractMovie> getMovieList() throws SQLException {
		ResultSet rs  = getMovieList.executeQuery();
		HashMap<Integer, AbstractMovie> hm = new HashMap<Integer, AbstractMovie>();
		
		while(rs.next()) {
			AbstractMovie m;
			switch(rs.getInt("TYPE")) {
				case 0:
					m = new Film(rs.getInt("MOVIEID"), 
							rs.getString("IMDBID"), 
							rs.getString("TITLE"),
							rs.getString("CUSTOMTITLE"),
							rs.getInt("MOVIEYEAR"),
							rs.getInt("RATING"),
							rs.getString("PLOTOUTLINE"),
							rs.getString("TAGLINE"),
							rs.getInt("COLOR"),
							rs.getInt("RUNTIME"),
							rs.getString("NOTES"),
							rs.getInt("VERSION"),
							rs.getString("CUSTOMFILMVERSION"),
							rs.getInt("LEGAL"),
							rs.getInt("SEEN"),
							rs.getInt("FORMAT"),
							rs.getInt("DISC"),
							rs.getInt("VIDEO"),
							rs.getInt("MYENCODE"),
							rs.getInt("DVDREGION"),
							rs.getInt("TVSYSTEM"),
							rs.getString("SCENERELEASENAME"),
							rs.getInt("VIDEORESOLUTION"),
							rs.getInt("VIDEOASPECT")
							);
//					m.setImageBytes(rs.getBlob("COVER").); //TODO finish
					break;
				case 1: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				case 2: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				case 3: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				case 4: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				case 5: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				case 6: m = getMovie(rs.getInt("MOVIEID")); System.out.println("Not implemented yet!"); break; //TODO
				default: 
					m = new Film();
					if(CONST.DEBUG_MODE)
						System.out.println("Invalid movie type: " + rs.getInt("TYPE"));
					break;
			}
			hm.put(rs.getInt("MOVIEID"), m);
		}
		
		rs.close();
		
		return hm;
	}
	
	public void editMovie(AbstractMovie m) throws SQLException {
		if(CONST.DEBUG_MODE)
			System.out.println("DATABASE: editMovie ID " + m.getID() + " Type " + MovieType.abstractMovieToInt(m));
		
		int type = MovieType.abstractMovieToInt(m);
		
		editMovieStatement.setInt(1, type);
		editMovieStatement.setString(2, m.getImdbID());
		editMovieStatement.setString(3, m.getTitle());
		editMovieStatement.setString(4, m.getCustomTitle());
		editMovieStatement.setInt(5, m.getYear());
		editMovieStatement.setInt(6, m.getRatingAsInt());
		editMovieStatement.setString(7, m.getPlotOutline());
		editMovieStatement.setString(8, m.getTagline());
		editMovieStatement.setInt(9, m.getColorInt());
		editMovieStatement.setInt(10, m.getRunTime());
		editMovieStatement.setInt(11, m.getID());
		editMovieStatement.execute();
	}
	
	public int addMovie(AbstractMovie m) throws SQLException {
		if(CONST.DEBUG_MODE)
			System.out.println("DATABASE: addMovie ID " + m.getID() + " Type " + MovieType.abstractMovieToInt(m));
		
		int type = MovieType.abstractMovieToInt(m);
		
		addMovieStatement.setInt(1, type);
		addMovieStatement.setString(2, m.getImdbID());
		addMovieStatement.setString(3, m.getTitle());
		addMovieStatement.setString(4, m.getCustomTitle());
		addMovieStatement.setInt(5, m.getYear());
		addMovieStatement.setInt(6, m.getRatingAsInt());
		addMovieStatement.setString(7, m.getPlotOutline());
		addMovieStatement.setString(8, m.getTagline());
		addMovieStatement.setInt(9, m.getColorInt());
		addMovieStatement.setInt(10, m.getRunTime());
		addMovieStatement.execute();

		ResultSet generatedKey = addMovieStatement.getGeneratedKeys();
		if(generatedKey.next()) {
			System.out.println("Generated key: " + generatedKey.getInt(1));
			return generatedKey.getInt(1);
		} else {
			throw new SQLException("Failed getting the autogenerated key");
		}
	}
	
	/**
	 * Checks the Actors, directors and writers for the movie and inserts them in the database
	 * if they're not there already.
	 * @param m
	 */
	private void updatePersons(AbstractMovie m) throws SQLException {
		clearActors.setInt(1, m.getID());
		clearDirectors.setInt(1, m.getID());
		clearWriters.setInt(1, m.getID());
		clearActors.execute();
		clearDirectors.execute();
		clearDirectors.execute();
		
		for(Person p : m.getDirectors()) { //TODO names are not updated if they change
			updatePersonsStatement.setString(1, p.getID());
			updatePersonsStatement.setString(2, p.getID());
			updatePersonsStatement.setString(3, p.getName());
			updatePersonsStatement.execute();
			
			addDirector.setInt(1, m.getID());
			addDirector.setString(2, p.getID());
			addDirector.setString(3, "");
			addDirector.execute();
		}
		
		for(Person p : m.getWriters()) { //TODO names are not updated if they change
			updatePersonsStatement.setString(1, p.getID());
			updatePersonsStatement.setString(2, p.getID());
			updatePersonsStatement.setString(3, p.getName());
			updatePersonsStatement.execute();
			
			addWriter.setInt(1, m.getID());
			addWriter.setString(2, p.getID());
			addWriter.setString(3, "");
			addWriter.execute();
		}
		
		for(ActorInfo a : m.getActors()) { //TODO names are not updated if they change
			updatePersonsStatement.setString(1, a.getPerson().getID());
			updatePersonsStatement.setString(2, a.getPerson().getID());
			updatePersonsStatement.setString(3, a.getPerson().getName());
			updatePersonsStatement.execute();
			
			addActor.setInt(1, m.getID());
			addActor.setString(2, a.getPerson().getID());
			addActor.setString(3, a.getCharacter());
			addActor.execute();
		}
		
	}
	
	/**
	 * Loads a movie from the database.
	 * @param id
	 * @return an instance of an AbstractMovie subclass
	 * @throws SQLException
	 */
	public AbstractMovie getMovie(int id) throws SQLException {
		getMovieStatement1.setInt(1, id);
		ResultSet rs = getMovieStatement1.executeQuery();
		if(!rs.next())
			return null;
		
		AbstractMovie m = MovieType.intToAbstractMovie(rs.getInt("TYPE"));
		if(m == null) {
			throw new SQLException("Invalid Type ID encountered in database: " + rs.getInt("TYPE"));
		}
		
		m.setID(rs.getInt("MOVIEID"));
		m.setImdbID(rs.getString("IMDBID"));
		m.setTitle(rs.getString("TITLE"));
		m.setCustomTitle(rs.getString("CUSTOMTITLE"));
		m.setYear(rs.getInt("MOVIEYEAR"));
		m.setRatingAsInt(rs.getInt("RATING"));
		m.setPlotOutline(rs.getString("PLOTOUTLINE"));
		m.setTagline(rs.getString("TAGLINE"));
		m.setColorInt(rs.getInt("COLOR"));
		m.setRunTime(rs.getInt("RUNTIME"));
		m.setSeen(rs.getInt("SEEN"));
		
		rs.close();
		return m;
	}
	
	public AbstractMovie getMovieFull(int id) throws SQLException {
		AbstractMovie m = getMovie(id);
		
		getActors.setInt(1, id);
		getDirectors.setInt(1, id);
		getWriters.setInt(1, id);
		
		ResultSet rsA = getActors.executeQuery();
		ArrayList<ActorInfo> actors = new ArrayList<ActorInfo>();
		int counter = 0;
		while(rsA.next()) {
			actors.add(new ActorInfo(counter, new Person(rsA.getString("PERSONID"), rsA.getString("NAME")), rsA.getString("CHARACTERDESCRIPTION")));
		}
		m.setActors(actors);
		rsA.close();
		
		ResultSet rsD = getDirectors.executeQuery();
		ArrayList<Person> directors = new ArrayList<Person>();
		while(rsD.next()) {
			directors.add(new Person(rsD.getString("PERSONID"), rsD.getString("NAME")));
		}
		m.setDirectors(directors);
		rsD.close();
		
		ResultSet rsW = getWriters.executeQuery();
		ArrayList<Person> writers = new ArrayList<Person>();
		while(rsW.next()) {
			writers.add(new Person(rsW.getString("PERSONID"), rsW.getString("NAME")));
		}
		m.setWriters(writers);
		rsW.close();
		
		return m;
	}
	
	public void deleteMovie(AbstractMovie m) throws SQLException {
		if(m.getID()!=-1) {
			deleteMovieStatement.setInt(1, m.getID());
			deleteMovieStatement.execute();
		}
	}
}
