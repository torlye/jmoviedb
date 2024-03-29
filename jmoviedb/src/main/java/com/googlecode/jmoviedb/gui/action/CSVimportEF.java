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

package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.AudioChannels;
import com.googlecode.jmoviedb.enumerated.AudioCodec;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.movietype.Film;

import org.eclipse.core.runtime.IProgressMonitor;

import com.csvreader.CsvReader;

/**
 * Worker class for importing data from CSV files exported from E.F.'s Access database.
 * This class implements IRunnableWithProgress in order to work with
 * org.eclipse.jface.dialogs.ProgressMonitorDialog
 * @author tor
 *
 */
public class CSVimportEF extends CSVimport {
	
	/**
	 * The default constructor.
	 * @param - filePath the path in which the CSV file is found.
	 */
	CSVimportEF(String filePath) {
		super(filePath);
	}
	
	/**
	 * An implementation of the run method in IRunnableWithProgress.
	 * Runs this operation. Progress is reported to the given progress monitor. 
	 * This method is invoked by an ProgressMonitorDialog's run method, which supplies 
	 * the progress monitor. A request to cancel the operation is honored and 
	 * acknowledged by throwing InterruptedException.
	 * 
	 * @param progressMonitor - the progress monitor to use to display progress 
	 * and receive requests for cancellation
	 * 
	 * @throws InvocationTargetException - if the run method must propagate a checked 
	 * exception, it wraps it inside an InvocationTargetException
	 *  
	 * @throws InterruptedException - if the operation detects a request to cancel, 
	 * using IProgressMonitor.isCanceled(), it exits by throwing InterruptedException
	 * 
	 */
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
		try {
			
			CsvReader reader1 = new CsvReader(filePath, ';');
			CsvReader reader2 = new CsvReader(filePath, ';');
			
			//Checking the number of records in the CSV file. This is used for the progress bar.
			int numRecords = 0;
			while(reader1.skipRecord())
				numRecords++;
			reader1 = null;
			
			progressMonitor.beginTask("Importing", numRecords*2);
			
			read = 0;
			skipped = 0;
			
			ArrayList<Film> importedFilms = new ArrayList<Film>();
			reader2.skipLine(); // skip header row
			
			while(reader2.readRecord()) {
				
				//Cancel if the cancel button is pressed
				if(progressMonitor.isCanceled())
					throw new InterruptedException();
				
				if(reader2.getColumnCount() != 11) {
					skipped++;
					if(CONST.DEBUG_MODE)
						System.out.println("Skipping record with ID " + 
								reader2.get(0) + ", " + skipped + "so far");
				}
				else {
					try
					{
						Film f = new Film(null, //id 
								reader2.get(1), //title
								reader2.get(0), //format
								"", //imdb id
								reader2.get(4), //director 
								reader2.get(5), //genre 
								null, //tagline
								null, //plot 
								null, //comment
								null, //country
								reader2.get(3), //year 
								reader2.get(8), //runtime
								null, //rate
								""); //sublanguages
						f.setCustomTitle(reader2.get(2));
						f.setSeen(reader2.get(9).equals("1"));
						f.setAudioTracks(getAudioTrackInfo(reader2.get(6)));
						f.setGenres(getGenreInfo(reader2.get(5)));
						f.setNotes(reader2.get(7) + "\n" + reader2.get(10));
						
						importedFilms.add(f);
						progressMonitor.subTask(reader2.get(1));
						System.out.println(reader2.get(0));
						read++;
					}
					catch (Exception e) {
						skipped++;
						if(CONST.DEBUG_MODE)
							System.out.println("Skipping record with ID " + 
									reader2.get(0) + ", " + skipped + "so far.\n"+
									e.getMessage());
					}
				}
				progressMonitor.worked(1);
			}
			
			reader2.close();
			reader2 = null;
			
			progressMonitor.subTask("Adding movies to database");
			for(Film f : importedFilms) {
				MainWindow.getMainWindow().getDB().saveBackground(f);
				progressMonitor.worked(1);
			}
			
			MainWindow.getMainWindow().getDB().updateModel();
			
		} 
		/* Because the last catch clause should catch any exception other than
		 * InterruptedException, any InterruptedExceptions have to be caught and
		 * re-thrown.
		 */
		catch (InterruptedException e) {
			throw e;
		} 
		// Any other exceptions should spawn an InvocationTargetException.
		catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			progressMonitor.done();
		}
	}

	private static ArrayList<AudioTrack> getAudioTrackInfo(String audioString) {
		ArrayList<AudioTrack> list = new ArrayList<AudioTrack>();
		if (audioString != null && audioString.length() > 0) {
			Language lang = null;
			switch(audioString) {
				case "Engelsk":
					lang = Language.english;
					break;
				case "Dansk":
					lang = Language.danish;
					break;
				case "Fransk":
					lang = Language.french;
					break;
				case "Svensk":
					lang = Language.swedish;
					break;
				case "Norsk":
					lang = Language.norwegian;
					break;
				case "Tysk":
					lang = Language.german;
					break;
				case "Japansk":
					lang = Language.japanese;
					break;
				case "Czech":
					lang = Language.czech;
					break;
			}
			if (lang != null) {
				list.add(new AudioTrack(lang, AudioCodec.other, AudioChannels.none, false, false, "", "", ""));
			}
		}
		
		return list;
	}

	private static ArrayList<Genre> getGenreInfo(String genreString) {
		ArrayList<Genre> list = new ArrayList<Genre>();
		if (genreString != null && genreString.length() > 0) {
			Genre g = null;
			switch(genreString) {
				case "Animation":
				case "Tegnefilm":
				case "Stop-Motion":
					g = Genre.animation;
					break;
				case "Dokumentar":
					g = Genre.documentary;
					break;
			}
			if (g != null) {
				list.add(g);
			}
		}
		
		return list;
	}
}