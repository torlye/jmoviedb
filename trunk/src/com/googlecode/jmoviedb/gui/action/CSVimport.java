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
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.Film;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.csvreader.CsvReader;

/**
 * Worker class for importing data from CSV files exported from Moviedb.
 * This class implements IRunnableWithProgress in order to work with
 * org.eclipse.jface.dialogs.ProgressMonitorDialog
 * @author tor
 *
 */
public class CSVimport implements IRunnableWithProgress {
	
	private String filePath;
	private int read;
	private int skipped;
	
	/**
	 * The default constructor.
	 * @param - filePath the path in which the CSV file is found.
	 */
	CSVimport(String filePath) {
		this.filePath = filePath;
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
			progressMonitor.beginTask("Scanning CSV file", IProgressMonitor.UNKNOWN);
			
			CsvReader reader1 = new CsvReader(filePath);
			CsvReader reader2 = new CsvReader(filePath);
			
			//Checking the number of records in the CSV file. This is used for the progress bar.
			int numRecords = 0;
			while(reader1.skipRecord())
				numRecords++;
			reader1 = null;
			
			progressMonitor.subTask("Importing");
			
			read = 0;
			skipped = 0;
			
			ArrayList<Film> importedFilms = new ArrayList<Film>();
			
			while(reader2.readRecord()) {
				
				//Cancel if the cancel button is pressed
				if(progressMonitor.isCanceled())
					throw new InterruptedException();
				
				if(reader2.getColumnCount() != 14) {
					skipped++;
					if(CONST.DEBUG_MODE)
						System.out.println("Skipping record with ID " + 
								reader2.get(0) + ", " + skipped + "so far");
				}
				else {
					Film f = new Film(reader2.get(0), reader2.get(1), reader2.get(2), reader2.get(3), reader2.get(4), 
							reader2.get(5), reader2.get(6), reader2.get(7), reader2.get(8), reader2.get(9),
							reader2.get(10), reader2.get(11), reader2.get(12), reader2.get(13));
					progressMonitor.subTask(reader2.get(1));
					System.out.println(reader2.get(0));
					read++;
					importedFilms.add(f);
				}
				progressMonitor.worked(1);
			}
			
			reader2.close();
			reader2 = null;
			
			progressMonitor.subTask("Adding movies to database");
			for(Film f : importedFilms) {
				MainWindow.getMainWindow().getDB().saveMovie(f, null);
				progressMonitor.worked(1);
			}
			
			progressMonitor.done();
			
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
		}
	}
	
	/**
	 * Returns the number of records that were read during the CSV import operation,
	 * or 0 if it has not yet been run.
	 * @return the number of records that were read
	 */
	public int getNumberOfReadRecords() {
		return read;
	}
	
	/**
	 * Returns the number of records that were skipped during the CSV import operation,
	 * or 0 if it has not yet been run.
	 * @return the number of records that were skipped
	 */
	public int getNumberOfSkippedRecords() {
		return skipped;
	}
}