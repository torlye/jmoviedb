package com.googlecode.jmoviedb.model;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Release {
    private String releaseTitle;
	private Integer releaseYear;
	private Tuple<String, String> territories;

    public Release() {}

    public Release(AbstractMovie m) {
        this.releaseTitle = m.getReleaseTitle();
        this.releaseYear = m.getReleaseYear();
		this.territories = m.getTerritories();
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

	public Tuple<String, String> getTerritories() {
		return territories;
	}

	public String getTerritoriesJson() {

	}

	public void setTerritories(Tuple<String, String> value) {
		territories = value;
	}

	public void setTerritoriesJson(String string) {
		territories = new Tuple<String, String>(string);
	}
}
