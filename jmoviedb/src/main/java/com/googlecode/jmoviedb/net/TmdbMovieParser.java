package com.googlecode.jmoviedb.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

import org.eclipse.core.runtime.IProgressMonitor;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.MovieDb;

public class TmdbMovieParser implements IParser {
    private MovieDb movieEntry;
    private TmdbApi api;
    private IProgressMonitor monitor;
    public TmdbMovieParser(MovieDb movieEntry, TmdbApi api, IProgressMonitor monitor) {
        this.movieEntry = movieEntry;
        this.api = api;
        this.monitor = monitor;
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
        var directorsList = new ArrayList<Person>();
        TmdbPeople people = api.getPeople();

        if (movieEntry.getCrew() != null)
        {
            var directors = movieEntry.getCrew().stream()
                .filter(c -> c.getJob().equals("Director"))
                .collect(Collectors.toList());
            
            this.monitor.beginTask("Getting director info", directors.size());
            for (int i = 0; i < directors.size(); i++) {
                var pc = directors.get(i);
                var pp = people.getPersonInfo(pc.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId()))
                    directorsList.add(new Person(pp.getImdbId(), pc.getName()));
                this.monitor.worked(1);
            }
        }
        return directorsList;
    }

    @Override
    public ArrayList<Person> getWriters() {
        var writersList = new ArrayList<Person>();
        TmdbPeople people = api.getPeople();

        if (movieEntry.getCrew() != null)
        {
            var writers = movieEntry.getCrew().stream()
                .filter(c -> c.getDepartment().equals("Writing"))
                .collect(Collectors.toList());
            
            this.monitor.beginTask("Getting writer info", writers.size());
            for (int i = 0; i < writers.size(); i++) {
                var pc = writers.get(i);
                var pp = people.getPersonInfo(pc.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId()))
                    writersList.add(new Person(pp.getImdbId(), pc.getName()));
                this.monitor.worked(1);
            }
        }
        return writersList;
    }

    @Override
    public ArrayList<ActorInfo> getActors() {
        TmdbPeople people = api.getPeople();
        var cast = movieEntry.getCast();
        var actors = new ArrayList<ActorInfo>();
        if (cast != null) {
            this.monitor.beginTask("Getting actor info", cast.size());
            for (int i = 0; i < cast.size(); i++) {
                var castInfo = cast.get(i);
                var pp = people.getPersonInfo(castInfo.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId()))
                    actors.add(new ActorInfo(i, new Person(pp.getImdbId(), castInfo.getName()), castInfo.getCharacter()));
                this.monitor.worked(1);
            }
        }
        return actors;
    }

    @Override
    public URL getImageURL() {
        try {
            if (!Utils.isNullOrEmpty(movieEntry.getPosterPath()))
                return new URL(Settings.getSettings().getTmdbImgUrl() + Settings.getSettings().getTmdbImgSize() + movieEntry.getPosterPath());
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

    @Override
    public String getTagline() {
        return movieEntry.getTagline();
    }
}
