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

import java.io.ByteArrayInputStream;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;

/**
 * This class provides a number of public constants and static methods.
 * @author Tor Arne Lye
 *
 */
public class CONST {
	
	/**
	 * Enables debug messages to the console.
	 */
	public static boolean DEBUG_MODE = true;
	
	public static final String FILE_EXTENSION = "jmdb";
	
	/**
	 * The "major" part of the current version number.
	 * The full version number will be displayed as:
	 * MAJOR_VERSION.MINOR_VERSION.RELEASE_VERSION DEVELOPMENT_STAGE
	 */
	public static final int MAJOR_VERSION = 1;
	
	/**
	 * The "minor" part of the current version number.
	 * The full version number will be displayed as:
	 * MAJOR_VERSION.MINOR_VERSION.RELEASE_VERSION DEVELOPMENT_STAGE
	 */
	public static final int MINOR_VERSION = 0;
	
	/**
	 * The "release" part of the current version number. Changes to this
	 * number indicate a bugfix release. The full version number will be displayed as:
	 * MAJOR_VERSION.MINOR_VERSION.RELEASE_VERSION DEVELOPMENT_STAGE
	 */
	public static final int RELEASE_VERSION = 1;
	
	/**
	 * The current development stage expressed as a string.
	 * The full version number will be displayed as:
	 * MAJOR_VERSION.MINOR_VERSION.RELEASE_VERSION DEVELOPMENT_STAGE
	 */
	public static final String DEVELOPMENT_STAGE = "";
	
	/**
	 * SWT version number
	 */
	public static final String VERSION_SWT = SWT.getVersion()+"";
	
	/**
	 * Apache Derby version number
	 */
	public static final String VERSION_DERBY = "10.14.2.0";
	
	/**
	 * BrowserLauncher2 version number
	 */
	public static final String VERSION_BROWSERLAUNCHER = "1.3";
	
	/**
	 * Java CSV version number
	 */
	public static final String VERSION_CSV = "2.0";
	
	/**
	 * Glazed Lists version number
	 */
	public static final String VERSION_GLAZED = "1.8.0";
	
	/**
	 * KTable version number
	 */
	public static final String VERSION_KTABLE = "2.2.0";
	
	/**
	 * This program's website
	 */
	public static final String WEBSITE = "https://github.com/torlye/jmoviedb";
	
	/**
	 * SWT website
	 */
	public static final String WEBSITE_SWT = "http://www.eclipse.org/swt";
	
	/**
	 * Glazed Lists website
	 */
	public static final String WEBSITE_GLAZED = "http://publicobject.com/glazedlists";
	
	
	/**
	 * Apache Derby website
	 */
	public static final String WEBSITE_DERBY = "http://db.apache.org/derby";
	
	/**
	 * BrowserLauncher2 website
	 */
	public static final String WEBSITE_BROWSERLAUNCHER = "http://browserlaunch2.sourceforge.net/";
	
	/**
	 * Java CSV website
	 */
	public static final String WEBSITE_CSV = "http://sourceforge.net/projects/javacsv";
	
	/**
	 * KTable website
	 */
	public static final String WEBSITE_KTABLE = "http://sourceforge.net/projects/ktable";
	
	/**
	 * Region 0/region free. Value is 1 &lt;&lt; 8. 
	 */
	public static final int R0 = 1 << 8;
	
	/**
	 * Region 1/A. Value is 1.
	 */
	public static final int R1 = 1;
	
	/**
	 * Region 2/B. Value is 1 &lt;&lt; 1.
	 */
	public static final int R2 = 1 << 1;
	
	/**
	 * Region 3/C. Value is 1 &lt;&lt; 2.
	 */
	public static final int R3 = 1 << 2;
	
	/**
	 * Region 4. Value is 1 &lt;&lt; 3.
	 */
	public static final int R4 = 1 << 3;
	
	/**
	 * Region 5. Value is 1 &lt;&lt; 4.
	 */
	public static final int R5 = 1 << 4;
	
	/**
	 * Region 6. Value is 1 &lt;&lt; 5.
	 */
	public static final int R6 = 1 << 5;
	
	/**
	 * Region 7. Value is 1 &lt;&lt; 6.
	 */
	public static final int R7 = 1 << 6;
	
	/**
	 * Region 8. Value is 1 &lt;&lt; 7.
	 */
	public static final int R8 = 1 << 7;
	
	/**
	 * Sort ascending. Value is -1.
	 */
	public static final int SORT_ASCENDING = -1;
	
	/**
	 * Sort descending. Value is -2.
	 */
	public static final int SORT_DESCENDING = -2;
	
	/**
	 * Sort by movie ID. Value is 0.
	 */
	public static final int SORT_BY_ID = 0;
	
	/**
	 * Sort by title. Value is 1.
	 */
	public static final int SORT_BY_TITLE = 1;
	
	/**
	 * Sort by year. Value is 2.
	 */
	public static final int SORT_BY_YEAR = 2;
	
	/**
	 * Sort by format type. Value is 3;
	 */
	public static final int SORT_BY_FORMAT = 3;
	
	/**
	 * Sort by IMDb rating. Value is 4;
	 */
	public static final int SORT_BY_RATING = 4;
	
	/**
	 * Return value when the &quot;Save&quot; button in a message dialog is pressed. Value is 0.
	 */
	public static final int ANSWER_SAVE = 0;
	
	/**
	 * Return value when the &quot;Don't save&quot; button in a message dialog is pressed. Value is 1.
	 */
	public static final int ANSWER_DONT_SAVE = 1;
	
	/**
	 * Return value when the &quot;Cancel&quot; button in a message dialog is pressed. Value us 3.
	 */
	public static final int ANSWER_CANCEL = 2;
	
	/**
	 * Film
	 */
	public static final int MOVIETYPE_FILM = 0;
	
	/**
	 * Straight-to-video movie
	 */
	public static final int MOVIETYPE_VIDEOMOVIE = 1;
	
	/**
	 * TV-movie
	 */
	public static final int MOVIETYPE_TVMOVIE = 2;
	
	/**
	 * TV-series
	 */
	public static final int MOVIETYPE_TVSERIES = 3;
	
	/**
	 * TV miniseries
	 */
	public static final int MOVIETYPE_MINISERIES = 4;
	
	/**
	 * Movie serial
	 */
	public static final int MOVIETYPE_MOVIESERIAL = 5;
	
	/**
	 * Main program icon, 16x16 pixels
	 */
	public static final URL ICON_MAIN_16 = ClassLoader.getSystemResource("icon-nuovo/gnome-mime-video-16.png");
	
	/**
	 * Main program icon, 32x32 pixels
	 */
	public static final URL ICON_MAIN_32 = ClassLoader.getSystemResource("icon-nuovo/gnome-mime-video-32.png");
	
	/**
	 * Main program icon, 64x64 pixels
	 */
	public static final URL ICON_MAIN_64 = ClassLoader.getSystemResource("icon-nuovo/gnome-mime-video-64.png");
	
	/**
	 * Main program icon, 128x128 pixels
	 */
	public static final URL ICON_MAIN_128 = ClassLoader.getSystemResource("icon-nuovo/gnome-mime-video-128.png");
	
	/**
	 * Main program icon, 256x256 pixels
	 */
	public static final URL ICON_MAIN_256 = ClassLoader.getSystemResource("icon-nuovo/gnome-mime-video-256.png");
	
	/**
	 * Icon file for the main tab of the movie dialog
	 */
	public static final URL ICON_MOVIEDIALOG_MAINTAB = ClassLoader.getSystemResource("icon-silk/film.png");
	
	/**
	 * Icon file for the tagline/plot tab of the movie dialog
	 */
	public static final URL ICON_MOVIEDIALOG_TAGLINEPLOTTAB = ClassLoader.getSystemResource("icon-silk/page_white_text.png");
	
	/**
	 * Icon file for the actors tab of the movie dialog
	 */
	public static final URL ICON_MOVIEDIALOG_ACTORSTAB = ClassLoader.getSystemResource("icon-silk/group.png");
	
	/**
	 * Icon file for the format tab of the movie dialog
	 */
	public static final URL ICON_MOVIEDIALOG_FORMATTAB = ClassLoader.getSystemResource("icon-silk/cd.png");
	
	/**
	 * Icon file for the audio/subtitle tab of the movie dialog
	 */
	public static final URL ICON_MOVIEDIALOG_AUDIOSUBTAB = ClassLoader.getSystemResource("icon-silk/comments.png");
	
	/**
	 * Icon file for the "add film" action
	 */
	public static final URL ICON_ADDFILM = ClassLoader.getSystemResource("icon-silk/film_add.png");
	
	/**
	 * Icon file for the "add movie serial" action
	 */
	public static final URL ICON_ADDMOVIESERIAL = ClassLoader.getSystemResource("icon-silk/film_add.png");
	
	/**
	 * Icon file for the "add tv movie" action
	 */
	public static final URL ICON_ADDTVMOVIE = ClassLoader.getSystemResource("icon-silk/television_add.png");
	
	/**
	 * Icon file for the "add tv series" action
	 */
	public static final URL ICON_ADDTVSERIES = ClassLoader.getSystemResource("icon-silk/television_add.png");
	
	/**
	 * Icon file for the "add mini series" action
	 */
	public static final URL ICON_ADDMINISERIES = ClassLoader.getSystemResource("icon-silk/television_add.png");
	
	/**
	 * Icon file for the "add straight-to-video" action
	 */
	public static final URL ICON_ADDVIDEOMOVIE = ClassLoader.getSystemResource("icon-silk/cd_add.png");
	
	/**
	 * Icon file for the "save" action 
	 */
	public static final URL ICON_SAVE = ClassLoader.getSystemResource("icon-silk/disk.png");
	
	/**
	 * Icon file for the "save as" action
	 */
	public static final URL ICON_SAVEAS = ClassLoader.getSystemResource("icon-silk/disk_multiple.png");
	
	/**
	 * Icon file for the "new file" action
	 */
	public static final URL ICON_NEW = ClassLoader.getSystemResource("icon-silk/page_white.png");
	
	/**
	 * Icon file for the "open" action
	 */
	public static final URL ICON_OPEN = ClassLoader.getSystemResource("icon-silk/folder.png");
	
	/**
	 * Icon file for the "about" action
	 */
	public static final URL ICON_ABOUT = ClassLoader.getSystemResource("icon-silk/information.png");
	
	/**
	 * Icon file for the "help" action
	 */
	public static final URL ICON_HELP = ClassLoader.getSystemResource("icon-silk/help.png");
	
	/**
	 * Icon file for the "print" action
	 */
	public static final URL ICON_PRINT = ClassLoader.getSystemResource("icon-silk/printer.png");
	
	/**
	 * Icon file for the "preferences" action
	 */
	public static final URL ICON_PREFERENCES = ClassLoader.getSystemResource("icon-silk/cog.png");

	/**
	 * Exclamation mark icon
	 */
	public static final URL ICON_EXCLAMATION = ClassLoader.getSystemResource("icon-silk/exclamation.png");

	/**
	 * Edit database icon
	 */
	public static final URL ICON_DATABASE_EDIT = ClassLoader.getSystemResource("icon-silk/database_edit.png");
	
	/**
	 * Tick mark icon, 12x12 pixels
	 */
	public static final URL ICON_TICK12 = ClassLoader.getSystemResource("icon-silk/tick12.png");
	
	/**
	 * Icon for an "add" action
	 */
	public static final URL ICON_ADD = ClassLoader.getSystemResource("icon-silk/add.png");
	/**
	 * Icon for a delete/remove action
	 */
	public static final URL ICON_DELETE = ClassLoader.getSystemResource("icon-silk/delete.png");
	/**
	 * Icon with an arrow pointing up
	 */
	public static final URL ICON_UP = ClassLoader.getSystemResource("icon-silk/arrow_up.png");
	/**
	 * Icon with an arrow pointing down
	 */
	public static final URL ICON_DOWN = ClassLoader.getSystemResource("icon-silk/arrow_down.png");
	
	/**
	 * The image to display when the movie has no associated cover image.
	 */
	public static final URL NO_COVER_IMAGE = ClassLoader.getSystemResource("nocover.png");
	
	//TODO move this
	public static final String RECENT_FILES_PROPERTY_NAME = "RecentFiles";
	
	/**
	 * Timeout when downloading files from the internet (seconds)
	 */
	public static final int DOWNLOAD_TIMEOUT = 15;
	
	/**
	 * Number of times to retry a failed download
	 */
	public static final int DOWNLOAD_RETRY_COUNT = 3;

	public static final String TMDB_TYPE_MOVIE = "movie";
	public static final String TMDB_TYPE_TV = "tv";
	
	/**
	 * Converts a boolean to an int.
	 * @param b a boolean
	 * @return 1 if true, 0 if false
	 */
	public static int booleanToInt(boolean b) {
		if(b) return 1;
		else return 0;
	}
	
	/**
	 * Converts an int to a boolean
	 * @param i an int
	 * @return false if 0, true otherwise
	 */
	public static boolean intToBoolean(int i) {
		if(i==0) return false;
		else return true;
	}
	
	/**
	 * Converts an int to a 7-digit IMDb ID.
	 * @param i any int
	 * @return the IMDb ID, as a String
	 */
	public static String intToImdbId(int i) {
		if(i<0 || i>9999999)
			return null;
		
		String s = "" + i;
		while(s.length()>7)
			s = "0" + s;
		
		return s;
	}
	
	/**
	 * Check for valid image data.
	 * If the data is a valid, supported SWT image format, true is returned.
	 * If the data is invalid or unsupported, false is returned instead.
	 * @param imageBytes the bytes to check
	 * @return true or false
	 */
	public static boolean isValidImage(byte[] imageBytes) {
		try {
			new ImageData(new ByteArrayInputStream(imageBytes));
			return true;
		} catch(SWTException e) {
			return false;			
		}
	}
	
	/**
	 * Replaces special character codes (ISO/IEC 8859-1) in 
	 * strings extracted from HTML code with UTF characters
	 * @param string input
	 * @return The cleaned-up version of the input
	 */
	public static String fixHtmlCharacters(String string) {
		return NumericCharacterReference.decode(string, '_');
	}
	
	/**
	 * Scales an image to the specified max size while keeping the original aspect ratio.
	 * @param imageData the ImageData to scale 
	 * @param enlarge whether or not to enlarge the image if it is smaller than the specified size
	 * @param maxWidth the maximum width
	 * @param maxHeight the maximum width
	 * @return a scaled version of the image, in the form of an ImageData instance
	 */
	public static ImageData scaleImage(ImageData imageData, boolean enlarge, int maxWidth, int maxHeight) {
		if(imageData.width==maxWidth || imageData.height==maxHeight)
			return imageData;
		if(enlarge || imageData.width-maxWidth > 0 || imageData.height-maxHeight > 0) {
			float widthfactor = (float)maxWidth/(float)imageData.width;
			float heightFactor = (float)maxHeight/(float)imageData.height;
			if(widthfactor < heightFactor) {
				//return imageData.scaledTo(Math.round(imageData.width*widthfactor), Math.round(imageData.height*widthfactor));
				return Utils.resize(imageData, widthfactor);
			} else {
				return Utils.resize(imageData, heightFactor);
				//return imageData.scaledTo(Math.round(imageData.width*heightFactor), Math.round(imageData.height*heightFactor));
			}
		}
		return imageData;
	}
}
