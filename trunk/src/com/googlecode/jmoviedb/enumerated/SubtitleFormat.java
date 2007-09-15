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

public enum SubtitleFormat {
	other(),
	medianative(),
	vobsub(),
	subrip(),
	microdvd(),
	mpeg4(),
	ogg(),
	ssa(),
	subviewer(),
	usf(),
	xsub(),
	aqt(),
	jaco(),
	mpsub(),
	phoenix(),
	powerdivx(),
	real(),
	sami(),
	ssf(),
	vplayer(),
	svcd(),
	cvd(),
	cc(),
	burnt_in();
	
	private int ID;
	private String shortName;
	private String longName;
	private String description;
	private URL url;
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
//	public static SubtitleFormat intToEnum(int id) {
//		for(SubtitleFormat s : SubtitleFormat.values())
//			if(id == s.getID())
//				return s;
//		if(CONST.DEBUG_MODE)
//			System.out.println("Unrecognised subtitle format ID: " + id);
//		return null;
//	}
}
