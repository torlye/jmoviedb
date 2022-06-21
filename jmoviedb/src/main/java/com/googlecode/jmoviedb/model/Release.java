package com.googlecode.jmoviedb.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class Release {
    private String releaseTitle;
	private Integer releaseYear;
	private ArrayList<Tuple<String, String>> territories;
	private ArrayList<Tuple<String, Integer>> media;
	private ArrayList<Tuple<String, String>> identifiers;
	private ArrayList<String> releaseTypes;
	private ArrayList<String> companies;

    public Release() {
		this.releaseTitle = "";
		territories = new ArrayList<Tuple<String, String>>();
		media = new ArrayList<Tuple<String, Integer>>();
		identifiers = new ArrayList<Tuple<String, String>>();
		releaseTypes = new ArrayList<String>();
		companies = new ArrayList<String>();
	}

    public Release(AbstractMovie m) {
        this.releaseTitle = m.getReleaseTitle();
        this.releaseYear = m.getReleaseYear();
		setTerritoriesJson(m.getTerritories(), m.getClassifications());
		setMediaJson(m.getMedia());
		setIdentifiersJson(m.getIdentifiers());
		setReleaseTypesJson(m.getReleaseType());
		setCompaniesJson(m.getCompaniesJson());
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

	public ArrayList<Tuple<String, Integer>> getMedia() {
		return media;
	}

	public void setMedia(ArrayList<Tuple<String, Integer>> value) {
		this.media = value;
	}

	public void setMediaJson(String json) {
		media = parseMedia(json);
	}

	public static ArrayList<Tuple<String, Integer>> parseMedia(String json) {
		ArrayList<Tuple<String, Integer>> media = new ArrayList<Tuple<String, Integer>>();
		if (Utils.isNullOrEmpty(json))
			return media;

		JSONArray array = new JSONArray(json);

		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject)array.get(i);
			String name = obj.keySet().toArray(new String[0])[0];
			Integer value = obj.getInt(name);
			media.add(new Tuple<String, Integer>(name, value));
		}
		return media;
	}

	public String getMediaJson() {
		JSONArray json = new JSONArray();
		for (Tuple<String, Integer> med : media) {
			JSONObject obj = new JSONObject();
			obj.put(med.getValue1(), med.getValue2());
			json.put(obj);
		}
		return json.toString();
	}

	public ArrayList<Tuple<String, String>> getIdentifiers() {
		return identifiers;
	}

	public String getIdentifiersJson() {
		JSONArray json = new JSONArray();
		for (Tuple<String, String> med : identifiers) {
			JSONObject obj = new JSONObject();
			obj.put(med.getValue1(), med.getValue2());
			json.put(obj);
		}
		return json.toString();
	}

	public void setIdentifiers(ArrayList<Tuple<String, String>> value) {
		this.identifiers = value;
	}

	public void setIdentifiersJson(String json) {
		identifiers = parseIdentifiers(json);
	}

	public static ArrayList<Tuple<String, String>> parseIdentifiers(String json) {
		ArrayList<Tuple<String, String>> identifiers = new ArrayList<Tuple<String, String>>();
		if (Utils.isNullOrEmpty(json))
			return identifiers;

		JSONArray array = new JSONArray(json);

		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject)array.get(i);
			String name = obj.keySet().toArray(new String[0])[0];
			String value = obj.getString(name);
			identifiers.add(new Tuple<String, String>(name, value));
		}

		return identifiers;
	}

	public ArrayList<String> getReleaseTypes() {
		return releaseTypes;
	}

	public String getReleaseTypesJson() {
		return serializeToJsonArray(releaseTypes);
	}

	private static String serializeToJsonArray(ArrayList<String> value) {
		JSONArray json = new JSONArray();
		for (String r : value) {
			json.put(r);
		}
		return json.toString();
	}

	public void setReleaseTypes(ArrayList<String> value) {
		releaseTypes = value;
	}

	public void setReleaseTypes(List<String> value) {
		releaseTypes = new ArrayList<String>(value);
	}

	public void setReleaseTypesJson(String json) {
		releaseTypes = parseJsonArray(json);
	}

    public static ArrayList<String> parseJsonArray(String json) {
        ArrayList<String> collection = new ArrayList<String>();
		if (Utils.isNullOrEmpty(json))
			return collection;

		JSONArray array = new JSONArray(json);

		for (int i = 0; i < array.length(); i++) {
			String s = (String)array.get(i);
			collection.add(s);
		}
		return collection;
    }

	public String getCompaniesJson() {
		return serializeToJsonArray(companies);
	}

    public void setCompaniesJson(String json) {
		companies = parseJsonArray(json);
    }

    public ArrayList<String> getCompanies() {
        return companies;
    }

	public void setCompanies(ArrayList<String> value) {
		companies = value;
	}

    public void setCompanies(List<String> list) {
		companies = new ArrayList<String>(list);
    }
}
