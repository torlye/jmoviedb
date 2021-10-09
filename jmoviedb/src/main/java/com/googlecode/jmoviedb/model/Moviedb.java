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

package com.googlecode.jmoviedb.model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.storage.Database;
import com.googlecode.jmoviedb.storage.ZipWorker;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Moviedb {
	
	public static final String ACTOR_LIST_PROPERTY_NAME = "actorlist";
	public static final String SAVE_STATUS_PROPERTY_NAME = "savestatus";
	
	private BasicEventList<AbstractMovie> movies;
//	private HashMap<Integer, String> actors;
//	private int[] sortedMovieList;
	private String title;
	private boolean saved;
	
	private Database database;
	private String saveFile;
	private String dbTempPath;
	
	private ArrayList<IPropertyChangeListener> listeners;
	
	private Moviedb() {
		listeners = new ArrayList<IPropertyChangeListener>();
		movies = new BasicEventList<AbstractMovie>();
		title = "Untitled"; // TODO change this
		setSaved(true);
		dbTempPath = getRandomTempPath();
	}
	
	public Moviedb(String file) throws ClassNotFoundException, SQLException, IOException {
		this();
		if(file == null) {
			newEmpty();
		} else {
			saveFile = file;
			open(saveFile);
		}
	}
	
	/**
	 * Generates a file path to a sub-folder of the system's temp folder.
	 * This is where temporary files will be kept.
	 * @return the file path to use for temporary files
	 */
	public static String getRandomTempPath() {
		String fileSeparator = System.getProperty("file.separator");
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		if(!tmpdir.endsWith(fileSeparator))
			tmpdir += fileSeparator;
		
		//Generate a random number
		Random random = new Random();
		int randomInt = random.nextInt(99999999);
		
		String fullpath = tmpdir+"moviedb"+fileSeparator+randomInt+fileSeparator;
	    
	    return fullpath;
	}
	
	private synchronized void newEmpty() throws ClassNotFoundException, SQLException {
		database = new Database(dbTempPath);
	}
	
	private synchronized void open(String file) throws ClassNotFoundException, SQLException, IOException {
		new ZipWorker(file, dbTempPath).extract();
		database = new Database(dbTempPath);
		movies.addAll(database.getMovieList());
		setSaved(true);
	}
	
	/**
	 * Saves the database to the specified file. This method is currently unsafe - 
	 * if save is called when there still are outstanding disk writes some changes
	 * presumably will not be saved. This will probably be very rare, as the database
	 * has autocommit enabled, but the problem nonetheless needs to be researched 
	 * further to prevent data loss.
	 * @param file the file to store the database in
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public synchronized void save(String file) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("shutdown");
		database.shutdown();
		System.out.println("zip");
		new ZipWorker(file, dbTempPath).compress();
		setSaved(true);
		System.out.println("open");
		database = new Database(dbTempPath);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		setSaved(false);
	}

	public synchronized AbstractMovie getMovie(int movieID) throws SQLException, IOException {
//		int movieID = sortedMovieList[listID];
		return database.getMovieFull(movieID);
	}
	
	public synchronized void revertMovie(AbstractMovie movie) throws SQLException, IOException {
		int index = movies.indexOf(movie);
		if(index > -1)
			movies.set(index, getMovie(movie.getID()));
	}
	
	/**
	 * Stores a movie in the database. If the movie's getID() returns -1,
	 * the movie is added as a new element in the database. If not, the existing
	 * movie element is updated.
	 * @param savedMovie the full movie instance to save.
	 * @param listMovieInstance the movie instance used as a list item. null if a new movie is to be added.
	 * @throws SQLException
	 */
	public synchronized void saveMovie(AbstractMovie movie) throws SQLException, IOException {
		int movieID = movie.getID();
		
		if(CONST.DEBUG_MODE)
			System.out.println("MODEL: saveMovie ID " + movieID);
		
		database.saveMovie(movie);
		
		if (movieID < 0) //new movie added
			movies.add(movie);
		else //existing movie updated
			movies.set(movies.indexOf(movie), movie);
		
		setSaved(false);
		
		if(CONST.DEBUG_MODE)
			System.out.println("Total number of movies is now " + getMovieCount());
	}

	public synchronized void massUpdateMovies(List<AbstractMovie> updatedMovies) throws SQLException
	{
		for (AbstractMovie movie : updatedMovies) {
			database.saveMovie(movie);
			movies.set(movies.indexOf(movie), movie);
		}

		setSaved(false);
	}
	
	public synchronized void deleteMovie(AbstractMovie movie) throws SQLException {
		int movieID = movie.getID();
		
		if(CONST.DEBUG_MODE)
			System.out.println("MODEL: Delete movie ID " + movieID);
		
		if (movies.remove(movie)) {

			database.deleteMovie(movie);
		
			setSaved(false);
		
			if(CONST.DEBUG_MODE)
				System.out.println("Total number of movies is now " + getMovieCount());
		} else if(CONST.DEBUG_MODE) {
			System.out.println("Movie not in list, could not remove");
		}
	}
	
	/**
	 * Saves a movie to the database without notifying the model layer. Used when mass-updating or mass-adding movies.
	 * updateModel() should be called when saving is complete.
	 * @param movie
	 * @throws SQLException 
	 */
	public synchronized void saveBackground(AbstractMovie movie) throws SQLException {
		database.saveMovie(movie);
	}
	
	/**
	 * Reloads all data from the database store into the model layer. To be used when the database has been modified without notifying the model layer.
	 * @throws SQLException
	 * @throws IOException
	 */
	public synchronized void updateModel() throws SQLException, IOException {
		movies.clear();
		movies.addAll(database.getMovieList());
		setSaved(false);
	}
	
	public int getMovieCount() {
		System.out.println("GETTING MOVIE COUNT: " + movies.size());
		return movies.size();
	}
	
	public EventList<AbstractMovie> getMovieList() {
		return movies;
	}
	
	public String toString() {
		return title;
	}
	
	/**
	 * Gets the database's save status
	 * @return false of there are unsaved changes, true otherwise
	 */
	public boolean isSaved() {
		return saved;
	}
	
	/**
	 * Sets the database's saved status. This method should usually NOT be called by the GUI,
	 * as it is updated automatically by Moviedb when adding/saving movies.
	 * @param s save status
	 */
	public void setSaved(boolean s) {
		if(saved == s)
			return;
		boolean oldValue = saved;
		saved = s;
		firePropertyChange(SAVE_STATUS_PROPERTY_NAME, oldValue, s);
	}
	
	public void addListener(IPropertyChangeListener listener) {
		listeners.add(listener);
		if(CONST.DEBUG_MODE)
			System.out.println("Moviedb has a new listener. It is " + listener
					+ " Total listener count is now " + listeners.size());
	}
	
	public void removeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
		if(CONST.DEBUG_MODE)
			System.out.println("Moviedb lost a listener. Total listener count is now " + listeners.size());
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		
		if(CONST.DEBUG_MODE)
			System.out.println("PCE: fired from Moviedb to " + listeners.size() + " listener(s)");
		
		for (IPropertyChangeListener listener : listeners) {
			listener.propertyChange(event);
		}
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	
	public synchronized void closeDatabase() {
		try {
			database.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		database=null;
		recursiveDeleteDirectory(new File(dbTempPath));
	}
	
	/**
	 * Returns the current Database object
	 * @return the current Database object
	 */
	public Database getDatabase() {
		return database;
	}
	
	private static void recursiveDeleteDirectory(File path) {
		if(path.exists()) {
			File[] files = path.listFiles();
			for(int i = 0; i<files.length; i++) {
				if(files[i].isDirectory())
					recursiveDeleteDirectory(files[i]);
				else
					files[i].delete();
			}
			path.delete();
		}
	}
}