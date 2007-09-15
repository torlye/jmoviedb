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
	file(0, "File", "Computer file", 
			Settings.getSettings().getLanguageClass().FORMAT_FILE_DESCRIPTION, 
			null, 
			new ContainerFormat[]{
					ContainerFormat.avi, 
					ContainerFormat.mpeg, 
					ContainerFormat.mpeg4, 
					ContainerFormat.divx, 
					ContainerFormat.matroska, 
					ContainerFormat.asf, 
					ContainerFormat.ogm, 
					ContainerFormat.vob, 
					ContainerFormat.real,
					ContainerFormat.quicktime, 
					ContainerFormat.nut, 
					ContainerFormat.ratdvd, 
					ContainerFormat.other}, 
			new DiscType[]{
					DiscType.cd,
					DiscType.cdr,
					DiscType.cdrw,
					DiscType.dvd, 
					DiscType.dvdminusr, 
					DiscType.dvdminusrdl, 
					DiscType.dvdminusrw, 
					DiscType.dvdplusr, 
					DiscType.dvdplusrdl, 
					DiscType.dvdplusrw, 
					DiscType.dvdram,
					DiscType.hddvd, 
					DiscType.hddvdr,
					DiscType.bd, 
					DiscType.bdr, 
					DiscType.bdre}, 
			null, 
			null,
			new SubtitleFormat[]{
					SubtitleFormat.vobsub,
					SubtitleFormat.subrip,
					SubtitleFormat.microdvd,
					SubtitleFormat.ssa,
					SubtitleFormat.subviewer,
					SubtitleFormat.usf,
					SubtitleFormat.aqt,
					SubtitleFormat.jaco,
					SubtitleFormat.mpsub,
					SubtitleFormat.phoenix,
					SubtitleFormat.powerdivx,
					SubtitleFormat.real,
					SubtitleFormat.sami,
					SubtitleFormat.ssf,
					SubtitleFormat.vplayer,
					SubtitleFormat.burnt_in,
					SubtitleFormat.other}),
	dvd(1, "DVD", "Digital Versatile Disc", 
			Settings.getSettings().getLanguageClass().FORMAT_DVD_DESCRIPTION, 
			Settings.getSettings().getLanguageClass().FORMAT_DVD_URL, 
			new ContainerFormat[]{ContainerFormat.vob}, 
			new DiscType[]{
					DiscType.dvd, 
					DiscType.dvdminusr, 
					DiscType.dvdminusrdl, 
					DiscType.dvdminusrw, 
					DiscType.dvdplusr, 
					DiscType.dvdplusrdl, 
					DiscType.dvdplusrw, 
					DiscType.dvdram}, 
			new VideoCodec[]{VideoCodec.mpeg2}, 
			new AudioCodec[]{
					AudioCodec.ac3, 
					AudioCodec.dts, 
					AudioCodec.mp2, 
					AudioCodec.pcm},
			new SubtitleFormat[]{
					SubtitleFormat.medianative, 
					SubtitleFormat.cc, 
					SubtitleFormat.burnt_in}),
	hddvd(2, "HD DVD", "High-Definition DVD", 
			Settings.getSettings().getLanguageClass().FORMAT_HDDVD_DESCRIPTION, 
			Settings.getSettings().getLanguageClass().FORMAT_HDDVD_URL, 
			new ContainerFormat[]{ContainerFormat.medianative}, 
			new DiscType[]{DiscType.hddvd, DiscType.hddvdr}, 
			new VideoCodec[]{
					VideoCodec.vc1, 
					VideoCodec.h264, 
					VideoCodec.mpeg2}, 
			new AudioCodec[]{
					AudioCodec.ac3, 
					AudioCodec.ddex, 
					AudioCodec.dts, 
					AudioCodec.ddplus, 
					AudioCodec.dtruehd, 
					AudioCodec.dts_hd_hra, 
					AudioCodec.dts_hd_ma, 
					AudioCodec.pcm},
			new SubtitleFormat[]{SubtitleFormat.medianative, SubtitleFormat.burnt_in}),
	bluray(3, "Blu-Ray", "Blu-ray Disc", Settings.getSettings().getLanguageClass().FORMAT_BLURAY_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_BLURAY_URL, 
			new ContainerFormat[]{ContainerFormat.medianative}, 
			new DiscType[]{DiscType.bd, DiscType.bdr, DiscType.bdre}, 
			new VideoCodec[]{
					VideoCodec.vc1, 
					VideoCodec.h264, 
					VideoCodec.mpeg2}, 
			new AudioCodec[]{
					AudioCodec.ac3, 
					AudioCodec.dts, 
					AudioCodec.ddplus, 
					AudioCodec.dtruehd, 
					AudioCodec.dts_hd_ma, 
					AudioCodec.pcm},
			new SubtitleFormat[]{SubtitleFormat.medianative, SubtitleFormat.burnt_in}),
	vcd(4, "VCD", "Video CD", Settings.getSettings().getLanguageClass().FORMAT_VCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_VCD_URL, 
			new ContainerFormat[]{ContainerFormat.mpeg}, 
			new DiscType[]{DiscType.cd, DiscType.cdr, DiscType.cdrw}, 
			new VideoCodec[]{VideoCodec.mpeg1}, 
			new AudioCodec[]{AudioCodec.mp2},
			new SubtitleFormat[]{SubtitleFormat.burnt_in}),
	svcd(5, "SVCD", "Super Video CD", Settings.getSettings().getLanguageClass().FORMAT_SVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_SVCD_URL, 
			new ContainerFormat[]{ContainerFormat.mpeg}, 
			new DiscType[]{DiscType.cd, DiscType.cdr, DiscType.cdrw}, 
			new VideoCodec[]{VideoCodec.mpeg2}, 
			new AudioCodec[]{AudioCodec.mp2},
			new SubtitleFormat[]{SubtitleFormat.svcd, SubtitleFormat.cvd, SubtitleFormat.burnt_in}),
	xvcd(7, "XVCD", "Non-standard VCD", Settings.getSettings().getLanguageClass().FORMAT_XVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_XVCD_URL, 
			new ContainerFormat[]{ContainerFormat.mpeg}, 
			new DiscType[]{DiscType.cd, DiscType.cdr, DiscType.cdrw}, 
			new VideoCodec[]{VideoCodec.mpeg1}, 
			new AudioCodec[]{AudioCodec.mp2},
			new SubtitleFormat[]{SubtitleFormat.burnt_in}),
	xsvcd(8, "XSVCD", "Non-standard SVCD", Settings.getSettings().getLanguageClass().FORMAT_XSVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_XSVCD_URL, 
			new ContainerFormat[]{ContainerFormat.mpeg}, 
			new DiscType[]{DiscType.cd, DiscType.cdr, DiscType.cdrw}, 
			new VideoCodec[]{VideoCodec.mpeg2}, 
			new AudioCodec[]{AudioCodec.mp2},
			new SubtitleFormat[]{SubtitleFormat.svcd, SubtitleFormat.cvd, SubtitleFormat.burnt_in}),
	kvcd(9, "KVCD", "K Video Compression Dynamics", Settings.getSettings().getLanguageClass().FORMAT_KVCD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_KVCD_URL, 
			new ContainerFormat[]{ContainerFormat.mpeg}, 
			new DiscType[]{DiscType.cd, DiscType.cdr, DiscType.cdrw}, 
			new VideoCodec[]{VideoCodec.mpeg1}, 
			new AudioCodec[]{AudioCodec.mp2},
			new SubtitleFormat[]{SubtitleFormat.burnt_in}),
	umd(6, "UMD", "", Settings.getSettings().getLanguageClass().FORMAT_UMD_DESCRIPTION, Settings.getSettings().getLanguageClass().FORMAT_UMD_URL, 
			new ContainerFormat[]{ContainerFormat.medianative}, 
			new DiscType[]{DiscType.umd}, 
			new VideoCodec[]{VideoCodec.h264}, 
			new AudioCodec[]{AudioCodec.atrac3plus},
			new SubtitleFormat[]{SubtitleFormat.medianative, SubtitleFormat.burnt_in}), 
	laserdisc(7, "LaserDisc", "", "DESCRIPTION", null,
			new ContainerFormat[]{ContainerFormat.medianative},
			new DiscType[]{DiscType.ld},
			new VideoCodec[]{VideoCodec.analog},
			new AudioCodec[]{AudioCodec.analog, AudioCodec.pcm, AudioCodec.ac3, AudioCodec.dts},
			new SubtitleFormat[]{SubtitleFormat.burnt_in}),
	other(8, "Other/unknown", "", null, null, null, null, null, null, null);
	
	private int id;
	private String shortName;
	private String longName;
	private String description;
	private String url;
	private ContainerFormat[] formats;
	private DiscType[] discs;
	private VideoCodec[] videoCodecs;
	private AudioCodec[] audioCodecs;
	private SubtitleFormat[] subFormats;
	
	FormatType(int id, String shortName, String longName, String description, String url, ContainerFormat[] formats, DiscType[] discs, VideoCodec[] videoCodecs, AudioCodec[] audioCodecs, SubtitleFormat[] subFormats) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
		this.formats = formats;
		this.discs = discs;
		this.videoCodecs = videoCodecs;
		this.audioCodecs = audioCodecs;
		this.subFormats = subFormats;
	}

	public SubtitleFormat[] getSubFormats() {
		return subFormats;
	}

	public AudioCodec[] getAudioCodecs() {
		return audioCodecs;
	}

	public String getDescription() {
		return description;
	}

	public DiscType[] getDiscs() {
		return discs;
	}

	public ContainerFormat[] getFormats() {
		return formats;
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

	public VideoCodec[] getVideoCodecs() {
		return videoCodecs;
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
