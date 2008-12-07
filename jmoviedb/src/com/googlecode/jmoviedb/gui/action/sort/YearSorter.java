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

package com.googlecode.jmoviedb.gui.action.sort;

import java.util.Comparator;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class YearSorter implements Comparator<AbstractMovie> {
	private boolean descending;
	private int modifier;
	
	public YearSorter(boolean descending) {
		this.descending = descending;

		//if sort direction is descending, the default sort direction must be reversed
		if(descending)
			modifier = -1;
		else
			modifier = 1;
	}
	
	public int compare(AbstractMovie movie1, AbstractMovie movie2) {
		//first, compare on year
		if(movie1.getYear() < movie2.getYear())
			return -1*modifier;
		if(movie1.getYear() > movie2.getYear())
			return 1*modifier;
		
		//if the movies were made in the same year, compare the titles
		return new TitleSorter(descending).compare(movie1, movie2);
	}
}
