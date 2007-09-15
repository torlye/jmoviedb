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
import java.net.URL;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.graphics.ImageData;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.gui.SearchResultDialog;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ImdbWorker {
	
	/**
	 * This method is used whenever the IMDb URL is unknown. It searches and downloads the results.
	 * @param searchString - the title to search for
	 * @return search results
	 * @throws IOException
	 */
	private static ImdbSearchResult[] search(String searchString) throws IOException {
		String searchURL = Settings.getSettings().getImdbSearchUrl();
		URL url = new URL(searchURL + searchString.replace(" ", "+"));
		ImdbParser parser = new HtmlReader(url).download();
		return parser.getSearchResults();
	}
	
	/**
	 * Updates the given movie with new data from IMDb. Presents search results (if any) in a nice dialog. 
	 * @param shell - parent shell 
	 * @param movie - the movie to update
	 * @return the updated movie
	 * @throws IOException
	 */
	public static AbstractMovie update(IShellProvider shell, AbstractMovie movie) throws IOException { //TODO dialog instead of throwing an exception
		if(!movie.isImdbUrlValid()) {
			/*
			 * We end up here if the movie doesn't have an IMDb URL yet, or if it is malformed.
			 */
			
			//Can't search if the movie has no title
			if(movie.getTitle() == null || movie.getTagline().length() == 0)
				return null; //TODO spawn a dialog instead
			
			ImdbSearchResult[] searchResults = ImdbWorker.search(movie.getTitle());
			
			//No search results found!
			if(searchResults.length == 0) {
				return null; //TODO spawn a dialog instead
			}
			
			int selection = new SearchResultDialog(shell, searchResults).open();
			
			//The search result dialog was cancelled
			if(selection == -1)
				return null;
			
			//Update the movie with the new ID
			movie.setImdbID(searchResults[selection].getImdbId());
		}
		URL url = new URL(movie.getImdbUrl());
		
		
		ImdbParser parser = new HtmlReader(url).download();
		movie.setTitle(parser.getTitle());
		movie.setYear(parser.getYear());
		movie.setRunTime(parser.getRuntime());
		movie.setRating(parser.getRating());
		movie.setColor(parser.isColor());
		movie.setPlotOutline(parser.getPlot());
		movie.setTagline(parser.getTagline());
		
		movie.setImdbLanguages(parser.getLanguages());
		movie.setCountries(parser.getCountries());
		movie.setGenres(parser.getGenres());
		movie.setDirectors(parser.getDirectors());
		movie.setWriters(parser.getWriters());
		movie.setActors(parser.getActors());
		
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
			ImageData data = parser.getImageData();
			System.out.println("Image size: " + data.width + "x" + data.height);
			
			System.out.println("------- END IMDB DATA DUMP -------");
			System.out.println("");
		}
		//TODO
		return movie;
	}
}
