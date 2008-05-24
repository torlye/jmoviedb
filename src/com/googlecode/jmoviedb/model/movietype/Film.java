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

import com.googlecode.jmoviedb.enumerated.ContainerFormat;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.SubtitleFormat;
import com.googlecode.jmoviedb.enumerated.VideoCodec;
import com.googlecode.jmoviedb.model.SubtitleTrack;

public class Film extends AbstractMovie {

	/**
	 * {@inheritDoc}
	 */
	public Film(
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
		super(id, imdbID, title, customTitle, year, rating, plotOutline, tagline, color, runTime, notes, version, customVersion, legal, seen, formatID, discTypeID, videoCodecID, myEncode, dvdRegion, tvSystem, sceneReleaseName, resolutionID, aspectID);
	}
	
	/**
	 * Constructor for moviedb CSV import. Several of the parameters are ignored in the current implementation, 
	 * as these attibutes can easily be dowloaded from imdb.com
	 * @param id moviedb database id. Ignored in the current implementation.
	 * @param title movie title.
	 * @param format movie format. Can be any String, for example &quot;DVD&quot; or &quot;XviD&quot;.
	 * @param imdbURL IMDb URL
	 * @param director driector(s) for this movie. If there are more than one, the list may be in an unexpected format. Ignored in the current implementation.
	 * @param genre list of genres, separated by slashes. Example: &quot;Adventure / Action / Sci-Fi&quot;. Ignored in the current implementation.
	 * @param tagLine movie tagline. Ignored in the current implementation.
	 * @param plotOutline movie plot outline. Ignored in the current implementation.
	 * @param comment user comment from IMDb. Ignored in the current implementation.
	 * @param country list of countries, separated by slashes. Example: &quot;Belgium / USA&quot;. Ignored in the current implementation.
	 * @param year the year in which the movie was made.
	 * @param runtime movie runtime in minutes. Ignored in the current implementation.
	 * @param rate movie rating from IMDb. Ignored in the current implementation.
	 * @param subLanguages list of subtitle languages.
	 */
	public Film(String id, String title, String format, String imdbURL, String director, String genre, 
			String tagLine, String plotOutline, String comment, String country, String year, 
			String runtime, String rate, String subLanguages) {
		this();
		
		//change "Matrix, The" to "The Matrix"
		if(title.endsWith(", The"))
			setTitle("The " + title.substring(0, title.length()-5));
		else if(title.endsWith(", A"))
			setTitle("A " + title.substring(0, title.length()-3));
		else
			setTitle(title);
		
		setImdbID(imdbURL);
		setYear(year);
		
		//parse the format parameter
		if(format.toLowerCase().contains("dvd")) {
			setFormat(FormatType.dvd);
			setContainer(ContainerFormat.vob);
			setVideo(VideoCodec.mpeg2);
		} else if(format.toLowerCase().contains("svcd")) {
			setFormat(FormatType.svcd);
			setContainer(ContainerFormat.mpeg);
			setVideo(VideoCodec.mpeg2);
		} else if(format.toLowerCase().contains("vcd")) {
			setFormat(FormatType.vcd);
			setContainer(ContainerFormat.mpeg);
			setVideo(VideoCodec.mpeg1);
		} else if(format.toLowerCase().contains("asf") || format.toLowerCase().contains("wmv") || format.toLowerCase().contains("windows")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.asf);
			setVideo(VideoCodec.wmv);
		} else if(format.toLowerCase().contains("avi")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.avi);
		} else if(format.toLowerCase().contains("divx")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.avi);
			setVideo(VideoCodec.divx);
		} else if(format.toLowerCase().contains("xvid")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.avi);
			setVideo(VideoCodec.xvid);
		} else if(format.toLowerCase().contains("matroska") || format.toLowerCase().contains("mkv")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.matroska);
		} else if(format.toLowerCase().contains("mp4")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.mpeg4);
		} else if(format.toLowerCase().contains("mpeg-1")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.mpeg);
			setVideo(VideoCodec.mpeg1);
		} else if(format.toLowerCase().contains("mpeg-2")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.mpeg);
			setVideo(VideoCodec.mpeg2);
		} else if(format.toLowerCase().contains("ogg")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.ogm);
		} else if(format.toLowerCase().contains("quicktime")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.quicktime);
		} else if(format.toLowerCase().contains("ratdvd")) {
			setFormat(FormatType.file);
			setContainer(ContainerFormat.ratdvd);
		} else if(format.toLowerCase().contains("h264")) {
			setVideo(VideoCodec.h264);
		} else {
			System.out.println("Did not recognise format \"" + format + "\"");
		}
		
		//parse subtitles
		String[] subtitleLanguages = subLanguages.split(" / ");
		for(int i=0; i<subtitleLanguages.length; i++) {
			Language aLanguage = Language.StringToEnum(subtitleLanguages[i]);
			if(aLanguage != null) {
				if(getFormat().equals(FormatType.dvd))
					addSubtitle(new SubtitleTrack(aLanguage, SubtitleFormat.medianative, false, false));
				else
					addSubtitle(new SubtitleTrack(aLanguage, SubtitleFormat.other, false, false));
			} else {
				if(subtitleLanguages[i].length() > 0)
					System.out.println("Did not recognise language \"" + subtitleLanguages[i] + "\"");
			}
		}
	}
	
	public Film() {
		super();
	}
	
	public Film(String title) {
		super();
		this.setTitle(title);
	}

	
}
