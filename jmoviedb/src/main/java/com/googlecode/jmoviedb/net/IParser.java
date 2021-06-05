package com.googlecode.jmoviedb.net;

import java.net.URL;
import java.util.ArrayList;

import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

public interface IParser {
    String getImdbID();
    String getTitle();
    String getOriginalTitle();
    MovieType getType(MovieType objectToEnum);
    int[] getYear();
    int getRuntime();
    double getRating();
    String getPlot();
    ArrayList<Language> getLanguages();
    ArrayList<Country> getCountries();
    ArrayList<Genre> getGenres();
    ArrayList<Person> getDirectors();
    ArrayList<Person> getWriters();
    ArrayList<ActorInfo> getActors();
    URL getImageURL();
    Integer getTmdbID();
    String getTmdbType();
}
