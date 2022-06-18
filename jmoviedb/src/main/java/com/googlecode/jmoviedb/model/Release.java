package com.googlecode.jmoviedb.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Release {
    private String releaseTitle;
	private Integer releaseYear;
	private ArrayList<Tuple<String, String>> territories;

    public Release() {
		territories = new ArrayList<Tuple<String, String>>();
	}

    public Release(AbstractMovie m) {
        this.releaseTitle = m.getReleaseTitle();
        this.releaseYear = m.getReleaseYear();
		setTerritoriesJson(m.getTerritories(), m.getClassifications());
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

	public ArrayList<Tuple<String, String>> getTerritories() {
		return territories;
	}

	public String getTerritoriesJson() {
		JSONArray json = new JSONArray();
		for (Tuple<String, String> territory : territories) {
			JSONObject obj = new JSONObject();
			obj.put(territory.getValue1(), territory.getValue2());
			json.put(obj);
		}
		return json.toString();
	}

	public void setTerritories(ArrayList<Tuple<String, String>> value) {
		territories = value;
	}

	public void setTerritoriesJson(String territoriesJson) {
		territories = new ArrayList<Tuple<String, String>>();
		if (Utils.isNullOrEmpty(territoriesJson))
			return;

		JSONArray territoriesArray = new JSONArray(territoriesJson);
		
		for (int i = 0; i < territoriesArray.length(); i++) {
			JSONObject territory = (JSONObject)territoriesArray.get(i);
			String territoryName = territory.keySet().toArray(new String[0])[0];
			String classification = territory.getString(territoryName);
			territories.add(new Tuple<String, String>(territoryName, classification));
		}
	}

	public void setTerritoriesJson(String territoriesJson, String classificationsJson) {
		territories = parseTerritories(territoriesJson, classificationsJson);
	}

	public static ArrayList<Tuple<String, String>> parseTerritories(String territoriesJson, String classificationsJson) {
		ArrayList<Tuple<String, String>> territories = new ArrayList<Tuple<String, String>>();
		if (Utils.isNullOrEmpty(territoriesJson) && Utils.isNullOrEmpty(classificationsJson))
			return territories;
		if (Utils.isNullOrEmpty(territoriesJson) != Utils.isNullOrEmpty(classificationsJson))
			throw new IllegalArgumentException("Territories and classifications must both be specified or both omitted");

		JSONArray territoriesArray = new JSONArray(territoriesJson);
		JSONArray classificationsArray = new JSONArray(classificationsJson);
		if (territoriesArray.length() != classificationsArray.length())
			throw new IllegalArgumentException("Territories and classifications must have the same length");
		
		for (int i = 0; i < territoriesArray.length(); i++) {
			String territory = (String)territoriesArray.get(i);
			String classificatoin = (String)classificationsArray.get(i);
			territories.add(new Tuple<String, String>(territory, classificatoin));
		}
		return territories;
	}
}
