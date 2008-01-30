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

package com.googlecode.jmoviedb.model.movietype;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.enumerated.AspectRatio;
import com.googlecode.jmoviedb.enumerated.ContainerFormat;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.DiscType;
import com.googlecode.jmoviedb.enumerated.FilmVersion;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.Resolution;
import com.googlecode.jmoviedb.enumerated.TVsystem;
import com.googlecode.jmoviedb.enumerated.VideoCodec;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.Person;
import com.googlecode.jmoviedb.model.SubtitleTrack;


public abstract class AbstractMovie implements Cloneable {

	//imdb data
	private String imdbID;
	private String title;
	private int year;
	private double rating;
	private String plotOutline;
	private String tagline;
	private boolean color;
	private int runTime;
	private ArrayList<Genre> genres;
	private ArrayList<Country> countries;
	private ArrayList<Language> imdbLanguages;
	private ArrayList<Person> directors;
	private ArrayList<Person> writers;
	private ArrayList<ActorInfo> actors;

	private int ID;
	private String customTitle;
	private String notes;
	private FilmVersion version;
	private String customVersion;
	private String sceneReleaseName;
	private boolean legal;
	private boolean seen;
	private boolean myEncode;
	private String location;
	
	private FormatType format;
	private ContainerFormat container;
	private DiscType disc;
	private VideoCodec video;
	private TVsystem tvSystem;
	private ArrayList<AudioTrack> audioTracks;
	private ArrayList<SubtitleTrack> subtitles;
	private Resolution resolution;
	private AspectRatio aspectRatio;
	private boolean[] dvdRegion;
	
	private byte[] imageBytes;
	
	/**
	 * Database constructor, to be used when loading movies from the database.
	 * @param id
	 * @param imdbID
	 * @param title
	 * @param customTitle
	 * @param year
	 * @param rating
	 * @param plotOutline
	 * @param tagline
	 * @param color
	 * @param runTime
	 * @param notes
	 * @param version
	 * @param customVersion
	 * @param legal
	 * @param seen
	 * @param formatID
	 * @param discTypeID
	 * @param videoCodecID
	 * @param myEncode
	 * @param dvdRegion
	 * @param tvSystem
	 * @param sceneReleaseName
	 * @param resolutionID
	 * @param aspectID
	 * @param cover
	 */
	public AbstractMovie(
			int id,
			String imdbID,
			String title,
			String customTitle,
			int year,
			int rating, 
			String plotOutline, 
			String tagline, 
			int color, 
			int runTime, 
			String notes,
			int version,
			String customVersion,
			int legal,
			int seen,
			int formatID,
			int discTypeID,
			int videoCodecID,
			int myEncode,
			int dvdRegion,
			int tvSystem,
			String sceneReleaseName,
			int resolutionID,
			int aspectID) {
		this();
		setID(id);
		setImdbID(imdbID);
		setTitle(title);
		setCustomTitle(customTitle);
		setYear(year);
		setRatingAsInt(rating);
		setPlotOutline(plotOutline);
		setTagline(tagline);
		setColor(CONST.intToBoolean(color));
		setRunTime(runTime);
		setNotes(notes);
		setVersion(FilmVersion.intToEnum(version));
		setCustomVersion(customVersion);
		setLegal(CONST.intToBoolean(legal));
		setSeen(CONST.intToBoolean(seen));
		setFormat(FormatType.intToEnum(formatID));
		setDisc(DiscType.intToEnum(discTypeID));
		setVideo(VideoCodec.intToEnum(videoCodecID));
		setMyEncode(CONST.intToBoolean(myEncode));
		setDvdRegionAsInt(dvdRegion);
		setTvSystem(TVsystem.intToEnum(tvSystem));
		setSceneReleaseName(sceneReleaseName);
		setResolution(Resolution.intToEnum(resolutionID));
		setAspectRatio(AspectRatio.intToEnum(aspectID));
		setImageBytes(null);
	}
	
	/**
	 * The default constructor. All attributes are set to default values.
	 *
	 */
	protected AbstractMovie() {
		this.imdbID = "";
		this.title = "";
		this.year = 0;
		this.rating = 0.0;
		this.plotOutline = "";
		this.tagline = "";
		this.color = true;
		this.runTime = 0;
		this.genres = new ArrayList<Genre>();
		this.countries = new ArrayList<Country>();
		this.imdbLanguages = new ArrayList<Language>();
		this.directors = new ArrayList<Person>();
		this.writers = new ArrayList<Person>();
		this.actors = new ArrayList<ActorInfo>();
		this.ID = -1;
		this.customTitle = "";
		this.notes = "";
		this.version = FilmVersion.unspecified;
		this.customVersion = "";
		this.legal = true;
		this.seen = false;
		this.format = FormatType.other;
		this.container = ContainerFormat.other;
		this.disc = DiscType.other;
		this.video = VideoCodec.other;
		this.audioTracks = new ArrayList<AudioTrack>();
		this.subtitles = new ArrayList<SubtitleTrack>();
		this.sceneReleaseName = "";
		this.myEncode = false;
		this.tvSystem = TVsystem.none;
		this.resolution = Resolution.unspecified;
		this.aspectRatio = AspectRatio.unspecified;
		this.dvdRegion = new boolean[]{false, false, false, false, false, false, false, false, false};
		this.imageBytes = null;
	}

	public String toString() {
		return getDisplayTitle() + " (" + getYear() + ")";
	}

	public ArrayList<ActorInfo> getActors() {
		return actors;
	}

	public void setActors(ArrayList<ActorInfo> actors) {
		this.actors = actors;
	}

	public ArrayList<AudioTrack> getAudioTracks() {
		return audioTracks;
	}

	public void setAudioTracks(ArrayList<AudioTrack> audioTracks) {
		this.audioTracks = audioTracks;
	}

	public boolean isColor() {
		return color;
	}
	
	public int getColorInt() {
		if(color)
			return 1;
		return 0;
	}

	public void setColor(boolean color) {
		this.color = color;
	}
	
	public void setColorInt(int color) {
		if(color == 0)
			this.color = false;
		else if(color == 1)
			this.color = true;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}
	
	public String getCountriesAsString() {
		String s = "";
		for(Country c : countries) {
			s += c.getName() + " / ";
		}
		if(s.endsWith(" / "))
			s = s.substring(0, s.length()-3);
		return s;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	public String getCustomVersion() {
		return customVersion;
	}

	public void setCustomVersion(String customVersion) {
		this.customVersion = customVersion;
	}

	public ArrayList<Person> getDirectors() {
		return directors;
	}

	/**
	 * Returns a list of all directors in a single string 
	 * @return directors
	 */
	public String getDirectorsAsString() {
		String s = "";
		for(Person d : directors) {
			s += d.getName() + ", ";
		}
		if(s.endsWith(", "))
			s = s.substring(0, s.length()-2);
		return s;
	}

	public void setDirectors(ArrayList<Person> directors) {
		this.directors = directors;
	}

	public DiscType getDisc() {
		return disc;
	}

	public void setDisc(DiscType disc) {
		this.disc = disc;
	}

	public String getCustomTitle() {
		return customTitle;
	}

	public void setCustomTitle(String customTitle) {
		this.customTitle = customTitle;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public FormatType getFormat() {
		return format;
	}

	public void setFormat(FormatType format) {
		this.format = format;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}
	
	public String getGenresAsString() {
		String s = "";
		for(Genre g : genres) {
			s += g.getGuiLanguageName() + " / ";
		}
		if(s.endsWith(" / "))
			s = s.substring(0, s.length()-3);
		return s;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public String getImdbID() {
		return imdbID;
	}

	/**
	 * Sets the IMDb ID from a string containing exactly seven 
	 * consecutive numerical digits. The String is parsed
	 * and only the numerical part is stored.
	 * @param imdbID a string containing the IMDb ID
	 */
	public void setImdbID(String imdbID) {
		Matcher matcher = Pattern.compile("\\D*(\\d{7})\\D*").matcher(imdbID);
		if(matcher.find())
			this.imdbID = matcher.group(1);
	}
	
	/**
	 * Returns the IMDb URL (as a String) of the current movie. 
	 * @return IMDb URL
	 */
	public String getImdbUrl() {
		if(getImdbID() == null || getImdbID().length() != 7)
			return "";
		return Settings.getSettings().getImdbUrl() + getImdbID() + "/";
	}
	
	/**
	 * Checks whether or not the IMDb URL will be valid if it is requested now
	 * @return
	 */
	public boolean isImdbUrlValid() {
		if(getImdbID() == null || getImdbID().length() != 7)
			return false;
		try {
			new URL(getImdbUrl());
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Language> getLanguages() {
		return imdbLanguages;
	}
	
	public String getLanguagesAsString() {
		String s = "";
		for(Language l : imdbLanguages) {
			s += l.getName() + " / ";
		}
		if(s.endsWith(" / "))
			s = s.substring(0, s.length()-3);
		return s;
	}

	public void setLanguages(ArrayList<Language> imdbLanguages) {
		this.imdbLanguages = imdbLanguages;
	}

	public boolean isLegal() {
		return legal;
	}

	public void setLegal(boolean legal) {
		this.legal = legal;
	}
	
	public void setLegal(int legal) {
		setLegal(CONST.intToBoolean(legal));
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPlotOutline() {
		return plotOutline;
	}

	public void setPlotOutline(String plotOutline) {
		this.plotOutline = plotOutline;
	}

	public double getRating() {
		return rating;
	}
	
	public int getRatingAsInt() {
		Double r = new Double(rating*10);
		return r.intValue();
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setRatingAsInt(int rating) {
		this.rating = rating/10;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}
	
	public void setRunTime(String runTime) {
		try {
			setRunTime(Integer.valueOf(runTime));
		} catch (NumberFormatException e) {
			setRunTime(0);
		}
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	public void setSeen(int seen) {
		setSeen(CONST.intToBoolean(seen));
	}

	public ArrayList<SubtitleTrack> getSubtitles() {
		return subtitles;
	}

	public void setSubtitles(ArrayList<SubtitleTrack> subtitles) {
		this.subtitles = subtitles;
	}
	
	public void addSubtitle(SubtitleTrack subtitle) {
		this.subtitles.add(subtitle);
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FilmVersion getVersion() {
		return version;
	}

	public void setVersion(FilmVersion version) {
		this.version = version;
	}

	public VideoCodec getVideo() {
		return video;
	}

	public void setVideo(VideoCodec video) {
		this.video = video;
	}

	public ArrayList<Person> getWriters() {
		return writers;
	}
	
	/**
	 * Returns a list of all writers in a single string 
	 * @return writers
	 */
	public String getWritersAsString() {
		String s = "";
		for(Person w : writers) {
			s += w.getName() + ", ";
		}
		if(s.endsWith(", "))
			s = s.substring(0, s.length()-2);
		return s;
	}

	public void setWriters(ArrayList<Person> writers) {
		this.writers = writers;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		if(year > 0 && year < 9999)
			this.year = year;
		else
			this.year = 0;
	}
	
	public void setYear(String year) {
		try {
			int yearInt = Integer.valueOf(year).intValue();
			setYear(yearInt);
		} catch (NumberFormatException e) {
			setYear(0);
		}
	}

	public ContainerFormat getContainer() {
		return container;
	}

	public void setContainer(ContainerFormat container) {
		this.container = container;
	}
	
	/**
	 * Returns the movie title as it should be displayed in the movie list.
	 * If at custom title exists, it is returned. If not, the normal title is
	 * used.
	 * @return the movie title
	 */
	public String getDisplayTitle() {
		if(getCustomTitle() != null && !getCustomTitle().equals(""))
			return getCustomTitle();
		else
			return getTitle();
	}
	
	/**
	 * Returns a modified version of the movie title, to be used for
	 * alphabetical sorting.
	 * @return the modified movie title
	 */
	public String getSortTitle() {
		String sortString = getDisplayTitle();
		
		if(sortString.toLowerCase().startsWith("the "))
			sortString = sortString.substring(4);
		else if(sortString.toLowerCase().startsWith("a "))
			sortString = sortString.substring(2);
		
		return sortString;
	}

	public boolean isMyEncode() {
		return myEncode;
	}

	public void setMyEncode(boolean myEncode) {
		this.myEncode = myEncode;
	}
	
	public void setMyEncode(int myEncode) {
		setMyEncode(CONST.intToBoolean(myEncode));
	}

	public String getSceneReleaseName() {
		return sceneReleaseName;
	}

	public void setSceneReleaseName(String sceneReleaseName) {
		this.sceneReleaseName = sceneReleaseName;
	}

	public AspectRatio getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(AspectRatio aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public TVsystem getTvSystem() {
		return tvSystem;
	}

	public void setTvSystem(TVsystem tvSystem) {
		this.tvSystem = tvSystem;
	}

	public boolean[] getDvdRegion() {
		return dvdRegion;
	}

	public void setDvdRegion(boolean[] dvdRegion) {
		this.dvdRegion = dvdRegion;
	}
	
	public void setDvdRegionAsInt(int dvdRegion) {
		//TODO
	}
	
	/**
	 * Returns the movie's DVD region(s) as an int. 
	 * The value is 0 if the movie doesn't have a region set.
	 * @return all DVD regions encoded in an int
	 */
	public int getDvdRegionAsInt() {
		int region = 0;;
		if(dvdRegion[0]) region += CONST.R0;
		if(dvdRegion[1]) region += CONST.R1;
		if(dvdRegion[2]) region += CONST.R2;
		if(dvdRegion[3]) region += CONST.R3;
		if(dvdRegion[4]) region += CONST.R4;
		if(dvdRegion[5]) region += CONST.R5;
		if(dvdRegion[6]) region += CONST.R6;
		if(dvdRegion[7]) region += CONST.R7;
		if(dvdRegion[8]) region += CONST.R8;
		
		return region;
	}

	/**
	 * Returns the movie's image data
	 * @return an array of bytes
	 */
	public byte[] getImageBytes() {
		return imageBytes;
	}

	/**
	 * Sets the movie's image data
	 * @param imageBytes
	 */
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	
	public ImageData getImageData() {
		if(imageBytes==null)
			return ImageDescriptor.createFromURL(CONST.NO_COVER_IMAGE).getImageData();
		try {
			return new ImageData(new ByteArrayInputStream(imageBytes));
		} catch(SWTException e) {//Invalid or unsupported image data
			System.out.println("E R R O R " + e.getMessage());
			return null;
		}
	}
	
	public AbstractMovie clone() {
		//TODO finish
		return null;
	}

}