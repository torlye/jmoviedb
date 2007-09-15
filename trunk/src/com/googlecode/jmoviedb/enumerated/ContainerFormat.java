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
	other(0, "Other/unknown", "", null, null, new VideoCodec[]{VideoCodec.other}, new AudioCodec[]{AudioCodec.other}, null),
	medianative(1, "Media native", "", null, null, null, null, null),
	vob(2, "VOB", "", null, null, 
			new VideoCodec[]{
					VideoCodec.mpeg2}, 
			new AudioCodec[]{AudioCodec.ac3, 
					AudioCodec.dts, 
					AudioCodec.mp2, 
					AudioCodec.pcm}, 
			new SubtitleFormat[]{
					SubtitleFormat.medianative, 
					SubtitleFormat.cc, 
					SubtitleFormat.burnt_in}),
	avi(3, "AVI", "Audio Video Interleave", null, null, ContainerFormat.AVI_LIKE_VIDEO_SUPPORT, ContainerFormat.AVI_LIKE_AUDIO_SUPPORT, null),
	mpeg4(4, "MPEG-4", "MPEG-4 Part 14", null, null, new VideoCodec[]{VideoCodec.mpeg4, VideoCodec.h264}, new AudioCodec[]{AudioCodec.aac}, new SubtitleFormat[]{SubtitleFormat.mpeg4}),
	mpeg(5, "MPEG-1/2", "", null, null, new VideoCodec[]{VideoCodec.mpeg1, VideoCodec.mpeg2}, null, null),
	matroska(6, "Matroska", "", null, null, ContainerFormat.AVI_LIKE_VIDEO_SUPPORT, ContainerFormat.AVI_LIKE_AUDIO_SUPPORT, null),
	asf(7, "Asf", "Asf/Windows Media", null, null, new VideoCodec[]{VideoCodec.wmv}, new AudioCodec[]{AudioCodec.wma}, null),
	divx(8, "DivX DMF", "DivX Media Container", null, null, new VideoCodec[]{VideoCodec.divx}, null, new SubtitleFormat[]{SubtitleFormat.xsub}),
	ogm(9, "Ogg Media", "", null, null, null, new AudioCodec[]{AudioCodec.ogg}, new SubtitleFormat[]{SubtitleFormat.ogg}),
	quicktime(10, "QuickTime", "", null, null, null, null, null),
	real(11, "RealMedia", "", null, null, new VideoCodec[]{VideoCodec.real}, new AudioCodec[]{AudioCodec.real}, null),
	nut(12, "Nut", "", null, null, null, null, null),
	ratdvd(13, "ratDVD", "", null, null, null, null, null);
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private URL url;
	private VideoCodec[] videoCodecs;
	private AudioCodec[] audioCodecs;
	private SubtitleFormat[] subFormats;
	
	private static VideoCodec[] AVI_LIKE_VIDEO_SUPPORT = new VideoCodec[]{VideoCodec.xvid, VideoCodec.divx, VideoCodec.h264, VideoCodec.mpeg4, VideoCodec.other};
	private static AudioCodec[] AVI_LIKE_AUDIO_SUPPORT = new AudioCodec[]{AudioCodec.mp3, AudioCodec.ac3, AudioCodec.dts, AudioCodec.pcm, AudioCodec.other};
	
	
	private ContainerFormat(int id, String shortName, String longName, String description, URL url, VideoCodec[] videoCodecs, AudioCodec[] audioCodecs, SubtitleFormat[] subFormats) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
		this.videoCodecs = videoCodecs;
		this.audioCodecs = audioCodecs;
		this.subFormats = subFormats;
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
