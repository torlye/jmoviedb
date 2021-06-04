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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;


public class ImdbParser {
	private Document doc;
	private JSONObject json;
	
	protected ImdbParser(String html) {
		System.out.println("Parsing document");
		doc = Jsoup.parse(html);
		
		Elements container = doc.select("script[type=application/ld+json]");
		if (container.size() == 1) {
			String text = container.first().data();
			json = new JSONObject(text);
		}
	}
	
	/**
	 * Finds the movie's IMDb ID, if the open document is a movie page.
	 * The ID is usually already known, so this method is used only in certain special cases. 
	 * @return IMDb ID
	 */
	protected String getID() {
		Pattern patternID = Pattern.compile("/title/tt(\\d{7,8})/");
		Matcher matcherID = patternID.matcher(doc.baseUri());
		if (matcherID.find())
			return matcherID.group(1);
		return null;
	}
	
	/**
	 * Examples:
	 * The Avengers (2012)
	 * Mazes and Monsters (TV 1982)
	 * Star Trek (TV Series 1966&ndash;1969)
	 * &quot;Star Trek&quot; The Cage (TV episode 1986)
	 * Clone Wars: Bridging the Saga (Video 2005)
	 * Taken (TV mini-series 2002)
	 */
	private String getTitleProperty() {
		return doc.select("meta[property=og:title]").first().attr("content");
	}
	
	/**
	 * Returns the title of the movie, if the open document is a movie page.
	 * @return the movie title
	 */
	protected String getTitle() {
		String title = getTitleProperty();
		Pattern patternTitle = Pattern.compile("([^<\\(]+)");
		Matcher matcherTitle = patternTitle.matcher(title);
		if (matcherTitle.find()) {
			title = matcherTitle.group(1).trim();
			
			//Remove quote at beginning and end of title for TV-series
			if(title.startsWith("\"") && title.endsWith("\""))
				title = title.substring(1, title.length()-1);
			
			return title;
		}
		return null;
	}
	
	
	protected String getOriginalTitle() {
		String title = null;
		Element titleElement = doc.select("span[class=title-extra]").first();
		if (titleElement == null) return null;
		
		String text = titleElement.text();
		Pattern patternTitle = Pattern.compile("\"([^\"]+)\"");
		Matcher matcherTitle = patternTitle.matcher(text);
		if (matcherTitle.find())
			title = matcherTitle.group(1).trim();
		return title;
	}
	
	protected MovieType getType(MovieType currentType) {
		if(currentType == MovieType.webseries || currentType == MovieType.movieserial)
			return currentType;
		
		String title = getTitleProperty();
		String type = doc.select("meta[property=og:type]").first().attr("content");
		
		switch (type) {
		case "video.tv_show":
			if (title.contains("TV Series"))
				return MovieType.tvseries;
			else if (title.contains("(TV mini-series"))
				return MovieType.miniseries;
			else
				return MovieType.tvmovie;
		case "video.movie":
			if (title.contains("(Video"))
				return MovieType.videomovie;
			else
				return MovieType.film;
		default:
			break;
		}
		
		return currentType;
	}
	
	/**
	 * Returns the movie's production year, if the open document is a movie page.
	 * @return a year
	 */
	protected int[] getYear() {
		String title = getTitleProperty();
		Pattern patternYear = Pattern.compile("(\\d{4})\u2013(\\d{4})\\)");
		Matcher matcherYear = patternYear.matcher(title);
		if (matcherYear.find()) {
			return new int[] {
					Integer.parseInt(matcherYear.group(1)),
					Integer.parseInt(matcherYear.group(2))
			};
		}
		patternYear = Pattern.compile("(\\d{4})\\)");
		//patternYear = Pattern.compile("\\d{4}");
		matcherYear = patternYear.matcher(title);
		if (matcherYear.find()) {
			return new int[] {Integer.parseInt(matcherYear.group(1))};
		}
		return new int[0];
	}
	
	/**
	 * Returns an array of the movie's genres, if the open document is a movie page.
	 * @return an array of genres, or an empty array if none were found.
	 */
	protected ArrayList<Genre> getGenres() {
		ArrayList<Genre> temp = new ArrayList<Genre>();
		
		Elements h4nodes = doc.select("h4");
		for (Element h4 : h4nodes) {
			if (h4.text().equals("Genres:"))
			{
				Elements genreNodes = h4.parent().select("a[href^=/search/title?genres]");
				for (Element element : genreNodes) {
					temp.add(Genre.StringToEnum(element.text()));
				}
			}
		}
		return temp;
	}
	
	/**
	 * Returns the movie's tagline, if the open document is a movie page.
	 * @return the tagline
	 */
	protected String getTagline() {
		Element h4 = doc.select("h4:matchesOwn(Taglines:)").first();
		if (h4 == null) return "";
		String text = h4.parent().ownText();
		return text;
	}
	
	/**
	 * Returns the movie's plot outline, if the open document is a movie page.
	 * @return the plot outline
	 */
	protected String getPlot() {
		Element h2 = doc.select("h2:matchesOwn(Storyline)").first();
		if (h2 == null) return "";
		Element el = h2.siblingElements().select("p").first();
		if (el == null) return "";
		return el.text();
	}
	
	/**
	 * Returns the movie's IMDb rating, if the open document is a movie page.
	 * @return the rating
	 */
	protected double getRating() {
		String starText = doc.select("[itemprop=ratingValue]").first().text();
		if (!starText.isEmpty())
			return Double.valueOf(starText).doubleValue();
		else
			return 0;
	}
	
	/**
	 * Returns the movie's runtime, if the open document is a movie page.
	 * @return runtime
	 */
	protected int getRuntime() {
		String time = doc.select("time[datetime]").attr("datetime");
		if (time.length()>0) {
			Pattern patternRuntime = Pattern.compile("PT([0-9]+)M");
			Matcher matcherRuntime = patternRuntime.matcher(time);
			if(matcherRuntime.find())
				return Integer.valueOf(matcherRuntime.group(1));
		}
		return 0;
	}
	
	/**
	 * Returns whether or not the movie has color, if the open document is a movie page.
	 * @return true if the movie is in color, false if it is black and white
	 */
	protected boolean isColor() {
		Element h4 = doc.select("h4:matchesOwn(Color:)").first();
		Element anchor = h4.parent().select("a").first();
		if (anchor.text().contains("Black and White")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the movie's languages, if the open document is a movie page.
	 * @return an ArrayList of languages
	 */
	protected ArrayList<Language> getLanguages() throws UnknownLanguageException {
		Elements languageElements = doc.select("a[href~=primary_language]");
		
		ArrayList<Language> tempList = new ArrayList<Language>();
		
		for (Element element : languageElements) {
			Language l = Language.StringToEnum(element.text());
			if(l == null)
				System.err.println("Unknown language: " + element.text());
			else {
				if(!tempList.contains(l)) tempList.add(l);
			}
		}
		
		return tempList;
	}
	
	/**
	 * Returns the countries in which the movie is made, if the open document is a movie page.
	 * @return an ArrayList of countries
	 */
	protected ArrayList<Country> getCountries() {
		Elements countryElements = doc.select("a[href~=country_of_origin]");
		
		ArrayList<Country> tempList = new ArrayList<Country>(); 
		
		for (Element element : countryElements) {
			Country c = Country.StringToEnumUsingName(element.text());
			if (c != null)
				tempList.add(c);
			else
				System.err.println("Unrecognized country "+element.text());
		}
			
		return tempList;
	}
	
	/**
	 * Returns the URL for the movie's poster image, if the open document is a movie page.
	 * @return the image URL, or null if no image was found.
	 */
	protected URL getImageURL() {
		String urlString = doc.select("img[title$=Poster]").attr("src");
		if (urlString.length()>0) {
			if (urlString.endsWith("imdb-share-logo.png"))
				return null;
			try {
				return new URL(urlString);
			} catch (MalformedURLException e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Returns the movie's actors, if the open document is a movie page.
	 * @return an ArrayList of ActorInfo objects
	 */
	protected ArrayList<ActorInfo> getActors() {
		ArrayList<ActorInfo> templist = new ArrayList<ActorInfo>();
		Element castListTable = doc.select("table[class=cast_list]").first();
		if (castListTable == null) return templist;
		Elements rows = castListTable.select("tr");
		
		int counter = 0;
		
		for (Element row : rows) {
			Elements cells = row.select("td");
			if (cells.size() < 4)
				continue;
			String name = cells.get(1).text();
			String href = cells.get(1).select("a").attr("href");
			Pattern patternId = Pattern.compile("/name/nm([0-9]+)");
			Matcher matcherId = patternId.matcher(href);
			String character = row.select("td[class=character]").text();
			
			if(matcherId.find()) {
				String personId = matcherId.group(1);
				templist.add(new ActorInfo(counter, new Person(personId, name), character));
				counter++;
			}
		}

		return templist;
	}
	
	/**
	 * Returns the movie's directors, if the open document is a movie page.
	 * @return an array of directors
	 */
	protected ArrayList<Person> getDirectors() {
		ArrayList<Person> personArray = new ArrayList<Person>();

		try {
			JSONArray array = json.getJSONArray("director");
			for (Object object : array) {
				if (object instanceof JSONObject) {
					Person p = getPerson((JSONObject)object);
					if (p != null)
						personArray.add(p);
				}
			}
		}
		catch (JSONException e) {
			try {
				JSONObject director = json.getJSONObject("director");
				Person p = getPerson(director);
				if (p != null)
					personArray.add(p);
			}
			catch (JSONException e2) {}
		}
		
		return personArray;
	}
	
	private static Person getPerson(JSONObject jsonObj) {
		try {
			String type = jsonObj.getString("@type");
			String url = jsonObj.getString("url");
			String name = jsonObj.getString("name");
			
			if (type.equals("Person")) {
				Pattern patternId = Pattern.compile("/name/nm([0-9]+)");
				Matcher matcherId = patternId.matcher(url);
				
				if(matcherId.find()) {
					return new Person(matcherId.group(1), name);
				}
			}
		} catch (JSONException e) {
		}
		return null;
	}
	
	/**
	 * Returns the movie's writers, if the open document is a movie page.
	 * @return an array of writers
	 */
	protected ArrayList<Person> getWriters() {
		Elements headers = doc.select("h4.inline");
		ArrayList<Person> personArray = new ArrayList<Person>();
		for(Element h : headers)
		{
			if (h.text().equals("Writer:") || h.text().equals("Writers:"))
			{
				for(Element a : h.siblingElements().select("a")) {
					String name = a.text();
					String href = a.attr("href");
					Pattern patternId = Pattern.compile("/name/nm([0-9]+)");
					Matcher matcherId = patternId.matcher(href);
					
					if(matcherId.find()) {
						Person p = new Person(matcherId.group(1), name);
						if(!personArray.contains(p))
							personArray.add(p);
					}
				}
				break;
			}
		}
		
		return personArray;
	}
	
	/**
	 * This method is called when a search result page should be parsed.
	 * This method will call the necessary private helper methods. 
	 * @return An array of search results, or null if no results were found.
	 */
	protected ImdbSearchResult[] getSearchResults() {
		
		/*
		 * Sometimes a search only gives one result. In these cases IMDb bypasses the
		 * search result page and goes directly to the movie page. The following is
		 * to handle this special case.
		 */
		if(doc.select("h1").size() > 0) {
			getTitleProperty();
			if(CONST.DEBUG_MODE) System.out.println("Only one search result, was redirected to movie page");
			return new ImdbSearchResult[]{new ImdbSearchResult(getID(), MovieType.film, getTitle(), "" + getYear(), new String[0])};
		}
		
		/*
		 * If we got this far, the current document is a search result page. We will now
		 * try to parse it and get the results
		 */
		
		Elements rows = doc.select("table tr");
		
		ArrayList<ImdbSearchResult> templist = new ArrayList<ImdbSearchResult>();
		
		Pattern hrefPattern = Pattern.compile("/title/tt(\\d+)");
		
		for (Element row : rows) {
			Elements cells = row.select("td");
			if (cells.size() != 3) continue;
			
			String href = cells.last().select("a").first().attr("href");
			
			Matcher matcher = hrefPattern.matcher(href);
			if (matcher.find()) {
				StringBuilder builder = new StringBuilder();
				List<Node> nodes = cells.last().childNodes();
				for (Node node : nodes) {
					if (node instanceof TextNode)
						builder.append(((TextNode) node).text());
					else if (node instanceof Element) {
						Elements pElements = ((Element) node).getElementsByTag("p");
						if (pElements.size()>0) {
							for (Element element : pElements) {
								builder.append("\n");
								builder.append(element.text());
							}
						} else {
							builder.append(((Element) node).text());
						}
					}
				}
				List<TextNode> texts = cells.last().textNodes();
				
				for (TextNode textNode : texts) {
					if (!textNode.isBlank() && textNode.nodeName().equals("p"))
						builder.append("\n"+textNode.text());
					else
						builder.append(textNode.text());
				}
				
				ImdbSearchResult result = new ImdbSearchResult(matcher.group(1), builder.toString());
				
				Element img = cells.first().select("img").first();
				if (img != null) {
					String src = img.attr("src");
					if (src.endsWith("/b.gif")) {
						
					} else {
						result.setImageURL(src);
					}
				}
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
				"<a\\shref=\"/title/tt(\\d{7})/\">([^<]+)</a>\\s\\((\\d{4})[IVX/]*\\)\\s*([()A-Z]{0,6})(.*)\\z"
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
		Pattern pattern = Pattern.compile("&#160;aka\\s*<em>(.+?)((<br>)|\\z)");
		Matcher matcher = pattern.matcher(group);
		
		ArrayList<String> templist = new ArrayList<String>();
		
		while (matcher.find()) {
			String title = matcher.group(1);
			title = title.replaceAll("<.+?>", "");
			
			if(title.length() > 0)
				templist.add(title);
		}
				
		if(templist.size() > 0) {
			return templist.toArray(new String[0]);
		}
		return null;
	}
}
