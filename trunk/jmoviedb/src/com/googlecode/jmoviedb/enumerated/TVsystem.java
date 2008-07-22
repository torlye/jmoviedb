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

public enum TVsystem {
	none(0, "", ""),
	ntsc(1, "NTSC", ""),
	pal(2, "PAL", ""),
	film(3, "Film", "");
	
	private int id;
	private String shortName;
	private String description;
	
	TVsystem(int id, String shortName, String description) {
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
	
	public static String[] getStringArray() {
		String[] strings = new String[TVsystem.values().length];
		for(int i = 0; i < TVsystem.values().length; i++)
			strings[i] = TVsystem.values()[i].getShortName();
		return strings;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static TVsystem intToEnum(int id) {
		for(TVsystem t : TVsystem.values())
			if(id == t.getID())
				return t;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised TV system ID: " + id);
		return null;
	}
}
