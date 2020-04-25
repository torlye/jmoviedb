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
	other(0, "", "", ""),
	harddrive(1, "Hard drive", "", ""),
	cd(2, "CD-ROM", "", ""),
	cdr(3, "CD-R", "", ""),
	//cdrw(4, "CD-RW", "", ""),
	dvd(5, "DVD-ROM", "", ""),
	dvdminusr(6, "DVD-R", "", ""),
	dvdplusr(7, "DVD+R", "", ""),
	//dvdminusrw(8, "DVD-RW", "", ""),
	//dvdplusrw(9, "DVD+RW", "", ""),
	dvdminusrdl(10, "DVD-R DL", "", ""),
	dvdplusrdl(11, "DVD+R DL", "", ""),
	dvdram(12, "DVD-RAM", "", ""),
	bd(13, "BD-ROM", "", ""),
	bdr(14, "BD-R", "", ""),
	uhdbd(21, "UHD BD", "", ""),
	//bdre(15, "BD-RE", "", ""),
	hddvd(16, "HD DVD-ROM", "", ""),
	hddvdr(17, "HD DVD-R", "", ""),
	umd(18, "UMD", "", ""),
	ld(19, "LD", "", ""),
	vhs(20, "VHS", "", ""),
	betamax(22, "Betamax", "", ""),	
	v2000(23, "Video 2000", "", ""),
	gba(24, "Game Boy Advance Video Pak", "", ""),
	film16mm(25, "16mm film reel", "", ""),
	dvhs(26, "D-VHS", "", ""),
	video8(27, "Video8", "", ""),
	mmcmobile(28, "MMCmobile", "", ""),
	ced(29, "CED", "", ""),
	cdi(30, "CD-i", "", ""),
	;
	
	private int id;
	private String name;
	private String longName;
	private String description;
	
	private DiscType(int id, String name, String longName, String description) {
		this.id = id;
		this.name = name;
		this.longName = longName;
		this.description = description;
	}
	
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
	
	public static String[] getStringArray() {
		String[] strings = new String[DiscType.values().length];
		for(int i = 0; i < DiscType.values().length; i++)
			strings[i] = DiscType.values()[i].getName();
		return strings;
	}
}
