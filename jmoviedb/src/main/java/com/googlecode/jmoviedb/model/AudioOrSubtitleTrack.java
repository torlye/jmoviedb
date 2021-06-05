package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.enumerated.Language;

public abstract class AudioOrSubtitleTrack {
    protected Language language;
    protected boolean commentary;

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
}