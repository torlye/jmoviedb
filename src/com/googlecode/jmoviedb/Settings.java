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

package com.googlecode.jmoviedb;

import java.io.IOException;

import com.googlecode.jmoviedb.language.*;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Point;

/**
 * Manages the user's preferences for the application and stores them in an xml file.
 * @author Tor Arne Lye
 *
 */
public class Settings {
	private static Settings settingsInstance;
	private static String fileName;
	private DialogSettings dSettings;
	
	private ListenerList listeners;
	
	private final static String VERSION = "Version";
	private final static String LANGUAGE = "Language";
	private final static String EEACTIVATE = "EEActivate";
	
	private final static String SECTION_WINDOW = "WindowSettings";
	private final static String WSIZEX = "WindowSizeX";
	private final static String WSIZEY = "WindowSizeY";
	private final static String WPOSX = "WindowPositionX";
	private final static String WPOSY = "WindowPositionY";
	
	private final static String SECTION_SORT = "Sorting";
	private final static String SORTBY = "SortBy";
	private final static String SORTDIR = "SortDirection";
	
	private final static String SECTION_RECENT = "RecentFiles";
	private final static String FILE1 = "File1";
	private final static String FILE2 = "File2";
	private final static String FILE3 = "File3";
	private final static String FILE4 = "File4";
	private final static String FOLDER = "Folder";
	
	private final static String SECTION_IMDB = "ImdbSettings";
	private final static String IMDB_URL = "ImdbUrl";
	private final static String IMDB_SEARCH_URL = "ImdbSearchUrl";
	private final static String IMDB_PERSON_URL = "ImdbPersonUrl";
	
	public static Settings getSettings() {
		if(settingsInstance == null) {
			settingsInstance = new Settings();
		}
		return settingsInstance;
	}
	
	public Settings() {
		listeners = new ListenerList();
		
		if(System.getProperty("user.home").endsWith(System.getProperty("file.separator")))
			fileName = System.getProperty("user.home") + ".jmdb-settings.xml";
		else
			fileName = System.getProperty("user.home") + System.getProperty("file.separator") + ".jmdb-settings.xml";
		
		dSettings = new DialogSettings("JMoviedb");
		try {
			dSettings.load(fileName);
		} catch (IOException e) {
			create();
		}
	}
	
	/**
	 * Create the settings file if it does not exist.
	 */
	private void create() {
		dSettings.put(VERSION, CONST.MAJOR_VERSION
				+ "." + CONST.MINOR_VERSION
				+ "." + CONST.RELEASE_VERSION);
		dSettings.put(LANGUAGE, ValidGuiLanguages.english.toString());
		dSettings.put(EEACTIVATE, "");
		
		DialogSettings window = new DialogSettings(SECTION_WINDOW);
		dSettings.addSection(window);
		window.put(WSIZEX, 600);
		window.put(WSIZEY, 400);
		window.put(WPOSX, -1);
		window.put(WPOSY, -1);
		
		DialogSettings sorting = new DialogSettings(SECTION_SORT);
		dSettings.addSection(sorting);
		sorting.put(SORTBY, CONST.SORT_BY_TITLE);
		sorting.put(SORTDIR, CONST.SORT_ASCENDING);
		
		DialogSettings files = new DialogSettings(SECTION_RECENT);
		dSettings.addSection(files);
		files.put(FILE1, "");
		files.put(FILE2, "");
		files.put(FILE3, "");
		files.put(FILE4, "");
		files.put(FOLDER, "");
		
		DialogSettings imdb = new DialogSettings(SECTION_IMDB);
		dSettings.addSection(imdb);
		imdb.put(IMDB_URL, "http://www.imdb.com/title/tt");
		imdb.put(IMDB_SEARCH_URL, "http://www.imdb.com/find?s=tt&q=");
		imdb.put(IMDB_PERSON_URL, "http://www.imdb.com/name/nm");
		
		save();
	}
	
	
	public void save() {
		try {
			dSettings.save(fileName);
		} catch (IOException e) {
			//TODO do something useful
			e.printStackTrace();
		}
	}
	
	public String getProgramVersion() {
		return dSettings.get(VERSION);
	}
	
	public ValidGuiLanguages getLanguage() {
		return ValidGuiLanguages.StringToEnum(dSettings.get(LANGUAGE));
	}
	
	public GuiLanguage getLanguageClass() {
		return ValidGuiLanguages.StringToEnum(dSettings.get(LANGUAGE)).getLanguageClass();
	}
	
	public void setLanguage(ValidGuiLanguages language) {
		dSettings.put(LANGUAGE, language.toString());
	}
	
	public String getEEactivate() {
		return dSettings.get(EEACTIVATE);
	}
	
	public void setEEactivate(String c) {
		dSettings.put(EEACTIVATE, c);
	}
	
	public Point getWindowSize() {
		return new Point(
				dSettings.getSection(SECTION_WINDOW).getInt(WSIZEX), 
				dSettings.getSection(SECTION_WINDOW).getInt(WSIZEY));
	}
	
	public void setWindowSize(Point p) {
		dSettings.getSection(SECTION_WINDOW).put(WSIZEX, p.x); 
		dSettings.getSection(SECTION_WINDOW).put(WSIZEY, p.y);
	}
	
	public Point getWindowPosition() {
		return new Point(
				dSettings.getSection(SECTION_WINDOW).getInt(WPOSX),
				dSettings.getSection(SECTION_WINDOW).getInt(WPOSY));
	}
	
	public void setWindowPosition(Point p) {
		dSettings.getSection(SECTION_WINDOW).put(WPOSX, p.x); 
		dSettings.getSection(SECTION_WINDOW).put(WPOSY, p.y);
	}
	
	public String[] getRecentFiles() {
		return new String[]{
				dSettings.getSection(SECTION_RECENT).get(FILE1),
				dSettings.getSection(SECTION_RECENT).get(FILE2),
				dSettings.getSection(SECTION_RECENT).get(FILE3),
				dSettings.getSection(SECTION_RECENT).get(FILE4)};
	}
	
	public void updateRecentFiles(String filePath) {
		if(dSettings.getSection(SECTION_RECENT).get(FILE1).equals(filePath))
			return;
		else { 
			if(dSettings.getSection(SECTION_RECENT).get(FILE2).equals(filePath)) {
				dSettings.getSection(SECTION_RECENT).put(FILE2, dSettings.getSection(SECTION_RECENT).get(FILE1));
				dSettings.getSection(SECTION_RECENT).put(FILE1, filePath);
			} else if(dSettings.getSection(SECTION_RECENT).get(FILE3).equals(filePath)) {
				dSettings.getSection(SECTION_RECENT).put(FILE3, dSettings.getSection(SECTION_RECENT).get(FILE3));
				dSettings.getSection(SECTION_RECENT).put(FILE2, dSettings.getSection(SECTION_RECENT).get(FILE1));
				dSettings.getSection(SECTION_RECENT).put(FILE1, filePath);
			} else {
				dSettings.getSection(SECTION_RECENT).put(FILE4, dSettings.getSection(SECTION_RECENT).get(FILE3));
				dSettings.getSection(SECTION_RECENT).put(FILE3, dSettings.getSection(SECTION_RECENT).get(FILE2));
				dSettings.getSection(SECTION_RECENT).put(FILE2, dSettings.getSection(SECTION_RECENT).get(FILE1));
				dSettings.getSection(SECTION_RECENT).put(FILE1, filePath);
			}
			firePropertyChange(CONST.RECENT_FILES_PROPERTY_NAME, null, null);
		}
	}
	
	public String getRecentFolder() {
		return dSettings.getSection(SECTION_RECENT).get(FOLDER);
	}
	
	public void setRecentFolder(String folderPath) {
		dSettings.getSection(SECTION_RECENT).put(FOLDER, folderPath);
	}
	
	public String getImdbUrl() {
		return dSettings.getSection(SECTION_IMDB).get(IMDB_URL);
	}
	
	public String getImdbSearchUrl() {
		return dSettings.getSection(SECTION_IMDB).get(IMDB_SEARCH_URL);
	}
	
	public String getImdbPersonUrl() {
		return dSettings.getSection(SECTION_IMDB).get(IMDB_PERSON_URL);
	}
	
	public String getSettingsFile() {
		return fileName;
	}
	
	/**
	 * Gets the sort parameter
	 * @return sort parameter, as defined by constants in the CONST class.
	 */
	public int getSortBy() {
		return dSettings.getSection(SECTION_SORT).getInt(SORTBY);
	}
	
	/**
	 * Gets the sort direction
	 * @return sort direction, as defined by constants in the CONST class.
	 */
	public int getSortDirection() {
		return dSettings.getSection(SECTION_SORT).getInt(SORTDIR);
	}
	
	public void setSortBy(int i) {
		dSettings.getSection(SECTION_SORT).put(SORTBY, i);
	}
	
	public void setSortDirection(int i) {
		dSettings.getSection(SECTION_SORT).put(SORTDIR, i);
	}
	
	public void addListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		
		Object[] listenerObjects = listeners.getListeners();
		for (int i = 0; i < listenerObjects.length; i++) {
			((IPropertyChangeListener)listenerObjects[i]).propertyChange(event);
		}
	}
}
