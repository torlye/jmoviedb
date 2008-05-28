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

/**
 * In addition to the fields in AbstractMovie, an AbstractSeries contains
 * information about how complete the series is, i.e. its <i>completeness</i>.
 * This information is represented in two parts: One is an enum of the type
 * Completeness, and the other is a String.  
 * Example 1: If completeness is Completeness.one_season
 * the detail string may be "4", i.e. this entry in the
 * database represents season four of a series.
 * 
 */
public abstract class AbstractSeries extends AbstractMovie {
	private Completeness completeness;
	private String completenessDetail;
	
	/**
	 * Gets the <i>completeness</i> of the series.
	 * @return completeness
	 */
	public Completeness getCompleteness() {
		return completeness;
	}
	
	/**
	 * Sets the <i>completeness</i> of the series.
	 * @param completeness
	 */
	public void setCompleteness(Completeness completeness) {
		this.completeness = completeness;
	}
	
	/**
	 * Gets details about the <i>completeness</i> of the series  
	 * @return completeness detail string
	 */
	public String getCompletenessDetail() {
		return completenessDetail;
	}
	
	/**
	 * Sets details about the <i>completeness</i> of the series,
	 * @param completenessDetail
	 */
	public void setCompletenessDetail(String completenessDetail) {
		this.completenessDetail = completenessDetail;
	}	
}
