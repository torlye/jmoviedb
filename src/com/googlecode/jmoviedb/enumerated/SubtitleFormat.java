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

public enum SubtitleFormat {
	other(0),
	burnt_in(99),
	medianative(1),
	vobsub(2),
	subrip(3),
	microdvd(4),
	mpeg4(5),
	ogg(6),
	ssa(7),
	subviewer(8),
	usf(9),
	xsub(10),
	aqt(11),
	jaco(12),
	mpsub(13),
	phoenix(14),
	powerdivx(15),
	real(16),
	sami(17),
	ssf(18),
	vplayer(19),
	svcd(20),
	cvd(21),
	cc(22);
	
	private int ID;
	private String shortName;
	private String longName;
	private String description;
	private URL url;
	
	private SubtitleFormat(int id) {
		this.ID = id;
	}
	
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

	public int getID() {
		return ID;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static SubtitleFormat intToEnum(int id) {
		for(SubtitleFormat a : SubtitleFormat.values())
			if(id == a.getID())
				return a;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised subtitle format ID: " + id);
		return null;
	}
}
