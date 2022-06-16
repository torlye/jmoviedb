package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Release {
    private String releaseTitle;
	private Integer releaseYear;
	
    public Release() {}

    public Release(AbstractMovie m) {
        this.releaseTitle = m.getReleaseTitle();
        this.releaseYear = m.getReleaseYear();
    }

    public String getReleaseTitle() {
		return releaseTitle;
	}

	public void setReleaseTitle(String value) {
		releaseTitle = value;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer value) {
		releaseYear = value;
	}

	public void setReleaseYear(String value) {
		try {
			setReleaseYear(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			setReleaseYear((Integer)null);
		}
	}
}
