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

public enum AudioCodec {
	other(0, "", "", "", null),
	mp3(4, "MP3", "MPEG-1 Audio Layer 3", Settings.getSettings().getLanguageClass().AUDIO_MP3_DESCRIPTION, null), 
	mp2(3, "MP2", "MPEG-1 Audio Layer 2", Settings.getSettings().getLanguageClass().AUDIO_MP2_DESCRIPTION, null),
	aac(5, "AAC", "AAC/MPEG-4 Part 3", Settings.getSettings().getLanguageClass().AUDIO_AAC_DESCRIPTION, null),
	ac3(1, "DD", "Dolby Digital (AC-3, A/52)", Settings.getSettings().getLanguageClass().AUDIO_AC3_DESCRIPTION, null), 
	ddex(16, "DD EX", "Dolby Digital Surround EX", null, null),
	ddplus(11, "DD+", "Dolby Digital Plus (DD+/E-AC-3)", null, null),
	dtruehd(12, "TrueHD", "Dolby TrueHD", null, null),
	dts(2, "DTS", "Digital Theater System", Settings.getSettings().getLanguageClass().AUDIO_DTS_DESCRIPTION, null), 
	dts_es(17, "DTS-ES", "DTS Extended Surround", null, null),
	dts9620(18, "DTS 96/24", "DTS 96/24", null, null),
	dts_hd_hra(13, "DTS-HD HRA", "DTS-HD High Resolution Audio", null, null),
	dts_hd_ma(14, "DTS-HD MA", "DTS-HD Master Audio (DTS++/DTS-HD)", null, null),
	wma(6, "WMA", "Windows Media Audio", Settings.getSettings().getLanguageClass().AUDIO_WMA_DESCRIPTION, null),
	pcm(9, "LPCM", "Pulse-code modulation", Settings.getSettings().getLanguageClass().AUDIO_PCM_DESCRIPTION, null),
	ogg(7, "Vorbis", "Vorbis, aka. Ogg Vorbis", Settings.getSettings().getLanguageClass().AUDIO_VORBIS_DESCRIPTION, null),
	real(8, "RealAudio", "RealAudio", Settings.getSettings().getLanguageClass().AUDIO_REAL_DESCRIPTION, null),
	analog(10, "Analog audio", "", "", null),
	atrac3plus(15, "ATRAC3plus", "", null, null),
	flac(19, "FLAC", "", null, null),
	dtsx(20, "DTS:X", "", null, null),
	atmos(21, "Dolby Atmos", "", null, null),
	;
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private URL url;
	
	AudioCodec(int id, String shortName, String longName, String description, URL url) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
	}

	public int getID() {
		return id;
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
	
	public static AudioCodec StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(AudioCodec a : AudioCodec.values())
			if(string.toLowerCase().equals(a.toString().toLowerCase()))
				return a;
		
		return null;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static AudioCodec intToEnum(int id) {
		for(AudioCodec a : AudioCodec.values())
			if(id == a.getID())
				return a;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised audio codec ID: " + id);
		return null;
	}

	public URL getUrl() {
		return url;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[AudioCodec.values().length];
		for(int i = 0; i < AudioCodec.values().length; i++)
			strings[i] = AudioCodec.values()[i].getShortName();
		return strings;
	}
}
