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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.gui.action.sort.IdSorter;
import com.googlecode.jmoviedb.gui.action.sort.RatingSorter;
import com.googlecode.jmoviedb.gui.action.sort.TitleSorter;
import com.googlecode.jmoviedb.gui.action.sort.YearSorter;
import com.googlecode.jmoviedb.storage.Database;
import com.googlecode.jmoviedb.storage.ZipWorker;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Moviedb {
	
	public static final String MOVIE_LIST_PROPERTY_NAME = "movielist";
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
	
	private ListenerList listeners;
	
	private Moviedb() {
		listeners = new ListenerList();
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
	
	private void newEmpty() throws ClassNotFoundException, SQLException {
		database = new Database(dbTempPath);
	}
	
	private void open(String file) throws ClassNotFoundException, SQLException, IOException {
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
	public void save(String file) throws ClassNotFoundException, SQLException, IOException {
		if(database == null) {//TODO move this somewhere else. 
			database = new Database(dbTempPath);
		}//TODO close and re-open database (create new database instance)
		database.save(this);
		new ZipWorker(file, dbTempPath).compress();
		setSaved(true);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		setSaved(false);
	}

	public AbstractMovie getMovie(int movieID) throws SQLException, IOException {
//		int movieID = sortedMovieList[listID];
		return database.getMovieFull(movieID);
	}
	
	/**
	 * Stores a movie in the database. If the movie's getID() returns -1,
	 * the movie is added as a new element in the database. If not, the existing
	 * movie element is updated.
	 * @param m
	 * @throws SQLException
	 */
	public void saveMovie(AbstractMovie m) throws SQLException {
		if(CONST.DEBUG_MODE)
			System.out.println("MODEL: saveMovie ID " + m.getID() + " Type " + MovieType.abstractMovieToInt(m));
		
		if(m.getID()==-1)
			movies.add(m);
		database.saveMovie(m);
		
		//TODO fire only when editing an existing movie
		firePropertyChange(MOVIE_LIST_PROPERTY_NAME, null, null);

		setSaved(false);
		
		if(CONST.DEBUG_MODE)
			System.out.println("Total number of movies is now " + getMovieCount());
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
	
	private void setSaved(boolean s) {
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
					+ " Total listener count is now " + listeners.getListeners().length);
	}
	
	public void removeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
		if(CONST.DEBUG_MODE)
			System.out.println("Moviedb lost a listener. Total listener count is now " + listeners.getListeners().length);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		
		if(CONST.DEBUG_MODE)
			System.out.println("PCE: fired from Moviedb to " + listeners.getListeners().length + " listener(s)");
		
		Object[] listenerObjects = listeners.getListeners();
		for (int i = 0; i < listenerObjects.length; i++) {
			((IPropertyChangeListener)listenerObjects[i]).propertyChange(event);
		}
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	
	public void closeDatabase() {
		try {
			database.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recursiveDeleteDirectory(new File(dbTempPath));
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
		}
	}
}