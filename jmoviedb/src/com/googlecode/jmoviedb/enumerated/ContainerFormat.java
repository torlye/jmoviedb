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

import java.net.URL;

import com.googlecode.jmoviedb.CONST;

public enum ContainerFormat {
	other(0, "", "", null, null),
	medianative(1, "Media native", "", null, null),
	vob(2, "VOB", "", null, null),
	avi(3, "AVI", "Audio Video Interleave", null, null),
	mpeg4(4, "MPEG-4", "MPEG-4 Part 14", null, null),
	mpeg(5, "MPEG-1/2", "", null, null),
	matroska(6, "Matroska", "", null, null),
	asf(7, "Asf", "Asf/Windows Media", null, null),
	divx(8, "DivX DMF", "DivX Media Container", null, null),
	ogm(9, "Ogg Media", "", null, null),
	quicktime(10, "QuickTime", "", null, null),
	real(11, "RealMedia", "", null, null),
	nut(12, "Nut", "", null, null),
	ratdvd(13, "ratDVD", "", null, null);
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private URL url;	

	public String getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}

	public String getDescription() {
		return description;
	}

	public URL getUrl() {
		return url;
	}

	private ContainerFormat(int id, String shortName, String longName, String description, URL url) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[ContainerFormat.values().length];
		for(int i = 0; i < ContainerFormat.values().length; i++)
			strings[i] = ContainerFormat.values()[i].getShortName();
		return strings;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static ContainerFormat intToEnum(int id) {
		for(ContainerFormat c : ContainerFormat.values())
			if(id == c.getID())
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised container format ID: " + id);
		return null;
	}

	public int getID() {
		return id;
	}
	
}
