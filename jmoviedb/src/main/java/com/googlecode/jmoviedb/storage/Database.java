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
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.AspectRatio;
import com.googlecode.jmoviedb.enumerated.AudioChannels;
import com.googlecode.jmoviedb.enumerated.AudioCodec;
import com.googlecode.jmoviedb.enumerated.Completeness;
import com.googlecode.jmoviedb.enumerated.ContainerFormat;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.DiscType;
import com.googlecode.jmoviedb.enumerated.FilmVersion;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.enumerated.Resolution;
import com.googlecode.jmoviedb.enumerated.SubtitleFormat;
import com.googlecode.jmoviedb.enumerated.TVsystem;
import com.googlecode.jmoviedb.enumerated.VideoCodec;
import com.googlecode.jmoviedb.model.*;
import com.googlecode.jmoviedb.model.movietype.*;

/**
 * This class handles all transactions to and from the DBMS.
 * @author Tor Arne Lye
 *
 */
public class Database {

	private String path;

	private Connection connection = null;

	private PreparedStatement addMovieStatement;
	private PreparedStatement editMovieStatement;
	private PreparedStatement getMovieStatement;
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
	private PreparedStatement clearAudioTracks;
	private PreparedStatement clearSubtitleTracks;
	private PreparedStatement addActor;
	private PreparedStatement addWriter;
	private PreparedStatement addDirector;
	private PreparedStatement addGenre;
	private PreparedStatement addLanguage;
	private PreparedStatement addCountry;
	private PreparedStatement addAudioTrack;
	private PreparedStatement addSubtitleTrack;
	private PreparedStatement getActors;
	private PreparedStatement getDirectors;
	private PreparedStatement getWriters;
	private PreparedStatement getGenres;
	private PreparedStatement getLanguages;
	private PreparedStatement getCountries;
	private PreparedStatement getAudioTracks;
	private PreparedStatement getSubtitleTracks;
	private PreparedStatement getReleaseInfo;
	private PreparedStatement addReleaseInfo;
	private PreparedStatement updateReleaseInfo;

	/**
	 * The default constructor. It opens a new database connection and, if
	 * @param path the path to keep the database storage files. If database files already
	 * exist, they will be opened. Otherwise, new files are created.
	 * @throws ClassNotFoundException if the Derby driver cannot be loaded
	 * @throws SQLException if Derby throws an exception
	 */
	public Database(String path) throws ClassNotFoundException, SQLException {
		this.path = path;
		System.out.println("Creating database @ " + path);
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		connection = DriverManager.getConnection("jdbc:derby:"+path+";create=true");

		try {
			createTables();
		} catch (SQLException e) {
			/*
			 * If the SQLState equals "X0Y32", the exception was thrown because the tables
			 * already existed. This exception is ignored, all others are re-thrown.
			 */
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
//			else {
//				Statement s = connection.createStatement();
//				s.execute("ALTER TABLE MOVIEAUDIO ADD COLUMN CHANNELID SMALLINT");
//			}
		}

		//Upgrade
		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE MOVIE ADD COLUMN YEAR2 SMALLINT");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE MOVIEAUDIO ADD COLUMN DESCRIPTIVE SMALLINT");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE MOVIESUBTITLE ADD COLUMN FORCED SMALLINT");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		//Upgrade
		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE MOVIE ADD COLUMN URL1 VARCHAR(250)");
			Statement s2 = connection.createStatement();
			s2.execute("ALTER TABLE MOVIE ADD COLUMN URL2 VARCHAR(250)");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE PERSON ADD COLUMN PERSONID_LONG VARCHAR(8)");

			s = connection.createStatement();
			s.execute("ALTER TABLE MOVIEACTOR ADD COLUMN PERSONID_LONG VARCHAR(8)");

			s = connection.createStatement();
			s.execute("ALTER TABLE MOVIEDIRECTOR ADD COLUMN PERSONID_LONG VARCHAR(8)");

			s = connection.createStatement();
			s.execute("ALTER TABLE MOVIEWRITER ADD COLUMN PERSONID_LONG VARCHAR(8)");

			s = connection.createStatement();
			s.execute("ALTER TABLE MOVIE ADD COLUMN IMDBID_LONG VARCHAR(8)");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		try {
			Statement s = connection.createStatement();
			s.execute("ALTER TABLE MOVIE ADD COLUMN TMDBID INT");
			s = connection.createStatement();
			s.execute("ALTER TABLE MOVIE ADD COLUMN TMDBTYPE VARCHAR(10)");
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		//1.2.0
		tryAddColumn("MOVIEAUDIO", "TRACKTYPE", "VARCHAR(250)");
		tryAddColumn("MOVIEAUDIO", "LANGUAGE", "VARCHAR(250)");
		tryAddColumn("MOVIEAUDIO", "NOTE", "VARCHAR(250)");
		tryAddColumn("MOVIESUBTITLE", "TRACKTYPE", "VARCHAR(250)");
		tryAddColumn("MOVIESUBTITLE", "LANGUAGE", "VARCHAR(250)");
		tryAddColumn("MOVIESUBTITLE", "NOTE", "VARCHAR(250)");

		//1.3.0
		try {
			Statement s = connection.createStatement();
			s.execute("CREATE TABLE RELEASE(URL VARCHAR(512), TITLE VARCHAR(256), " +
				"TERRITORIES VARCHAR(2048), IDENTIFIERS VARCHAR(2048), RELEASEYEAR SMALLINT, " +
				"TYPE VARCHAR(256), MEDIA VARCHAR(512), PRIMARY KEY(URL))"
			);
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}

		//Note: Make sure addMovieStatement and editMovieStatement have the same column names at all times
		addMovieStatement = connection.prepareStatement("INSERT INTO MOVIE (" +
				"TYPE, IMDBID_LONG, TITLE, CUSTOMTITLE, MOVIEYEAR, RATING, " +
				"PLOTOUTLINE, TAGLINE, COLOR, RUNTIME, NOTES, VERSION, " +
				"CUSTOMFILMVERSION, LEGAL, SEEN, LOCATION, FORMAT, DISC, VIDEO, " +
				"MYENCODE, DVDREGION, TVSYSTEM, SCENERELEASENAME, " +
				"VIDEORESOLUTION, VIDEOASPECT, COVER, CONTAINER, COMPLETENESS, " +
				"COMPLETENESSDETAIL, YEAR2, URL1, URL2, TMDBID, TMDBTYPE) " +
				"VALUES (" +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
		editMovieStatement = connection.prepareStatement("UPDATE MOVIE SET " +
				"TYPE = ?, IMDBID_LONG = ?, TITLE = ?, CUSTOMTITLE = ?, " +
				"MOVIEYEAR = ?, RATING = ?, PLOTOUTLINE = ?, TAGLINE = ?, " +
				"COLOR = ?, RUNTIME = ?, NOTES = ?, VERSION = ?, " +
				"CUSTOMFILMVERSION = ?, LEGAL = ?, SEEN = ?, LOCATION = ?, FORMAT = ?, " +
				"DISC = ?, VIDEO = ?, MYENCODE = ?, DVDREGION = ?, TVSYSTEM = ?, " +
				"SCENERELEASENAME = ?, VIDEORESOLUTION = ?, VIDEOASPECT = ?, " +
				"COVER = ?, CONTAINER = ?, COMPLETENESS = ?, COMPLETENESSDETAIL = ?, " +
				"YEAR2 = ?, URL1 = ?, URL2 = ?, TMDBID = ?, TMDBTYPE = ?" +
				"WHERE MOVIEID = ?");
		getMovieStatement = connection.prepareStatement("SELECT * FROM MOVIE WHERE MOVIEID = ?");
		deleteMovieStatement = connection.prepareStatement("DELETE FROM MOVIE WHERE MOVIEID = ?");
		getMovieList = connection.prepareStatement("SELECT MOVIEID FROM MOVIE");
		addPersonStatement = connection.prepareStatement("INSERT INTO PERSON VALUES (?, ?, ?)");
		updatePersonStatement = connection.prepareStatement("UPDATE PERSON SET NAME = ?, PERSONID_LONG = ? WHERE PERSONID = ?");
		checkPersonStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSONID = ?");
		clearActors = connection.prepareStatement("DELETE FROM MOVIEACTOR WHERE MOVIEID = ?");
		clearDirectors = connection.prepareStatement("DELETE FROM MOVIEDIRECTOR WHERE MOVIEID = ?");
		clearWriters = connection.prepareStatement("DELETE FROM MOVIEWRITER WHERE MOVIEID = ?");
		clearGenres = connection.prepareStatement("DELETE FROM MOVIEGENRE WHERE MOVIEID = ?");
		clearCountries = connection.prepareStatement("DELETE FROM MOVIECOUNTRY WHERE MOVIEID = ?");
		clearLanguages = connection.prepareStatement("DELETE FROM MOVIELANGUAGE WHERE MOVIEID = ?");
		clearAudioTracks = connection.prepareStatement("DELETE FROM MOVIEAUDIO WHERE MOVIEID = ?");
		clearSubtitleTracks = connection.prepareStatement("DELETE FROM MOVIESUBTITLE WHERE MOVIEID = ?");
		addActor = connection.prepareStatement("INSERT INTO MOVIEACTOR VALUES (?, ?, ?, ?, ?)");
		addDirector = connection.prepareStatement("INSERT INTO MOVIEDIRECTOR VALUES (?, ?, ?, ?)");
		addWriter = connection.prepareStatement("INSERT INTO MOVIEWRITER VALUES (?, ?, ?, ?)");
		addGenre = connection.prepareStatement("INSERT INTO MOVIEGENRE VALUES(?, ?)");
		addLanguage = connection.prepareStatement("INSERT INTO MOVIELANGUAGE VALUES(?, ?)");
		addCountry = connection.prepareStatement("INSERT INTO MOVIECOUNTRY VALUES(?, ?)");
		addAudioTrack = connection.prepareStatement("INSERT INTO MOVIEAUDIO VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		addSubtitleTrack = connection.prepareStatement("INSERT INTO MOVIESUBTITLE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		getActors = connection.prepareStatement("SELECT * FROM MOVIEACTOR JOIN PERSON ON MOVIEACTOR.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getDirectors = connection.prepareStatement("SELECT * FROM MOVIEDIRECTOR JOIN PERSON ON MOVIEDIRECTOR.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getWriters = connection.prepareStatement("SELECT * FROM MOVIEWRITER JOIN PERSON ON MOVIEWRITER.PERSONID = PERSON.PERSONID AND MOVIEID = ?");
		getGenres = connection.prepareStatement("SELECT * FROM MOVIEGENRE WHERE MOVIEID = ?");
		getLanguages = connection.prepareStatement("SELECT * FROM MOVIELANGUAGE WHERE MOVIEID = ?");
		getCountries = connection.prepareStatement("SELECT * FROM MOVIECOUNTRY WHERE MOVIEID = ?");
		getAudioTracks = connection.prepareStatement("SELECT * FROM MOVIEAUDIO WHERE MOVIEID = ?");
		getSubtitleTracks = connection.prepareStatement("SELECT * FROM MOVIESUBTITLE WHERE MOVIEID = ?");
		getReleaseInfo = connection.prepareStatement("SELECT * FROM RELEASE WHERE URL = ?");
		addReleaseInfo = connection.prepareStatement("INSERT INTO RELEASE VALUES(?, ?, ?, ?, ?, ?, ?)");
		updateReleaseInfo = connection.prepareStatement("UPDATE RELEASE SET TITLE = ?, TERRITORIES = ?, IDENTIFIERS = ?, RELEASEYEAR = ?, TYPE = ?, MEDIA = ? WHERE URL = ?");
	}

	private Pattern namePattern = Pattern.compile("^[A-Z0-9]+$", Pattern.CASE_INSENSITIVE);
	private Pattern typePattern = Pattern.compile("^[A-Z0-9\\(\\)]+$", Pattern.CASE_INSENSITIVE);

	private void tryAddColumn(String table, String column, String type) throws SQLException, IllegalArgumentException {
		try {
			if (!namePattern.matcher(table).matches() || !namePattern.matcher(column).matches() || !typePattern.matcher(type).matches())
				throw new IllegalArgumentException("Invalid argument");

			Statement s = connection.createStatement();
			s.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + type);
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32"))
				throw e;
		}
	}

	/**
	 * Creates all the tables to be used in the database
	 * @throws SQLException in case of any SQL-related trouble
	 */
	private void createTables() throws SQLException  {
		String person = "CREATE TABLE PERSON(" +
				"PERSONID CHAR(7) NOT NULL, " +
				"NAME VARCHAR(250), " +
				"PERSONID_LONG VARCHAR(8), " +
				"PRIMARY KEY(PERSONID)" +
				")";
		String movie = "CREATE TABLE MOVIE(" +
				"MOVIEID INTEGER GENERATED ALWAYS AS IDENTITY, " +
				"TYPE SMALLINT, " +
				"IMDBID CHAR(7), " +
				"TITLE VARCHAR(250), " +
				"CUSTOMTITLE VARCHAR(250), " +
				"MOVIEYEAR SMALLINT, " +
				"YEAR2 SMALLINT, " +
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
				"CONTAINER SMALLINT, " +
				"COMPLETENESS SMALLINT, " +
				"COMPLETENESSDETAIL VARCHAR(50), " +
				"URL1 VARCHAR(250), " +
				"URL2 VARCHAR(250), " +
				"IMDBID_LONG VARCHAR(8), " +
				"TMDBID INT, TMDBTYPE VARCHAR(10), " +
				"PRIMARY KEY (MOVIEID)" +
				")";
		String movieActor = "CREATE TABLE MOVIEACTOR(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"CHARACTERNUMBER SMALLINT, " +
				"CHARACTERDESCRIPTION VARCHAR(250), " +
				"PERSONID_LONG VARCHAR(8), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " +
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" +
				")";
		String movieDirector = "CREATE TABLE MOVIEDIRECTOR(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PERSONID_LONG VARCHAR(8), " +
				"PRIMARY KEY (MOVIEID, PERSONID), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE, " +
				"FOREIGN KEY (PERSONID) REFERENCES PERSON ON DELETE CASCADE" +
				")";
		String movieWriter = "CREATE TABLE MOVIEWRITER(" +
				"MOVIEID INTEGER NOT NULL, " +
				"PERSONID CHAR(7) NOT NULL, " +
				"DETAILS VARCHAR(100), " +
				"PERSONID_LONG VARCHAR(8), " +
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
				"CHANNELID SMALLINT, " +
				"DESCRIPTIVE SMALLINT, " +
				"TRACKTYPE VARCHAR(250), " +
				"LANGUAGE VARCHAR(250), " +
				"NOTE VARCHAR(250), " +
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
				"FORCED SMALLINT, " +
				"TRACKTYPE VARCHAR(250), " +
				"LANGUAGE VARCHAR(250), " +
				"NOTE VARCHAR(250), " +
				"PRIMARY KEY (MOVIEID, TRACKNR), " +
				"FOREIGN KEY (MOVIEID) REFERENCES MOVIE ON DELETE CASCADE" +
				")";

		for(String query : new String[]{movie, person, movieActor, movieDirector,
				movieWriter, movieGenre, movieCountry, movieLanguage, movieAudio,
				movieSubtitle}) {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
	}

	/**
	 * Closes all PreparedStatements and shuts down the connection.
	 * @throws SQLException
	 */
	public void shutdown() throws SQLException {
		addMovieStatement.close();
		editMovieStatement.close();
		getMovieStatement.close();
		deleteMovieStatement.close();
		getMovieList.close();
		addPersonStatement.close();
		updatePersonStatement.close();
		checkPersonStatement.close();
		clearActors.close();
		clearCountries.close();
		clearDirectors.close();
		clearGenres.close();
		clearLanguages.close();
		clearWriters.close();
		clearAudioTracks.close();
		clearSubtitleTracks.close();
		addActor.close();
		addCountry.close();
		addDirector.close();
		addGenre.close();
		addLanguage.close();
		addWriter.close();
		addAudioTrack.close();
		addSubtitleTrack.close();
		getActors.close();
		getCountries.close();
		getDirectors.close();
		getGenres.close();
		getLanguages.close();
		getWriters.close();
		getAudioTracks.close();
		getSubtitleTracks.close();

		connection.close();

		// Shut down Derby
		try {
			DriverManager.getConnection("jdbc:derby:"+path+";shutdown=true");
		} catch (SQLException e) {
			//SQLException is always thrown on successful shutdown
			if(CONST.DEBUG_MODE)
				System.out.println("Derby was shut down");
		}
	}


	public ArrayList<AbstractMovie> getMovieList() throws SQLException, IOException {
		ResultSet rs  = getMovieList.executeQuery();
		ArrayList<AbstractMovie> list = new ArrayList<AbstractMovie>();

		while(rs.next()) {
			list.add(getMovieFull(rs.getInt("MOVIEID")));
		}

		rs.close();

		return list;
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
		statement.setString(16, m.getLocation());
		statement.setInt(17, m.getFormat().getID());
		statement.setInt(18, m.getDisc().getID());
		statement.setInt(19, m.getVideo().getID());
		statement.setInt(20, CONST.booleanToInt(m.isMyEncode()));
		statement.setInt(21, m.getDvdRegionAsInt());
		statement.setInt(22, m.getTvSystem().getID());
		statement.setString(23, m.getSceneReleaseName());
		statement.setInt(24, m.getResolution().getID());
		statement.setInt(25, m.getAspectRatio().getID());
		statement.setInt(30, m.getYear2());
		statement.setString(31, m.getUrl1StringOrNull());
		statement.setString(32, m.getUrl2StringOrNull());
		setInteger(statement, 33, m.getTmdbID());
		statement.setString(34, m.getTmdbType());

		if (m instanceof AbstractSeries) {
			AbstractSeries series = (AbstractSeries)m;
			statement.setInt(28, series.getCompleteness().getID());
			statement.setString(29, series.getCompletenessDetail());
		} else {
			statement.setInt(28, 0);
			statement.setString(29, null);
		}

		if(m.getImageBytes() != null)
			statement.setBinaryStream(26, new ByteArrayInputStream(m.getImageBytes()), m.getImageBytes().length);
		else
			statement.setNull(26, Types.BLOB);

		statement.setInt(27, m.getContainer().getID());

		if(edit)
			statement.setInt(35, m.getID());

		statement.execute();

		if(!edit) {
			ResultSet generatedKey = statement.getGeneratedKeys();
			if(generatedKey.next()) {
				System.out.println("DATABASE: Generated key: " + generatedKey.getInt(1));
				m.setID(generatedKey.getInt(1));
			} else {
				throw new SQLException("DATABASE: Failed getting the autogenerated key");
			}
		}
		statement.clearParameters();

		updateRelations(m);
	}

	private static void setInteger(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
		if (value == null)
			statement.setNull(parameterIndex, java.sql.Types.INTEGER);
		else
			statement.setInt(parameterIndex, value);
	}

	/**
	 * Checks the Actors, directors and writers for the movie and inserts them in the database
	 * if they're not there already.
	 * @param m
	 */
	private void updateRelations(AbstractMovie m) throws SQLException {

		clearDirectors.setInt(1, m.getID());
		clearDirectors.execute();
		clearDirectors.clearParameters();

		for(Person p : m.getDirectors()) {
			addOrUpdatePerson(p);

			addDirector.setInt(1, m.getID());
			addDirector.setString(2, shortenToSevenDigits(p.getID()));
			addDirector.setString(3, "");
			addDirector.setString(4, p.getID());
			addDirector.execute();
			addDirector.clearParameters();
		}

		clearWriters.setInt(1, m.getID());
		clearWriters.execute();
		clearWriters.clearParameters();

		for(Person p : m.getWriters()) {
			addOrUpdatePerson(p);

			addWriter.setInt(1, m.getID());
			addWriter.setString(2, shortenToSevenDigits(p.getID()));
			addWriter.setString(3, "");
			addWriter.setString(4, p.getID());
			addWriter.execute(); //TODO fix cases where a writer or director is listed twice
			addWriter.clearParameters();
		}

		clearActors.setInt(1, m.getID());
		clearActors.execute();
		clearActors.clearParameters();

		for(int i=0; i<m.getActors().size(); i++) {
			ActorInfo a = m.getActors().get(i);

			addOrUpdatePerson(a.getPerson());

			addActor.setInt(1, m.getID());
			addActor.setString(2, shortenToSevenDigits(a.getPerson().getID()));
			addActor.setInt(3, i);
			addActor.setString(4, a.getCharacter().substring(0, Math.min(250, a.getCharacter().length())));
			addActor.setString(5, a.getPerson().getID());
			addActor.execute();
			addActor.clearParameters();
		}

		clearGenres.setInt(1, m.getID());
		clearGenres.execute();
		clearGenres.clearParameters();

		for(Genre g : m.getGenres()) {
			addGenre.setInt(1, m.getID());
			addGenre.setInt(2, g.getID());
			addGenre.execute();
			addGenre.clearParameters();
		}

		clearLanguages.setInt(1, m.getID());
		clearLanguages.execute();
		clearLanguages.clearParameters();

		for(Language l : m.getLanguages()) {
			addLanguage.setInt(1, m.getID());
			addLanguage.setInt(2, l.getID());
			addLanguage.execute();
			addLanguage.clearParameters();
		}

		clearCountries.setInt(1, m.getID());
		clearCountries.execute();
		clearCountries.clearParameters();

		for(Country c : m.getCountries()) {
			addCountry.setInt(1, m.getID());
			addCountry.setInt(2, c.getID());
			addCountry.execute();
			addCountry.clearParameters();
		}

		clearAudioTracks.setInt(1, m.getID());
		clearAudioTracks.execute();
		clearAudioTracks.clearParameters();

		for (int i = 0; i < m.getAudioTracks().size(); i++) {
			AudioTrack track = m.getAudioTracks().get(i);
			addAudioTrack.setInt(1, m.getID());
			addAudioTrack.setInt(2, i);
			addAudioTrack.setInt(3, track.getAudio().getID());
			addAudioTrack.setInt(4, track.getLanguage() == null ? 0 : track.getLanguage().getID());
			addAudioTrack.setInt(5, CONST.booleanToInt(track.isCommentary()));
			addAudioTrack.setInt(6, track.getChannels().getID());
			addAudioTrack.setInt(7, CONST.booleanToInt(track.isAudioDescriptive()));
			addAudioTrack.setString(8, track.getTrackType());
			addAudioTrack.setString(9, track.getLanguageString());
			addAudioTrack.setString(10, track.getNote());
			addAudioTrack.execute();
			addAudioTrack.clearParameters();
		}

		clearSubtitleTracks.setInt(1, m.getID());
		clearSubtitleTracks.execute();
		clearSubtitleTracks.clearParameters();

		for (int i = 0; i < m.getSubtitles().size(); i++) {
			SubtitleTrack track = m.getSubtitles().get(i);
			addSubtitleTrack.setInt(1, m.getID());
			addSubtitleTrack.setInt(2, i);
			addSubtitleTrack.setInt(3, track.getFormat().getID());
			addSubtitleTrack.setInt(4, track.getLanguage() == null ? 0 : track.getLanguage().getID());
			addSubtitleTrack.setInt(5, CONST.booleanToInt(track.isCommentary()));
			addSubtitleTrack.setInt(6, CONST.booleanToInt(track.isHearingImpaired()));
			addSubtitleTrack.setInt(7, CONST.booleanToInt(track.isForced()));
			addSubtitleTrack.setString(8, track.getTrackType());
			addSubtitleTrack.setString(9, track.getLanguageString());
			addSubtitleTrack.setString(10, track.getNote());
			addSubtitleTrack.execute();
			addSubtitleTrack.clearParameters();
		}
	}

	public void addUpdateRelease(AbstractMovie m, Release r) throws SQLException {
		if (m.getUrl2StringOrNull() != null) {
			getReleaseInfo.setString(1, m.getUrl2StringOrNull());
			ResultSet result = getReleaseInfo.executeQuery();
			boolean hasResult = result.next();
			getReleaseInfo.clearParameters();

			if (hasResult) {
				// "UPDATE RELEASE SET TITLE = ?, TERRITORIES = ?, IDENTIFIERS = ?, RELEASEYEAR = ?, TYPE = ?, MEDIA = ? WHERE URL = ?"
				updateReleaseInfo.setString(1, r.getReleaseTitle());
				updateReleaseInfo.setString(2, r.getTerritoriesJson());
				updateReleaseInfo.setString(3, m.getIdentifiers());
				updateReleaseInfo.setInt(4, r.getReleaseYear());
				updateReleaseInfo.setString(5, m.getReleaseType());
				updateReleaseInfo.setString(6, m.getMedia());
				updateReleaseInfo.setString(7, m.getUrl2String());
				updateReleaseInfo.execute();
				updateReleaseInfo.clearParameters();
			} else {
				addReleaseInfo.setString(1, m.getUrl2String());
				addReleaseInfo.setString(2, r.getReleaseTitle());
				addReleaseInfo.setString(3, r.getTerritoriesJson());
				addReleaseInfo.setString(4, m.getIdentifiers());
				addReleaseInfo.setInt(5, r.getReleaseYear());
				addReleaseInfo.setString(6, m.getReleaseType());
				addReleaseInfo.setString(7, m.getMedia());
				addReleaseInfo.execute();
				addReleaseInfo.clearParameters();
			}
		}
	}

	/**
	 * Loads a movie from the database.
	 * @param id
	 * @return an instance of an AbstractMovie subclass
	 * @throws SQLException
	 */
	private AbstractMovie getMovieLite(int id) throws SQLException, IOException {
		getMovieStatement.setInt(1, id);
		ResultSet rs = getMovieStatement.executeQuery();
		if(!rs.next())
			return null;

		AbstractMovie m = MovieType.intToAbstractMovie(rs.getInt("TYPE"));
		if(m == null) {
			throw new SQLException("Invalid Type ID encountered in database: " + rs.getInt("TYPE"));
		}

		m.setID(rs.getInt("MOVIEID"));
		m.setImdbID(resolveShortOrLong(rs, "IMDBID", "IMDBID_LONG"));
		m.setTmdbID(rs.getObject("TMDBID", Integer.class));
		m.setTmdbType(rs.getString("TMDBTYPE"));
		m.setTitle(rs.getString("TITLE"));
		m.setCustomTitle(rs.getString("CUSTOMTITLE"));
		m.setYear(rs.getInt("MOVIEYEAR"));
		m.setYear2(rs.getInt("YEAR2"));
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
		m.setLocation(rs.getString("LOCATION"));
		m.setFormat(FormatType.intToEnum(rs.getInt("FORMAT")));
		m.setDisc(DiscType.intToEnum(rs.getInt("DISC")));
		m.setVideo(VideoCodec.intToEnum(rs.getInt("VIDEO")));
		m.setMyEncode(rs.getInt("MYENCODE"));
		m.setDvdRegionAsInt(rs.getInt("DVDREGION"));
		m.setTvSystem(TVsystem.intToEnum(rs.getInt("TVSYSTEM")));
		m.setSceneReleaseName(rs.getString("SCENERELEASENAME"));
		m.setResolution(Resolution.intToEnum(rs.getInt("VIDEORESOLUTION")));
		m.setAspectRatio(AspectRatio.intToEnum(rs.getInt("VIDEOASPECT")));
		m.setContainer(ContainerFormat.intToEnum(rs.getInt("CONTAINER")));
		m.setUrl1(rs.getString("URL1"));
		m.setUrl2(rs.getString("URL2"));

		if (m instanceof AbstractSeries) {
			AbstractSeries series = (AbstractSeries)m;
			series.setCompleteness(Completeness.intToEnum(rs.getInt("COMPLETENESS")));
			series.setCompletenessDetail(rs.getString("COMPLETENESSDETAIL"));
		}

		InputStream stream = rs.getBinaryStream("COVER");
		if(stream != null) {
			byte[] imageBytes = new byte[0];
			while(stream.available() > 0) { //loop while there is more data to read

				//how much data is ready right now?
				int readLength = stream.available();

				//create a new array that contains the bytes read until now, but with free space for more
				byte[] newBytes = new byte[imageBytes.length+readLength];
				System.arraycopy(imageBytes, 0, newBytes, 0, imageBytes.length);

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

	private String resolveShortOrLong(ResultSet rs, String shortColumnID, String longColumnID) throws SQLException {
		String longVal = rs.getString(longColumnID);
		if (longVal != null)
			return longVal;
		return expandFromSevenDigits(rs.getString(shortColumnID));
	}

	private String shortenToSevenDigits(String dec) throws SQLException {
		if (dec.length() > 7) {
			int numericId = Integer.parseInt(dec);
			String hex = "x" + Integer.toString(numericId, 16).toUpperCase();
			if (hex.length() <= 7) {
				return hex;
			}
			else {
				throw new SQLException("Could not shorten value " + dec + " to 7 digits (" + hex + ")");
			}
		}
		return dec;
	}

	private String expandFromSevenDigits(String hex) {
		if (hex.startsWith("x"))
		{
			int numericId = Integer.parseInt(hex.substring(1), 16);
			String dec = Integer.toString(numericId, 10);
			return dec;
		}
		return hex;
	}

	public AbstractMovie getMovieFull(int id) throws SQLException, IOException {
		AbstractMovie m = getMovieLite(id);

		getActors.setInt(1, id);
		ResultSet rsA = getActors.executeQuery();
		ArrayList<ActorInfo> actors = new ArrayList<ActorInfo>();

		while(rsA.next())
			actors.add(new ActorInfo(rsA.getInt("CHARACTERNUMBER"), new Person(resolveShortOrLong(rsA, "PERSONID", "PERSONID_LONG"), rsA.getString("NAME")), rsA.getString("CHARACTERDESCRIPTION")));
		Collections.sort(actors);
		m.setActors(actors);
		rsA.close();

		getDirectors.setInt(1, id);
		ResultSet rsD = getDirectors.executeQuery();
		ArrayList<Person> directors = new ArrayList<Person>();
		while(rsD.next())
			directors.add(new Person(resolveShortOrLong(rsD, "PERSONID", "PERSONID_LONG"), rsD.getString("NAME")));
		m.setDirectors(directors);
		rsD.close();

		getWriters.setInt(1, id);
		ResultSet rsW = getWriters.executeQuery();
		ArrayList<Person> writers = new ArrayList<Person>();
		while(rsW.next())
			writers.add(new Person(resolveShortOrLong(rsW, "PERSONID", "PERSONID_LONG"), rsW.getString("NAME")));
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

		getAudioTracks.setInt(1, id);
		ResultSet rsAud = getAudioTracks.executeQuery();
		HashMap<Integer, AudioTrack> audioHash = new HashMap<Integer, AudioTrack>();
		ArrayList<AudioTrack> audioTracks = new ArrayList<AudioTrack>();
		while(rsAud.next())
			audioHash.put(rsAud.getInt("TRACKNR"), new AudioTrack(
					Language.intToEnum(rsAud.getInt("LANGUAGEID")),
					AudioCodec.intToEnum(rsAud.getInt("CODEC")),
					AudioChannels.intToEnum(rsAud.getInt("CHANNELID")),
					CONST.intToBoolean(rsAud.getInt("COMMENTARY")),
					CONST.intToBoolean(rsAud.getInt("DESCRIPTIVE")),
					rsAud.getString("TRACKTYPE"),
					rsAud.getString("LANGUAGE"),
					rsAud.getString("NOTE")
			));
		for (int i = 0; i < audioHash.size(); i++) {
			if(audioHash.containsKey(i))
				audioTracks.add(audioHash.get(i));
		}
		m.setAudioTracks(audioTracks);
		rsAud.close();

		getSubtitleTracks.setInt(1, id);
		ResultSet rsSub = getSubtitleTracks.executeQuery();
		HashMap<Integer, SubtitleTrack> subHash = new HashMap<Integer, SubtitleTrack>();
		ArrayList<SubtitleTrack> subTracks = new ArrayList<SubtitleTrack>();
		while(rsSub.next()) {
			subHash.put(rsSub.getInt("TRACKNR"), new SubtitleTrack(
					Language.intToEnum(rsSub.getInt("LANGUAGEID")),
					SubtitleFormat.intToEnum(rsSub.getInt("FORMAT")),
					CONST.intToBoolean(rsSub.getInt("COMMENTARY")),
					CONST.intToBoolean(rsSub.getInt("HEARINGIMPAIRED")),
					CONST.intToBoolean(rsSub.getInt("FORCED")),
					rsSub.getString("TRACKTYPE"),
					rsSub.getString("LANGUAGE"),
					rsSub.getString("NOTE")
			));
		}
		for (int i = 0; i < subHash.size(); i++) {
			if(subHash.containsKey(i))
				subTracks.add(subHash.get(i));
		}
		m.setSubtitles(subTracks);
		rsSub.close();

		return m;
	}

	public Release getRelease(AbstractMovie m) throws SQLException {
		if (m.getUrl2StringOrNull() != null) {
			getReleaseInfo.setString(1, m.getUrl2String());
			ResultSet result = getReleaseInfo.executeQuery();
			getReleaseInfo.clearParameters();
			if (result.next()) {
				/* "CREATE TABLE RELEASE(URL VARCHAR(512), TITLE VARCHAR(256), " +
				"TERRITORIES VARCHAR(2048), IDENTIFIERS VARCHAR(2048), RELEASEYEAR SMALLINT, " +
				"TYPE VARCHAR(256), MEDIA VARCHAR(512), PRIMARY KEY(URL)) */
				Release r = new Release();
				r.setReleaseTitle(result.getString("TITLE"));
				r.setReleaseYear(result.getInt("RELEASEYEAR"));
				r.setTerritoriesJson(result.getString("TERRITORIES"));
			}
		}
		return null;
	}

	public void deleteMovie(AbstractMovie m) throws SQLException {
		if(m.getID()!=-1) {
			deleteMovieStatement.setInt(1, m.getID());
			deleteMovieStatement.execute();
			deleteMovieStatement.clearParameters();
		}
		m = null;
	}

	private void addOrUpdatePerson(Person p) throws SQLException {
		checkPersonStatement.setString(1, shortenToSevenDigits(p.getID()));
		ResultSet rs = checkPersonStatement.executeQuery();
		if(rs.next()) { //Person exists in database
			if(rs.getString("NAME") != p.getName()) { //Person's name has changed since last time it was added
				updatePersonStatement.setString(1, p.getName());
				updatePersonStatement.setString(2, shortenToSevenDigits(p.getID()));
				updatePersonStatement.setString(3, p.getID());
				updatePersonStatement.execute();
				updatePersonStatement.clearParameters();
			}
		}
		else { //person does not yet exist in database
			addPersonStatement.setString(1, shortenToSevenDigits(p.getID()));
			addPersonStatement.setString(2, p.getName());
			addPersonStatement.setString(3, p.getID());
			addPersonStatement.execute();
			addPersonStatement.clearParameters();
		}
	}

	/**
	 * Runs a custom SQL query against the database
	 * @param query the query to run
	 * @return an Object, either a ResultSet containing the results or
	 * an Integer containing the number of updated rows.
	 * @throws SQLException
	 */
	public Object runCustomQuery(String query) throws SQLException {
		Statement s = connection.createStatement();
		if(s.execute(query))
			return s.getResultSet();
		int count = s.getUpdateCount();
		if(count>-1)
			return Integer.valueOf(count);
		throw new SQLException("getUpdateCount() returned -1");
	}
}
