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
import com.googlecode.jmoviedb.Settings;

@SuppressWarnings("static-access")

public enum VideoCodec {
	other(0, "", "", null, null),
	xvid(1, "XviD", "XviD", Settings.getSettings().getLanguageClass().VIDEO_XVID_DESCRIPTION, null),
	divx(2, "DivX", "DivX 3/4/5/6", Settings.getSettings().getLanguageClass().VIDEO_DIVX_DESCRIPTION, null),
	h264(3, "H.264", "H.264/AVC", Settings.getSettings().getLanguageClass().VIDEO_H264_DESCRIPTION, null),
	mpeg1(4, "MPEG-1", "", Settings.getSettings().getLanguageClass().VIDEO_MPEG1_DESCRIPTION, null),
	mpeg2(5, "MPEG-2", "", Settings.getSettings().getLanguageClass().VIDEO_MPEG2_DESCRIPTION, null),
	mpeg4(6, "MPEG-4", "Generic MPEG-4 video", Settings.getSettings().getLanguageClass().VIDEO_MPEG4_DESCRIPTION, null),
	wmv(7, "WMV", "Windows Media Video", null, null),
	vc1(8, "VC-1", "VC-1", Settings.getSettings().getLanguageClass().VIDEO_VC1_DESCRIPTION, null),
	theora(9, "Theora", "Ogg Theora", null, null),
	dirac(10, "Dirac", "Dirac", null, null),
	real(11, "RealVideo", "", Settings.getSettings().getLanguageClass().VIDEO_REAL_DESCRIPTION, null),
	vp6(12, "On2 VP6", "On2 VP6", null, null),
	sorenson3(13, "SVQ3", "Sorenson Video 3", null, null),
	analog(99, "Analog video", "", "", null);
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private URL url;
	
	VideoCodec(int id, String shortName, String longName, String description, URL url) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}

	public String getLongName() {
		return longName;
	}

	public String getShortName() {
		return shortName;
	}
	
	public String toString() {
		return shortName;
	}
	
	public static VideoCodec StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(VideoCodec v : VideoCodec.values())
			if(string.toLowerCase().equals(v.toString().toLowerCase()))
				return v;
		
		return null;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static VideoCodec intToEnum(int id) {
		for(VideoCodec v : VideoCodec.values())
			if(id == v.getID())
				return v;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised video codec ID: " + id);
		return null;
	}

	public int getID() {
		return id;
	}

	public URL getUrl() {
		return url;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[VideoCodec.values().length];
		for(int i = 0; i < VideoCodec.values().length; i++)
			strings[i] = VideoCodec.values()[i].getShortName();
		return strings;
	}
}
