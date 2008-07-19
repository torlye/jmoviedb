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
import com.googlecode.jmoviedb.Settings;

@SuppressWarnings("static-access")

public enum FormatType {
	other(0, "", "", null, null),
	file(1, "File", "Computer file", 
			Settings.getSettings().getLanguageClass().FORMAT_FILE_DESCRIPTION, 
			null),
	dvd(2, "DVD", "Digital Versatile Disc", 
			Settings.getSettings().getLanguageClass().FORMAT_DVD_DESCRIPTION, 
			Settings.getSettings().getLanguageClass().FORMAT_DVD_URL),
	bluray(3, "Blu-Ray", "Blu-ray Disc", Settings.getSettings().getLanguageClass().FORMAT_BLURAY_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_BLURAY_URL),
	hddvd(4, "HD DVD", "High-Definition DVD", 
			Settings.getSettings().getLanguageClass().FORMAT_HDDVD_DESCRIPTION, 
			Settings.getSettings().getLanguageClass().FORMAT_HDDVD_URL),
	vcd(5, "VCD", "Video CD", Settings.getSettings().getLanguageClass().FORMAT_VCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_VCD_URL),
	svcd(6, "SVCD", "Super Video CD", Settings.getSettings().getLanguageClass().FORMAT_SVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_SVCD_URL),
	xvcd(7, "XVCD", "Non-standard VCD", Settings.getSettings().getLanguageClass().FORMAT_XVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_XVCD_URL),
	xsvcd(8, "XSVCD", "Non-standard SVCD", Settings.getSettings().getLanguageClass().FORMAT_XSVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_XSVCD_URL),
	vhs(9, "VHS", "VHS video cassette", "", ""),
	umd(10, "UMD", "", Settings.getSettings().getLanguageClass().FORMAT_UMD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_UMD_URL), 
	laserdisc(11, "LaserDisc", "", "DESCRIPTION", null);
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private String url;
	
	FormatType(int id, String shortName, String longName, String description, String url) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
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

	public String getShortName() {
		return shortName;
	}

	public String getUrl() {
		return url;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[FormatType.values().length];
		for(int i = 0; i < FormatType.values().length; i++)
			strings[i] = FormatType.values()[i].getShortName();
		return strings;
	}

	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static FormatType intToEnum(int id) {
		for(FormatType f : FormatType.values())
			if(id == f.getID())
				return f;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised format type ID: " + id);
		return null;
	}
}
