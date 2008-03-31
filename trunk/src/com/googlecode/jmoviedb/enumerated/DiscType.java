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

public enum DiscType {
	cd/*(0, "", "", "")*/,
	cdr,
	cdrw,
	dvd,
	dvdminusr,
	dvdplusr,
	dvdminusrw,
	dvdplusrw,
	dvdminusrdl,
	dvdplusrdl,
	dvdram,
	bd,
	bdr,
	bdre,
	hddvd,
	hddvdr,
	umd,
	ld,
	vhs, other;
	
	private int id;
	private String name;
	private String longName;
	private String description;
	
//	Disc(int id, String name, String longName, String description) {
//		
//	}
	
	public String getDescription() {
		return description;
	}
	public int getID() {
		return id;
	}
	public String getLongName() {
		return longName;
	}
	public String getName() {
		return name;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static DiscType intToEnum(int id) {
		for(DiscType d : DiscType.values())
			if(id == d.getID())
				return d;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised disc type ID: " + id);
		return null;
	}
}
