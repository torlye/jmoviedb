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

public enum Completeness {
	other(0, ""),
	complete(1, "Complete series"),
	one_season(2, "One season"), 
	one_episode(3, "One episode"),
	seasons(5, "Range of seasons"),
	episodes(4, "Range of episodes"), 
	sporadic(9, "Sporadic episodes");
	
	private int id;
	private String name;

	private Completeness(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static Completeness intToEnum(int id) {
		for(Completeness c : Completeness.values())
			if(id == c.getID())
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised completeness ID: " + id);
		return null;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[Completeness.values().length];
		for(int i = 0; i < Completeness.values().length; i++)
			strings[i] = Completeness.values()[i].getName();
		return strings;
	}

}
