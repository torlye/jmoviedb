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
	complete(0, "Complete"),
	one_season(1, ""), 
	one_episode(2, ""),
	complete_until_season(3, ""), 
	complete_until_episode(4, ""),
	from_season_to_season(5, ""), 
	from_episode_to_episode(6, ""),
	sporadic(7, "");
	
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

}
