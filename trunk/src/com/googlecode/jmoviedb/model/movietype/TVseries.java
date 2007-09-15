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

public class TVseries extends AbstractSeries {
	private String fromSeason;
	private String toSeason;
	
	public String getFromSeason() {
		return fromSeason;
	}
	public void setFromSeason(String fromSeason) {
		this.fromSeason = fromSeason;
	}
	public String getToSeason() {
		return toSeason;
	}
	public void setToSeason(String toSeason) {
		this.toSeason = toSeason;
	}
}