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

import com.googlecode.jmoviedb.enumerated.Completeness;

public abstract class AbstractSeries extends AbstractMovie {
	private Completeness completeness;
	private int numberOfEpisodes;
	private String fromEpisode;
	private String toEpisode;
	
	public Completeness getCompleteness() {
		return completeness;
	}
	public void setCompleteness(Completeness completeness) {
		this.completeness = completeness;
	}
	public int getNumberOfEpisodes() {
		return numberOfEpisodes;
	}
	public void setNumberOfEpisodes(int numberOfEpisodes) {
		this.numberOfEpisodes = numberOfEpisodes;
	}
	public String getFromEpisode() {
		return fromEpisode;
	}
	public void setFromEpisode(String fromEpisode) {
		this.fromEpisode = fromEpisode;
	}
	public String getToEpisode() {
		return toEpisode;
	}
	public void setToEpisode(String toEpisode) {
		this.toEpisode = toEpisode;
	}
	
}
