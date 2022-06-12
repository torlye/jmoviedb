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

package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.*;

public class AudioTrack extends AudioOrSubtitleTrack {
	private AudioCodec audio;
	private AudioChannels channels;
	private boolean audioDescriptive;
	private String trackType;
	
	public AudioTrack(Language language, AudioCodec audio, AudioChannels channels, boolean commentary, boolean audioDescriptive, String trackType) {
		this.language = language;
		this.audio = audio;
		this.channels = channels;
		if (Utils.isNullOrEmpty(trackType)) {
			this.commentary = commentary;
			this.setAudioDescriptive(audioDescriptive);
			if (commentary && audioDescriptive)
				System.err.println("Commentary & descriptive");
			if (commentary)
				this.trackType = AudioTrackType.getStringArray()[3];
			else if (audioDescriptive)
				this.trackType = AudioTrackType.getStringArray()[4];
			else
				this.trackType = "";
		} else {
			this.trackType = trackType;
		}
	}

	public AudioCodec getAudio() {
		return audio;
	}

	public void setAudio(AudioCodec audio) {
		this.audio = audio;
	}

	public AudioChannels getChannels() {
		return channels;
	}

	public void setChannels(AudioChannels channels) {
		this.channels = channels;
	}

	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String value) {
		trackType = value;
	}

	public boolean isAudioDescriptive() {
		return audioDescriptive;
	}

	public void setAudioDescriptive(boolean audioDescriptive) {
		this.audioDescriptive = audioDescriptive;
	}	
}
