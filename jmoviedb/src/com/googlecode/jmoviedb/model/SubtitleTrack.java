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

package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.SubtitleFormat;

/**
 * An instance of this class represents a concrete subtitle track in a concrete movie. 
 * Each subtitle track is in a specified language and a specified format. 
 * @author Tor
 * @see SubtitleFormat
 * @see Language
 */
public class SubtitleTrack {
	private Language language;
	private SubtitleFormat format;
	private boolean commentary;
	private boolean hearingImpaired;
	private boolean forced;
	
	/**
	 * Creates a new subtitle track.
	 * @param language language
	 * @param format format type for this subtitle
	 * @param commentary true if the subtitle track is for an audio commentary track
	 * @param hearingImpaired true if the subtitle track is optimised for the hearing impaired
	 */
	public SubtitleTrack(Language language, SubtitleFormat format, boolean commentary, boolean hearingImpaired, boolean forced) {
		this.language = language;
		this.format = format;
		this.commentary = commentary;
		this.hearingImpaired = hearingImpaired;
		this.forced = forced;
	}
	
	public SubtitleFormat getFormat() {
		return format;
	}
	public void setFormat(SubtitleFormat format) {
		this.format = format;
	}
	public boolean isCommentary() {
		return commentary;
	}
	public void setCommentary(boolean commentary) {
		this.commentary = commentary;
	}
	public boolean isHearingImpaired() {
		return hearingImpaired;
	}
	public void setHearingImpaired(boolean hearingImpaired) {
		this.hearingImpaired = hearingImpaired;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

	public boolean isForced() {
		return forced;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}
}
