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
import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import org.eclipse.swt.graphics.ImageData;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;


public class ImdbParser {
	private String html;
	
	/**
	 * The default contstructor
	 * @param html - a HTML document 
	 */
	protected ImdbParser(String html) {
		this.html = html;
	}
	
	/**
	 * Finds the movie's IMDb ID, if the open document is a movie page.
	 * The ID is usually already known, so this method is used only in certain special cases. 
	 * @return IMDb ID
	 */
	protected String getID() {
		Pattern patternID = Pattern.compile("<a\\s+href=\"/rg/title-top/boards/title/tt(\\d{7})/board\">");
		Matcher matcherID = patternID.matcher(html);
		if (matcherID.find())
			return matcherID.group(1);
		return null;
	}
	
	/**
	 * Returns the title of the movie, if the open document is a movie page.
	 * @return the movie title
	 */
	protected String getTitle() {
		Pattern patternTitle = Pattern.compile("<h1>([^<]+)<span>");
		Matcher matcherTitle = patternTitle.matcher(html);
		if (matcherTitle.find()) {
//			String title = matcherTitle.group(1);
//			if(title.endsWith(" "))
//				title.toupp
			return matcherTitle.group(1).trim();
		}
		return null;
	}
	
	/**
	 * Returns the movie's production year, if the open document is a movie page.
	 * @return a year
	 */
	protected int getYear() {
		Pattern patternYear = Pattern.compile("<a href=\"/Sections/Years/\\d{4}\">(\\d{4})</a>");
		Matcher matcherYear = patternYear.matcher(html);
		if (matcherYear.find()) {
			return Integer.parseInt(matcherYear.group(1));	
		}
		return 0;
	}
	
	/**
	 * Returns an array of the movie's genres, if the open document is a movie page.
	 * @return an array of genres, or an empty array if none were found.
	 */
	protected ArrayList<Genre> getGenres() {
		Pattern patternGenre = Pattern.compile("<a\\shref=\"/Sections/Genres/[a-zA-Z]+/\">([a-zA-Z]+)</a>");
		Matcher matcherGenre = patternGenre.matcher(html);
		ArrayList<Genre> temp = new ArrayList<Genre>();
		while (matcherGenre.find()) {
			temp.add(Genre.StringToEnum(matcherGenre.group(1)));
		}
		return temp;
	}
	
	/**
	 * Returns the movie's tagline, if the open document is a movie page.
	 * @return the tagline
	 */
	protected String getTagline() {
		Pattern patternTagline = Pattern.compile("<h5>Tagline:</h5>\\s*([^<]+)\\s*<a");
		Matcher matcherTagline = patternTagline.matcher(html);
		if (matcherTagline.find()) {
			return matcherTagline.group(1);	
		}
		return "";
	}
	
	/**
	 * Returns the movie's plot outline, if the open document is a movie page.
	 * @return the plot outline
	 */
	protected String getPlot() {
		Pattern patternPlot = Pattern.compile("<h5>Plot Outline:</h5>\\s*([^<]+)\\s*<a");
		Matcher matcherPlot = patternPlot.matcher(html);
		if (matcherPlot.find()) {
			//String plot = matcherPlot.group(1);
			return matcherPlot.group(1);
		}
		return "";
	}
	
	/**
	 * Returns the movie's IMDb rating, if the open document is a movie page.
	 * @return the rating
	 */
	protected double getRating() {
		Pattern patternRating = Pattern.compile("<b>User Rating:</b>\\s+<b>([0-9\\.]+)/10</b>");
		Matcher matcherRating = patternRating.matcher(html);
		if (matcherRating.find()) {
			return Double.valueOf(matcherRating.group(1)).doubleValue();	
		}
		return 0.0;
	}
	
	/**
	 * Returns the movie's runtime, if the open document is a movie page.
	 * @return runtime
	 */
	protected int getRuntime() {
		Pattern patternRuntime = Pattern.compile("<h5>Runtime:</h5>\\s*([0-9]+)\\smin\\s+</div>");
		Matcher matcherRuntime = patternRuntime.matcher(html);
		if(matcherRuntime.find())
			return Integer.valueOf(matcherRuntime.group(1));
		return 0;
	}
	
	/**
	 * Returns whether or not the movie has color, if the open document is a movie page.
	 * @return true if the movie is in color, false if it is black and white
	 */
	protected boolean isColor() {
		Pattern patternColor = Pattern.compile("<h5>Color:</h5>\\s*<a[^>]+>([^<]+)</a>");
		Matcher matcherColor = patternColor.matcher(html);
		if(matcherColor.find())
			if(matcherColor.group(1).equals("Black and White"))
				return false;
		return true;
	}
	
	/**
	 * Returns the movie's languages, if the open document is a movie page.
	 * @return an ArrayList of languages
	 */
	protected ArrayList<Language> getLanguages() {
		Pattern patternLanguage1 = Pattern.compile("<h5>Language:</h5>(.+?)</div>");
		Pattern patternLanguage2 = Pattern.compile("<a href=\"/Sections/Languages/([^/]+)/\">");
		Matcher matcherLanguage = patternLanguage1.matcher(html);
		
		ArrayList<Language> tempList = new ArrayList<Language>(); 
		
		if(matcherLanguage.find()) {
			matcherLanguage = patternLanguage2.matcher(matcherLanguage.group(1));

			while(matcherLanguage.find()) {
				tempList.add(Language.StringToEnum(matcherLanguage.group(1)));
			}
		}
			
		return tempList;
	}
	
	/**
	 * Returns the countries in which the movie is made, if the open document is a movie page.
	 * @return an ArrayList of countries
	 */
	protected ArrayList<Country> getCountries() {
		Pattern patternCountry1 = Pattern.compile("<h5>Country:</h5>(.+?)</div>");
		Pattern patternCountry2 = Pattern.compile("<a href=\"/Sections/Countries/([^/]+)/\">");
		Matcher matchercountry = patternCountry1.matcher(html);
		
		ArrayList<Country> tempList = new ArrayList<Country>(); 
		
		if(matchercountry.find()) {
			matchercountry = patternCountry2.matcher(matchercountry.group(1));

			while(matchercountry.find()) {
				tempList.add(Country.StringToEnum(matchercountry.group(1)));
			}
		}
			
		return tempList;
	}
	
	/**
	 * Returns the URL for the movie's poster image, if the open document is a movie page.
	 * @return the image URL
	 */
	protected String getImageURL() {
		Pattern pattern = Pattern.compile("<a name=\"poster\"[^>]+><img[^>]+src=\"(\\S+)\"[^>]+>");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	/**
	 * Downloads the poster image for the current movie
	 * @return image data
	 * @throws IOException
	 */
	protected ImageData getImageData() throws IOException {
		String url = getImageURL();
		
		if(url == null)
			return null;
		
		InputStream stream = new URL(url).openStream();
		ImageData imageData = new ImageData(stream);
		stream.close();
		
		return imageData;
	}
	
	/**
	 * Returns the movie's actors, if the open document is a movie page.
	 * @return an ArrayList of ActorInfo objects
	 */
	protected ArrayList<ActorInfo> getActors() {
		//TODO the following does not work:
		//<td class="char"><a href="/character/ch0008987/">Nick</a> (as Nephi Pomaikai Brown)</td>
		Pattern pattern = Pattern.compile("<td class=\"nm\"><a href=\"/name/nm(\\d+)/\">([^<>]+)</a></td><td class=\"ddd\">\\s\\.\\.\\.\\s</td><td class=\"char\">(<a href=\"[^\"]+\">)?([^<>]+)(</a>)?</td>");
		Matcher matcher = pattern.matcher(html);
		ArrayList<ActorInfo> templist = new ArrayList<ActorInfo>();
		int counter = 0;
		
		while (matcher.find()) {
			//System.out.println(matcher.group(1)+" "+matcher.group(2)+" "+matcher.group(4));
			templist.add(new ActorInfo(counter, new Person(matcher.group(1), CONST.fixHtmlCharacters(matcher.group(2))), CONST.fixHtmlCharacters(matcher.group(4))));
			counter++;
		}

		return templist;
	}
	
	/**
	 * Returns the movie's directors, if the open document is a movie page.
	 * @return an array of directors
	 */
	protected ArrayList<Person> getDirectors() {
		Pattern pattern = Pattern.compile("<h5>Directors?:</h5>(.+?)</div>");
		Matcher matcher = pattern.matcher(html);
		ArrayList<Person> personArray = new ArrayList<Person>();

		if(matcher.find()) {
			Pattern personPattern = Pattern.compile("<a\\shref=\"/name/nm(\\d{7})/\">([^<]+)</a>");
			Matcher personMatcher = personPattern.matcher(matcher.group(1));
			while(personMatcher.find()) {
				personArray.add(new Person(personMatcher.group(1), CONST.fixHtmlCharacters(personMatcher.group(2))));
			}
		}
		
		return personArray;
	}
	
	/**
	 * Returns the movie's writers, if the open document is a movie page.
	 * @return an array of writers
	 */
	protected ArrayList<Person> getWriters() {
		Pattern pattern = Pattern.compile("<h5>Writers?(\\s<a[^<]+</a>)?:</h5>(.+?)</div>");
		Matcher matcher = pattern.matcher(html);
		ArrayList<Person> personArray = new ArrayList<Person>();
		
		if(matcher.find()) {
			Pattern personPattern = Pattern.compile("<a\\shref=\"/name/nm(\\d{7})/\">([^<]+)</a>");
			Matcher personMatcher = personPattern.matcher(matcher.group(1));
			while(personMatcher.find()) {
				personArray.add(new Person(personMatcher.group(2), CONST.fixHtmlCharacters(personMatcher.group(2))));
			}
		}

		return personArray;
	}
	
	/**
	 * This method is called when a search result page should be parsed.
	 * This method will call the necessary private helper methods. 
	 * @return An array of search results
	 */
	protected ImdbSearchResult[] getSearchResults() {
		
		/*
		 * Sometimes a search only gives one result. In these cases IMDb bypasses the
		 * search result page and goes directly to the movie page. The following is
		 * to handle this special case.
		 */
		Pattern moviePagePattern = Pattern.compile("<div\\s+id=\"tn15\"\\s+class=\"maindetails\">");
		Matcher moviePageMatcher = moviePagePattern.matcher(html);
		if(moviePageMatcher.find()) {
			//TODO fix type and altTitles, these are currently hard-coded with default values
			return new ImdbSearchResult[]{new ImdbSearchResult(getID(), MovieType.film, getTitle(), "" + getYear(), new String[0])};
		}
		
		/*
		 * If we got this far, the current document is a search result page. We will now
		 * try to parse it and get the results
		 */
		
		Pattern pattern = Pattern.compile(
				"<tr>\\s*<td valign=\"top\">(.+?)</td>\\s*" +
				"<td align=\"right\" valign=\"top\">.+?</td>\\s*" +
				"<td valign=\"top\">(.+?)</td>\\s*</tr>"
				);
		Matcher matcher = pattern.matcher(html);
		
		ArrayList<ImdbSearchResult> templist = new ArrayList<ImdbSearchResult>(); 

		while(matcher.find()) {
			//Call a separate method to find the image URL, if any
			String imgUrl = imgSearchResult(matcher.group(1));
			
			//Call a method to parse the title, year, etc. This method will also return the ImdbSearchResult object.
			ImdbSearchResult result = titleSearchResult(matcher.group(2));
			
			//Add the image URL to the ImdbSearchResult object and add the ImdbSearchResult to the result list. 
			if(result != null) {
				result.setImageURL(imgUrl);
				templist.add(result);
			}
		}
		
		//We want to return an array, so the ArrayList must be converted
		if(templist.size() > 0) {
			return templist.toArray(new ImdbSearchResult[0]);
		}
		
		//if no results were found, return null
		return null;
	}
	
	/**
	 * Internal helper method, only called from getSearchResults()
	 * @param group - a substring of the HTML document
	 * @return an image URL
	 */
	private String imgSearchResult(String group) {
		Pattern pattern = Pattern.compile("<a[^>]+><img\\ssrc=\"([^\"]+)\"[^>]+>\\s*</a>");
		Matcher matcher = pattern.matcher(group);
		
		if(matcher.find()) {
			return matcher.group(1);
		}
		
		return null;
	}
	
	/**
	 * Internal helper method, only called from getSearchResults()
	 * @param group - a substring of the HTML document
	 * @return an ImdbSearchResult instance that will be added to the list of results 
	 */
	private ImdbSearchResult titleSearchResult(String group) {
		Pattern pattern = Pattern.compile(
				"<a\\shref=\"/title/tt(\\d{7})/\">([^<]+)</a>\\s\\((\\d{4})\\)\\s*([()A-Z]{0,6})(.*)\\z"
				);
		Matcher matcher = pattern.matcher(group);
		
		String title;
		String year;
		String id;
		MovieType type;
		ImdbSearchResult result = null;
		
		if(matcher.find()) {
			id = matcher.group(1);
			title = matcher.group(2);
			year = matcher.group(3);
			
			title = CONST.fixHtmlCharacters(title);
			
			if(title.startsWith("\"") && title.endsWith("\"")) {
				title = title.substring(1, title.length()-1);
				if(matcher.group(4).equals("(mini)"))
					type = MovieType.miniseries;
				else
					type = MovieType.tvseries;
			} 
			else if(matcher.group(4).equals("(TV)"))
				type = MovieType.tvmovie;
			else if(matcher.group(4).equals("(V)"))
				type = MovieType.videomovie;
			else if(matcher.group(4).equals("(VG)"))
				//Video games will be skipped
				return null;
			else
				type = MovieType.film;
			
			String[] altTitles = altTitleSearchResult(matcher.group(5));
			
			result = new ImdbSearchResult(id, type, title, year, altTitles);
		}
		return result;
	}
	
	/**
	 * Internal helper method, only called from titleSearchResult()
	 * @param group - a substring of the HTML document
	 * @return an array of alternative titles
	 */
	private String[] altTitleSearchResult(String group) {
		System.out.println(group);
		String[] titles = group.split("<br>&#160;aka\\s*<em>");
		
		ArrayList<String> templist = new ArrayList<String>(); 
		
		for(String title : titles) {
			System.out.println("title: "+title);
			title = title.replaceAll("<em>", "");
			title = title.replaceAll("</em>", "");
			title = CONST.fixHtmlCharacters(title);
			if(title.length() > 0)
				templist.add(title);
		}
		
		
		if(templist.size() > 0) {
			return templist.toArray(new String[0]);
		}
		return null;
	}
}
