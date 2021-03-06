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

public enum AspectRatio {
	unspecified(0, "", ""),
	fullframe(1, "4:3", "4:3 1.33 Full-frame"),
	letterbox(3, "4:3 LB", "4:3 Letterbox"),
	wide16_9(2, "Wide", "16:9 1.78 Widescreen");
	
	private int id;
	private String shortName;
	private String description;
	
	AspectRatio(int id, String shortName, String description) {
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
	public static AspectRatio intToEnum(int id) {
		for(AspectRatio a : AspectRatio.values())
			if(id == a.getID())
				return a;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised aspect ratio ID: " + id);
		return null;
	}

	public static String[] getStringArray() {
		String[] strings = new String[AspectRatio.values().length];
		for(int i = 0; i < AspectRatio.values().length; i++)
			strings[i] = AspectRatio.values()[i].getShortName();
		return strings;
	}
}
