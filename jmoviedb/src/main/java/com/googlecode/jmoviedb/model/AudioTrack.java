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

import com.googlecode.jmoviedb.enumerated.*;

public class AudioTrack {
	private Language language;
	private AudioCodec audio;
	private AudioChannels channels;
	private boolean commentary;
	private boolean audioDescriptive;
	
	public AudioTrack(Language language, AudioCodec audio, AudioChannels channels, boolean commentary, boolean audioDescriptive) {
		this.language = language;
		this.audio = audio;
		this.commentary = commentary;
		this.channels = channels;
		this.setAudioDescriptive(audioDescriptive);
	}

	public AudioCodec getAudio() {
		return audio;
	}

	public void setAudio(AudioCodec audio) {
		this.audio = audio;
	}

	public boolean isCommentary() {
		return commentary;
	}

	public void setCommentary(boolean commentary) {
		this.commentary = commentary;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public AudioChannels getChannels() {
		return channels;
	}

	public void setChannels(AudioChannels channels) {
		this.channels = channels;
	}

	public boolean isAudioDescriptive() {
		return audioDescriptive;
	}

	public void setAudioDescriptive(boolean audioDescriptive) {
		this.audioDescriptive = audioDescriptive;
	}	
}
