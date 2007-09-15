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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

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
	
	private HashMap<Integer, AbstractMovie> movies;
//	private HashMap<Integer, String> actors;
	private int[] sortedMovieList;
	private String title;
	private boolean saved;
	
	private Database database;
	private String saveFile;
	private String dbTempPath;
	
	private ListenerList listeners;
	
	private Moviedb() {
		listeners = new ListenerList();
		movies = new HashMap<Integer, AbstractMovie>();
		sortedMovieList = new int[0]; 
//		actors = new HashMap<Integer, String>();
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
		sort();
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
		movies = database.getMovieList();
		setSaved(true);
	}
	
	public void save(String file) throws ClassNotFoundException, SQLException, IOException {
		if(database == null) {
			database = new Database(dbTempPath);
			database.createTables();
		}
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

	public AbstractMovie getMovie(int listID) throws SQLException {
		int movieID = sortedMovieList[listID];
		return movies.get(movieID);
		//TODO load more info from DB
	}
	
	/**
	 * Stores a movie in the database. If the movie's getID() returns -1,
	 * the movie is added as a new element in the database. If not, the existing
	 * movie element is updated.
	 * @param m
	 * @throws Exception
	 */
	public void saveMovie(AbstractMovie m) throws SQLException {
		saveMovieWithoutSorting(m);
		sort();
		// firePropertyChange sould be unnecessary, as it is fired from sort(int, int).
	}

	/**
	 * Stores a movie in the database. This is a special case that adds a movie without
	 * sorting or triggering property change events. This is useful for example when
	 * importing a number of movies in one operation.
	 * @param m
	 * @throws SQLException 
	 */
	public void saveMovieWithoutSorting(AbstractMovie m) throws SQLException {
		if(CONST.DEBUG_MODE)
			System.out.println("MODEL: saveMovie ID " + m.getID() + " Type " + MovieType.abstractMovieToInt(m));
		
		if(m.getID() == -1)
			m.setID(database.addMovie(m));
		else
			database.editMovie(m);
		movies.put(m.getID(), m);

		setSaved(false);
		
		if(CONST.DEBUG_MODE)
			System.out.println("Total number of movies is now " + getMovieCount());
	}
	
	public int getMovieCount() {
		System.out.println("GETTING MOVIE COUNT: " + movies.size());
		return movies.size();
	}
	
	public AbstractMovie[] getMovieArray() {
		try {
			AbstractMovie[] returnArray = new AbstractMovie[sortedMovieList.length];
			int counter = 0;
			
			for(int i : sortedMovieList) {
				returnArray[counter] = movies.get(i);
				counter++;
			}
			if(CONST.DEBUG_MODE)
				System.out.println("Returning a movie array of size " + returnArray.length);
			
			return returnArray;
		} catch(Exception e) {
			//TODO
			e.printStackTrace();
			return null;
		}
	}
	
//	public AbstractMovie[] getMovieArray() {
//		AbstractMovie[] returnArray = new AbstractMovie[movies.size()];
//		
//		Iterator movieIterator = movies.keySet().iterator();
//		int i = 0;
//		while(movieIterator.hasNext()) {
//			returnArray[i] = movies.get(movieIterator.next());
//			i++;
//		}
//		return returnArray;
//	}
//	
//	public Iterator getMovieIterator() {
//		return movies.keySet().iterator();
//	}
	
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
	
	public void setSaved(boolean s) {
		if(saved == s)
			return;
		boolean oldValue = saved;
		saved = s;
		firePropertyChange(SAVE_STATUS_PROPERTY_NAME, oldValue, s);
	}
	
	public void addListener(IPropertyChangeListener listener) {
		listeners.add(listener);
		if(CONST.DEBUG_MODE) {
			System.out.println("Moviedb has a new listener. It is " + listener);
			System.out.println("Total listener count is now " + listeners.getListeners().length);
		}
	}
	
	public void removeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
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
	
	public void close() {
		database.shutdown();
		recursiveDeleteDirectory(new File(dbTempPath));
	}
	
	public static void recursiveDeleteDirectory(File path) {
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
	
	/**
	 * Sorts the movie list
	 */
	public void sort() {
		//TODO needs lots of work. this is a dummy implementation.
		
		int sortBy = Settings.getSettings().getSortBy();
		int sortDir = Settings.getSettings().getSortDirection();
		
		boolean descending = false;
		if(sortDir == CONST.SORT_DESCENDING)
			descending = true;
		
		//creating a temporary array to sort on
		AbstractMovie[] movieArray = new AbstractMovie[getMovieCount()];
		int counter = 0;
		for(int id : movies.keySet()) {
			movieArray[counter] = movies.get(id);
			counter++;
		}
		
		//sorting according to the correct parameter
		if(sortBy == CONST.SORT_BY_ID) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by ID, descending: " + descending);
			Arrays.sort(movieArray, new IdSorter(descending));
		} else if(sortBy == CONST.SORT_BY_TITLE) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by title, descending: " + descending);
			Arrays.sort(movieArray, new TitleSorter(descending));
		} else if(sortBy == CONST.SORT_BY_YEAR) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by year, descending: " + descending);
			Arrays.sort(movieArray, new YearSorter(descending));
		} else if(sortBy == CONST.SORT_BY_RATING) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by rating, descending: " + descending);
			Arrays.sort(movieArray, new RatingSorter(descending));
		}
		
		//updating sortedMovieList with the new data
		sortedMovieList = new int[movieArray.length];	
		for(int i=0; i<movieArray.length; i++) {
			sortedMovieList[i] = movieArray[i].getID();
		}
			
		if(CONST.DEBUG_MODE)
			System.out.println("Sort completed, " + movieArray.length + " items");
		
		firePropertyChange(MOVIE_LIST_PROPERTY_NAME, null, null);
	}
}