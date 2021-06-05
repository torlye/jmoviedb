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

package com.googlecode.jmoviedb.enumerated;

import com.googlecode.jmoviedb.language.GuiLanguage;
import com.googlecode.jmoviedb.model.movietype.*;

public enum MovieType {
	film(0, new Film().getClass(), GuiLanguage.TYPE_FILM, GuiLanguage.TYPE_FILM_DESCRIPTION),
	tvmovie(1, new TVmovie().getClass(), GuiLanguage.TYPE_TVMOVIE, GuiLanguage.TYPE_TVMOVIE_DESCRIPTION),
	videomovie(2, new VideoMovie().getClass(), GuiLanguage.TYPE_VIDEOMOVIE, GuiLanguage.TYPE_VIDEOMOVIE_DESCRIPTION),
	tvseries(3, new TVseries().getClass(), GuiLanguage.TYPE_TVSERIES, GuiLanguage.TYPE_TVSERIES_DESCRIPTION),
	miniseries(4, new MiniSeries().getClass(), GuiLanguage.TYPE_MINISERIES, GuiLanguage.TYPE_MINISERIES_DESCRIPTION),
	webseries(5, new WebSeries().getClass(), GuiLanguage.TYPE_WEBSERIES, GuiLanguage.TYPE_WEBSERIES_DESCRIPTION),
	movieserial(6, new MovieSerial().getClass(), GuiLanguage.TYPE_MOVIESERIAL, GuiLanguage.TYPE_MOVIESERIAL_DESCRIPTION);
	
	private int id;
	private Class<?> cl;
	private String name;
	private String description;
	
	private MovieType(int id, Class<?> cl, String name, String description) {
		this.id = id;
		this.cl = cl;
		this.name = name;
		this.description = description;
	}

	public Class<?> getCl() {
		return cl;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return getName();
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[MovieType.values().length];
		for(int i = 0; i < MovieType.values().length; i++)
			strings[i] = MovieType.values()[i].toString();
		return strings;
	}
	
	public static MovieType objectToEnum(Object o) {
		if(o == null)
			return null;
		for(MovieType t : MovieType.values())
			if(t.getCl().equals(o.getClass()))
				return t;
		return null;
	}
	
	public static MovieType stringToEnum(String str) {
		if(str == null)
			return null;
		for(MovieType t : MovieType.values())
			if(t.toString().equals(str))
				return t;
		return null;
	}
	
	public static AbstractMovie intToAbstractMovie(int id) {
		if(id == 0)
			return new Film();
		if(id == 1)
			return new TVmovie();
		if(id == 2)
			return new VideoMovie();
		if(id == 3)
			return new TVseries();
		if(id == 4)
			return new MiniSeries();
		if(id == 5)
			return new WebSeries();
		if(id == 6)
			return new MovieSerial();
		return null;
	}

	public static int abstractMovieToInt(AbstractMovie movie) {
		if(movie == null)
			return 0;
		MovieType type = abstractMovieToEnum(movie);
		if (type != null)
			return type.getId();
		return 0;
	}

	public static MovieType abstractMovieToEnum(AbstractMovie movie) {
		for(MovieType type : MovieType.values()) { 
			if(type.getCl().equals(movie.getClass()))
				return type;
		}
		return null;
	}
}
