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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//import org.apache.derby.client.am.Connection;
//import org.apache.derby.client.am.PreparedStatement;
//import org.apache.derby.client.am.ResultSet;
//import org.apache.derby.client.am.Statement;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.AspectRatio;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.DiscType;
import com.googlecode.jmoviedb.enumerated.FilmVersion;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.enumerated.Resolution;
import com.googlecode.jmoviedb.enumerated.TVsystem;
import com.googlecode.jmoviedb.enumerated.VideoCodec;
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
	private PreparedStatement addPersonStatement;
	private PreparedStatement updatePersonStatement;
	private PreparedStatement checkPersonStatement;
	private PreparedStatement clearActors;
	private PreparedStatement clearDirectors;
	private PreparedStatement clearWriters;
	private PreparedStatement clearGenres;
	private PreparedStatement clearLanguages;
	private PreparedStatement clearCountries;
	private PreparedStatement addActor;
	private PreparedStatement addWriter;
	private PreparedStatement addDirector;
	private PreparedStatement addGenre;
	private PreparedStatement addLanguage;
	private PreparedStatement addCountry;
	private PreparedStatement getActors;
	private PreparedStatement getDirectors;
	private PreparedStatement getWriters;
	private PreparedStatement getGenres;
	private PreparedStatement getLanguages;
	private PreparedStatement getCountries;
	
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
		
		//Make sure addMovieStatement and editMovieStatement have the same column names at all times
		addMovieStatement = connection.prepareStatement("INSERT INTO MOVIE (" +
				"TYPE, IMDBID, TITLE, CUSTOMTITLE, MOVIEYEAR, RATING, " + 
				"PLOTOUTLINE, TAGLINE, COLOR, RUNTIME, NOTES, VERSION, " +
				"CUSTOMFILMVERSION, LEGAL, SEEN, LOCATION, FORMAT, DISC, VIDEO, " +
				"MYENCODE, DVDREGION, TVSYSTEM, SCENERELEASENAME, " +
				"VIDEORESOLUTION, VIDEOASPECT, COVER) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?)", 
				PreparedStatement.RETURN_GENERATED_KEYS);
		editMovieStatement = connection.prepareStatement("UPDATE MOVIE SET " +
				"TYPE = ?, IMDBID = ?, TITLE = ?, CUSTOMTITLE = ?, " +
				"MOVIEYEAR = ?, RATING = ?, PLOTOUTLINE = ?, TAGLINE = ?, " +
				"COLOR = ?, RUNTIME = ?, NOTES = ?, VERSION = ?, " +
				"CUSTOMFILMVERSION = ?, LEGAL = ?, SEEN = ?, LOCATION = ?, FORMAT = ?, " +
				"DISC = ?, VIDEO = ?, MYENCODE = ?, DVDREGION = ?, TVSYSTEM = ?, " +
				"SCENERELEASENAME = ?, VIDEORESOLUTION = ?, VIDEOASPECT = ?, " +
				"COVER = ? " +
				"WHERE MOVIEID = ?");
		getMovieStatement1 = connection.prepareStatement("SELECT * FROM MOVIE WHERE MOVIEID = ?");
		deleteMovieStatement = connection.prepareStatement("DELETE FROM MOVIE WHERE MOVIEID = ?");
		getMovieList = connection.prepareStatement("SELECT * FROM MOVIE");
		addPersonStatement = connection.prepareStatement("INSERT INTO PERSON VALUES (?, ?)");
		updatePersonStatement = connection.prepareStatement("UPDATE PERSON SET NAME = ? WHERE PERSONID = ?");
		checkPersonStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSONID = ?");
		clearActors = connection.prepareStatement("DELETE FROM MOVIEACTOR WHERE MOVIEID = ?");
		clearDirectors = connection.prepareStatement("DELETE FROM MOVIEDIRECTOR WHERE MOVIEID = ?");
		clearWriters = connection.prepareStatement("DELETE FROM MOVIEWRITER WHERE MOVIEID = ?");
		clearGenres = connection.prepareStatement("DELETE FROM MOVIEGENRE WHERE MOVIEID = ?");
		clearCountries = connection.prepareStatement("DELETE FROM MOVIECOUNTRY WHERE MOVIEID = ?");
		clearLanguages = connection.prepareStatement("DELETE FROM MOVIELANGUAGE WHERE MOVIEID = ?");
		addActor = connection.prepareStatement("INSERT INTO MOVIEACTOR VALUES (?, ?, ?)");
		addDirector = connection.prepareStatement("INSERT INTO MOVIEDIRECTOR VALUES (?, ?, ?)");
		addWriter = connection.prepareStatement("INSERT INTO MOVIEWRITER VALUES (?, ?, ?)");
		addGenre = connection.prepareStatement("INSERT INTO MOVIEGENRE VALUES(?, ?)");
		addLanguage = connection.prepareStatement("INSERT INTO MOVIELANGUAGE VALUES(?, ?)");
		addCountry = connection.prepareStatement("INSERT INTO MOVIECOUNTRY VALUES(?, ?)");
		getActors = connection.prepareStatement("SELECT * FROM MOVIEACTOR JOIN PERSON ON MOVIEACTOR.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getDirectors = connection.prepareStatement("SELECT * FROM MOVIEDIRECTOR JOIN PERSON ON MOVIEDIRECTOR.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getWriters = connection.prepareStatement("SELECT * FROM MOVIEWRITER JOIN PERSON ON MOVIEWRITER.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getGenres = connection.prepareStatement("SELECT * FROM MOVIEGENRE WHERE MOVIEID = ?");
		getLanguages = connection.prepareStatement("SELECT * FROM MOVIELANGUAGE WHERE MOVIEID = ?");
		getCountries = connection.prepareStatement("SELECT * FROM MOVIECOUNTRY WHERE MOVIEID = ?");
	}
	
	//TODO remove this?
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
				"LOCATION VARCHAR(150), " +
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
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " + //Probably remove cascade
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" + //Probably remove cascade
				")";
		String movieDirector = "CREATE TABLE MOVIEDIRECTOR(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " + //Probably remove cascade
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" + //Probably remove cascade
				")";
		String movieWriter = "CREATE TABLE MOVIEWRITER(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " + //Probably remove cascade
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" + //Probably remove cascade
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
	
	public HashMap<Integer, AbstractMovie> getMovieList() throws SQLException, IOException {
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
	
	public void saveMovie(AbstractMovie m) throws SQLException {
		if(CONST.DEBUG_MODE)
			System.out.println("DATABASE: saveMovie ID " + m.getID() + " Type " + MovieType.abstractMovieToInt(m));
		
		int type = MovieType.abstractMovieToInt(m);
		PreparedStatement statement;
		boolean edit;
		
		if(m.getID() == -1) {
			statement = addMovieStatement;
			edit = false;
		} else {
			statement = editMovieStatement;
			edit = true;
		}
		
		statement.setInt(1, type);
		statement.setString(2, m.getImdbID());
		statement.setString(3, m.getTitle());
		statement.setString(4, m.getCustomTitle());
		statement.setInt(5, m.getYear());
		statement.setInt(6, m.getRatingAsInt());
		statement.setString(7, m.getPlotOutline());
		statement.setString(8, m.getTagline());
		statement.setInt(9, m.getColorInt());
		statement.setInt(10, m.getRunTime());
		statement.setString(11, m.getNotes());
		statement.setInt(12, m.getVersion().getID());
		statement.setString(13, m.getCustomVersion());
		statement.setInt(14, CONST.booleanToInt(m.isLegal()));
		statement.setInt(15, CONST.booleanToInt(m.isSeen()));
		statement.setString(16, ""); //TODO FIX!
		statement.setInt(17, m.getFormat().getID());
		statement.setInt(18, m.getDisc().getID());
		statement.setInt(19, m.getVideo().getID());
		statement.setInt(20, CONST.booleanToInt(m.isMyEncode()));
		statement.setInt(21, m.getDvdRegionAsInt());
		statement.setInt(22, m.getTvSystem().getID());
		statement.setString(23, m.getSceneReleaseName());
		statement.setInt(24, m.getResolution().getID());
		statement.setInt(25, m.getAspectRatio().getID());
		
		if(m.getImageBytes() != null)
			statement.setBinaryStream(26, new ByteArrayInputStream(m.getImageBytes()));
		else
			statement.setNull(26, Types.BLOB);
		
		if(edit)
			statement.setInt(27, m.getID());
		
		statement.execute();
		
		updateRelations(m);
		
		if(!edit) {
			ResultSet generatedKey = statement.getGeneratedKeys();
			if(generatedKey.next()) {
				System.out.println("DATABASE: Generated key: " + generatedKey.getInt(1));
//				return generatedKey.getInt(1);
				m.setID(generatedKey.getInt(1));
			} else {
				throw new SQLException("DATABASE: Failed getting the autogenerated key");
			}
		}
	}
	
	/**
	 * Checks the Actors, directors and writers for the movie and inserts them in the database
	 * if they're not there already.
	 * @param m
	 */
	private void updateRelations(AbstractMovie m) throws SQLException {
		
		clearDirectors.setInt(1, m.getID());
		clearDirectors.execute();
		
		for(Person p : m.getDirectors()) {
			addOrUpdatePerson(p);
			
			addDirector.setInt(1, m.getID());
			addDirector.setString(2, p.getID());
			addDirector.setString(3, "");
			addDirector.execute();
		}
		
		clearWriters.setInt(1, m.getID());
		clearWriters.execute();
		
		for(Person p : m.getWriters()) {
			addOrUpdatePerson(p);
			
			addWriter.setInt(1, m.getID());
			addWriter.setString(2, p.getID());
			addWriter.setString(3, "");
			addWriter.execute(); //TODO fix cases where a writer or director is listed twice
		}
		
		clearActors.setInt(1, m.getID());
		clearActors.execute();
		
		for(ActorInfo a : m.getActors()) {
			addOrUpdatePerson(a.getPerson());
			
			addActor.setInt(1, m.getID());
			addActor.setString(2, a.getPerson().getID());
			addActor.setString(3, a.getCharacter());
			addActor.execute();
		}

		clearGenres.setInt(1, m.getID());
		clearGenres.execute();
		
		for(Genre g : m.getGenres()) {
			addGenre.setInt(1, m.getID());
			addGenre.setInt(2, g.getID());
			addGenre.execute();
		}
		
		clearLanguages.setInt(1, m.getID());
		clearLanguages.execute();
		
		for(Language l : m.getLanguages()) {
			addLanguage.setInt(1, m.getID());
			addLanguage.setInt(2, l.getID());
			addLanguage.execute();
		}
		
		clearCountries.setInt(1, m.getID());
		clearCountries.execute();
		
		for(Country c : m.getCountries()) {
			addCountry.setInt(1, m.getID());
			addCountry.setInt(2, c.getID());
			addCountry.execute();
		}
		
	}
	
	/**
	 * Loads a movie from the database.
	 * @param id
	 * @return an instance of an AbstractMovie subclass
	 * @throws SQLException
	 */
	public AbstractMovie getMovie(int id) throws SQLException, IOException {
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
		m.setNotes(rs.getString("NOTES"));
		m.setVersion(FilmVersion.intToEnum(rs.getInt("VERSION")));
		m.setCustomVersion(rs.getString("CUSTOMFILMVERSION"));
		m.setLegal(rs.getInt("LEGAL"));
		m.setSeen(rs.getInt("SEEN"));
		String foo = rs.getString("LOCATION"); //TODO fix!
		m.setFormat(FormatType.intToEnum(rs.getInt("FORMAT")));
		m.setDisc(DiscType.intToEnum(rs.getInt("DISC")));
		m.setVideo(VideoCodec.intToEnum(rs.getInt("VIDEO")));
		m.setMyEncode(rs.getInt("MYENCODE"));
		m.setDvdRegionAsInt(rs.getInt("DVDREGION"));
		m.setTvSystem(TVsystem.intToEnum(rs.getInt("TVSYSTEM")));
		m.setSceneReleaseName(rs.getString("SCENERELEASENAME"));
		m.setResolution(Resolution.intToEnum(rs.getInt("VIDEORESOLUTION")));
		m.setAspectRatio(AspectRatio.intToEnum(rs.getInt("VIDEOASPECT")));
		
		InputStream stream = rs.getBinaryStream("COVER");
		if(stream != null) {
			byte[] imageBytes = new byte[0];
			while(stream.available() > 0) { //loop while there is more data to read

				//how much data is ready right now?
				int readLength = stream.available();

				//create a new array that contains the bytes read until now, but with
				//free space for more
				byte[] newBytes = Arrays.copyOf(imageBytes, imageBytes.length+readLength);

				//read the new bytes into the empty part of the newly created array
				stream.read(newBytes, imageBytes.length, readLength);

				//replace the "old" byte array with the new one
				imageBytes = newBytes;
			}
			stream.close();
			
			if(CONST.isValidImage(imageBytes))
				m.setImageBytes(imageBytes);
		}
		rs.close();

		return m;
	}
	
	public AbstractMovie getMovieFull(int id) throws SQLException, IOException {
		AbstractMovie m = getMovie(id);
				
		getActors.setInt(1, id);
		ResultSet rsA = getActors.executeQuery();
		ArrayList<ActorInfo> actors = new ArrayList<ActorInfo>();
		int counter = 0;
		while(rsA.next())
			actors.add(new ActorInfo(counter, new Person(rsA.getString("PERSONID"), rsA.getString("NAME")), rsA.getString("CHARACTERDESCRIPTION")));
		m.setActors(actors);
		rsA.close();
		
		getDirectors.setInt(1, id);
		ResultSet rsD = getDirectors.executeQuery();
		ArrayList<Person> directors = new ArrayList<Person>();
		while(rsD.next())
			directors.add(new Person(rsD.getString("PERSONID"), rsD.getString("NAME")));
		m.setDirectors(directors);
		rsD.close();
		
		getWriters.setInt(1, id);
		ResultSet rsW = getWriters.executeQuery();
		ArrayList<Person> writers = new ArrayList<Person>();
		while(rsW.next())
			writers.add(new Person(rsW.getString("PERSONID"), rsW.getString("NAME")));
		m.setWriters(writers);
		rsW.close();
		
		getGenres.setInt(1, id);
		ResultSet rsG = getGenres.executeQuery();
		ArrayList<Genre> genres = new ArrayList<Genre>();
		while(rsG.next())
			genres.add(Genre.intToEnum(rsG.getInt("GENREID")));
		m.setGenres(genres);
		rsG.close();
		
		getLanguages.setInt(1, id);
		ResultSet rsL = getLanguages.executeQuery();
		ArrayList<Language> languages = new ArrayList<Language>();
		while(rsL.next())
			languages.add(Language.intToEnum(rsL.getInt("LANGUAGEID")));
		m.setLanguages(languages);
		rsL.close();
		
		getCountries.setInt(1, id);
		ResultSet rsC = getCountries.executeQuery();
		ArrayList<Country> countries = new ArrayList<Country>();
		while(rsC.next())
			countries.add(Country.intToEnum(rsC.getInt("COUNTRYID")));
		m.setCountries(countries);
		rsC.close();
		
		return m;
	}
	
	public void deleteMovie(AbstractMovie m) throws SQLException {
		if(m.getID()!=-1) {
			deleteMovieStatement.setInt(1, m.getID());
			deleteMovieStatement.execute();
		}
	}
	
	private void addOrUpdatePerson(Person p) throws SQLException {
		checkPersonStatement.setString(1, p.getID());
		ResultSet rs = checkPersonStatement.executeQuery();
		if(rs.next()) { //Person exists in database
			if(rs.getString("NAME") != p.getName()) { //Person's name has change since last time it was added
				updatePersonStatement.setString(1, p.getName());
				updatePersonStatement.setString(2, p.getID());
				updatePersonStatement.execute();
			}
		}
		else { //person does not yet exist in database
			addPersonStatement.setString(1, p.getID());
			addPersonStatement.setString(2, p.getName());
			addPersonStatement.execute();
		}
	}
}
