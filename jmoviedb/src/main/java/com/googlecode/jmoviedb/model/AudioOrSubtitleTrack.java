package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.enumerated.Language;

public abstract class AudioOrSubtitleTrack {
    protected Language language;
    protected boolean commentary;
	protected String languageString;
	protected String note;
	protected String trackType;

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

	public String getLanguageString() {
		return languageString;
	}

	public void setLanguageString(String value) {
		languageString = value;
		language = Language.StringToEnum(value);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String value) {
		note = value;
	}
	
	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String value) {
		trackType = value;
	}
}