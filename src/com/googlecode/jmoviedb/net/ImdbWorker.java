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

package com.googlecode.jmoviedb.net;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.management.monitor.Monitor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.gui.SearchResultDialog;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ImdbWorker implements IRunnableWithProgress {
	private AbstractMovie movie;
	private Shell parentShell;
	private ImdbSearchResult[] searchResults;
	
	public ImdbWorker(Shell parentShell, AbstractMovie movie)  {
		this.movie = movie;
		this.parentShell = parentShell;
	}
	
	public void run(IProgressMonitor arg0) throws InvocationTargetException, InterruptedException {
		try {
			this.movie = update();
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
	}
		
	/**
	 * Updates the given movie with new data from IMDb. Presents search results (if any) in a nice dialog. 
	 * @param shell - parent shell 
	 * @param movie - the movie to update
	 * @return the updated movie
	 * @throws IOException
	 */
	public AbstractMovie update() throws IOException { //TODO dialog instead of throwing an exception
		if(!movie.isImdbUrlValid()) {
			/*
			 * We end up here if the movie doesn't have an IMDb URL yet, or if it is malformed.
			 */
			
			//Can't search if the movie has no title
			if(movie.getTitle() == null || movie.getTitle().length() == 0) {
				MessageDialog.openInformation(parentShell, "Missing information", "You must enter a title or an IMDb URL before you can download information.");
				return movie;
			}
			
			//Run search with progress bar
			try {
				new ProgressMonitorDialog(parentShell).run(true, false, new ImdbSearcher());
			} catch (InvocationTargetException e) {
				// handle exception
			} catch (InterruptedException e) {
				// handle cancellation
			}
			
			//No search results found!
			if(searchResults == null) {
				MessageDialog.openInformation(parentShell, "No search results", "The search returned no results.");
				return movie;
			}
			
			//Show a list of search results and let the user choose
			SearchResultDialog dialog = new SearchResultDialog(parentShell, searchResults);
			int selection = dialog.open();
			dialog.dispose();

			//The search result dialog was cancelled
			if(selection == -1)
				return movie;

			//Update the movie with the new ID
			movie.setImdbID(searchResults[selection].getImdbId());
		}
		
		//At this point we have a valid IMDb URL
		try {
			new ProgressMonitorDialog(parentShell).run(true, false, new ImdbDownloader());
		} catch (InvocationTargetException e) {
			// handle exception
		} catch (InterruptedException e) {
			//Not used
		}
		return movie;
	}
	
	private class ImdbSearcher implements IRunnableWithProgress {
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				monitor.beginTask("IMDb search", IProgressMonitor.UNKNOWN);
				String searchURL = Settings.getSettings().getImdbSearchUrl();
				URL url = new URL(searchURL + movie.getTitle().replace(" ", "+"));
				ImdbParser parser = new ImdbParser(new DownloadWorker(url).downloadHtml());
				searchResults = parser.getSearchResults();
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}

		}
	}
	
	private class ImdbDownloader implements IRunnableWithProgress {
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				monitor.beginTask("Importing information from IMDb", IProgressMonitor.UNKNOWN);
				URL url = new URL(movie.getImdbUrl());
				
				monitor.subTask("Downloading");
				ImdbParser parser = new ImdbParser(new DownloadWorker(url).downloadHtml());
				
				monitor.subTask("Importing data");
				movie.setTitle(parser.getTitle());
				movie.setYear(parser.getYear());
				movie.setRunTime(parser.getRuntime());
				movie.setRating(parser.getRating());
				movie.setColor(parser.isColor());
				movie.setPlotOutline(parser.getPlot());
				movie.setTagline(parser.getTagline());

				movie.setLanguages(parser.getLanguages());
				movie.setCountries(parser.getCountries());
				movie.setGenres(parser.getGenres());
				movie.setDirectors(parser.getDirectors());
				movie.setWriters(parser.getWriters());
				movie.setActors(parser.getActors());

				monitor.subTask("Downloading cover image");
				movie.setImageBytes(new DownloadWorker(parser.getImageURL()).downloadBytes());

				if(CONST.DEBUG_MODE) {
					System.out.println("");
					System.out.println("------- BEGIN IMDB DATA DUMP -------");
					System.out.println("Title: " + parser.getTitle());
					System.out.println("Year: " + parser.getYear());
					System.out.println("Runtime: " + parser.getRuntime());
					System.out.println("Rating: " + parser.getRating());
					System.out.println("Color: " + parser.isColor());
					System.out.println("Tagline: " + parser.getTagline());
					System.out.println("Plot outline: " + parser.getPlot());

					String languages = "Languages:";
					for(Language l : parser.getLanguages())
						languages += " " + l.getName();
					System.out.println(languages);

					String countries = "Countries:";
					for(Country c : parser.getCountries())
						countries += " " + c.getName();
					System.out.println(countries);

					String genres = "Genres:";
					for(Genre g : parser.getGenres())
						genres += " " + g;
					System.out.println(genres);			

					String directors = "Directors:";
					for(Person d : parser.getDirectors())
						directors += " " + d.getName();
					System.out.println(directors);

					String writers = "Writers:";
					for(Person w : parser.getWriters())
						writers += " " + w.getName();
					System.out.println(writers);

					String actors = "\nActors:";
					for(ActorInfo a : parser.getActors())
						actors += "\n" + a.toString();
					System.out.println(actors + "\n");

					System.out.println("Image URL: " + parser.getImageURL());
					try{
						ImageData data = movie.getImageData();
						System.out.println("Image size: " + data.width + "x" + data.height);
					} catch(NullPointerException e) {
						System.out.println("No image!");
					}

					System.out.println("------- END IMDB DATA DUMP -------");
					System.out.println("");
				}

			} catch (Exception e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}
		}
	}
}
