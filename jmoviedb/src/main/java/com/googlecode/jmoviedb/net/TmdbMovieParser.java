package com.googlecode.jmoviedb.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

import info.movito.themoviedbapi.model.MovieDb;

public class TmdbMovieParser implements IParser {
    private MovieDb movieEntry;
    public TmdbMovieParser(MovieDb movieEntry) {
        this.movieEntry = movieEntry;
    }

    @Override
    public String getImdbID() {
        return movieEntry.getImdbID();
    }

    @Override
    public String getTitle() {
        return movieEntry.getTitle();
    }

    @Override
    public String getOriginalTitle() {
        return movieEntry.getOriginalTitle();
    }

    @Override
    public MovieType getType(MovieType objectToEnum) {
        if (movieEntry.getGenres() != null) {
            boolean isTvMovie = !movieEntry.getGenres().stream().noneMatch(g -> g.getId() == 10770);
            if (isTvMovie) return MovieType.tvmovie;
        }
        return MovieType.film;
    }

    @Override
    public int[] getYear() {
        Integer year = parseYearFromDate(movieEntry.getReleaseDate());
        if (year != null)
            return new int[] { year };
        return null;
    }

    @Override
    public int getRuntime() {
        return movieEntry.getRuntime();
    }

    @Override
    public double getRating() {
        int rating10 = (int)(movieEntry.getVoteAverage()*10);
        return ((double)rating10) / 10;
    }

    @Override
    public String getPlot() {
        return movieEntry.getOverview();
    }

    @Override
    public ArrayList<Language> getLanguages() {
        // TODO Auto-generated method stub
        return new ArrayList<Language>();
    }

    @Override
    public ArrayList<Country> getCountries() {
        // TODO Auto-generated method stub
        return new ArrayList<Country>();
    }

    @Override
    public ArrayList<Genre> getGenres() {
        if (movieEntry.getGenres() != null) {
            List<Genre> list = movieEntry.getGenres().stream()
                .map(g -> Genre.tmdbGenreToEnum(g))
                .filter(v -> v != null)
                .collect(Collectors.toList());
            return new ArrayList<Genre>(list);
        }
        return new ArrayList<Genre>();
    }

    @Override
    public ArrayList<Person> getDirectors() {
        // TODO Auto-generated method stub
        return new ArrayList<Person>();
    }

    @Override
    public ArrayList<Person> getWriters() {
        // TODO Auto-generated method stub
        return new ArrayList<Person>();
    }

    @Override
    public ArrayList<ActorInfo> getActors() {
        // TODO Auto-generated method stub
        return new ArrayList<ActorInfo>();
    }

    @Override
    public URL getImageURL() {
        try {
            if (!Utils.isNullOrEmpty(movieEntry.getPosterPath()))
                return new URL("https://image.tmdb.org/t/p/" + "w342" + movieEntry.getPosterPath());
        } catch (MalformedURLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Integer getTmdbID() {
        return movieEntry.getId();
    }

    @Override
    public String getTmdbType() {
        return CONST.TMDB_TYPE_MOVIE;
    }

    public static Integer parseYearFromDate(String date)
    {
        if (date == null)
            return null;

        Matcher yearMatcher = Pattern.compile("(\\d{4})-\\d{2}-\\d{2}").matcher(date);
        if (yearMatcher.find())
            return Integer.parseInt(yearMatcher.group(1));
        
        return null;
    }
}
