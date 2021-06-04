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

public enum Resolution {
	unspecified(0, ""), 
	cif(1, "CIF"), 
	sd(2, "SD"),
	p720(3, "720p"), 
	i1080(4, "1080i"), 
	p1080(5, "1080p"),
	p2160(6, "4K UHD");
	
	int id;
	String name;
	
	Resolution(int id, String name) {
		this.id = id;
		this.name = name;
	}
		
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public boolean isHD() {
		if(id >= 3)
			return true;
		return false;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[Resolution.values().length];
		for(int i = 0; i < Resolution.values().length; i++)
			strings[i] = Resolution.values()[i].getName();
		return strings;
	}

	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static Resolution intToEnum(int id) {
		for(Resolution r : Resolution.values())
			if(id == r.getID())
				return r;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised resolution ID: " + id);
		return null;
	}
}
