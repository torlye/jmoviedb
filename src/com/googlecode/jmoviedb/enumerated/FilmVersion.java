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

import com.googlecode.jmoviedb.CONST;

public enum FilmVersion {
	unspecified(0, "", ""),
	custom(1, "", ""),
	cinematic(2, "", ""), 
	unrated(3, "", ""), 
	directors(4, "", ""), 
	extended(5, "", ""), 
	special(6, "", ""), 
	special_extended(7, "", ""), 
	reconstruction(8, "", "");
	
	private int id;
	private String shortName;
	private String description;
	
	FilmVersion(int id, String shortName, String description) {
		this.id = id;
		this.shortName = shortName;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getID() {
		return id;
	}

	public String getShortName() {
		return shortName;
	}

	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static FilmVersion intToEnum(int id) {
		for(FilmVersion f : FilmVersion.values())
			if(id == f.getID())
				return f;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised film version ID: " + id);
		return null;
	}
}
