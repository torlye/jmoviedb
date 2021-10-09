package com.googlecode.jmoviedb.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

import org.eclipse.core.runtime.IProgressMonitor;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class TmdbMovieParser extends TmdbParser implements IParser {
    private MovieDb movieEntry;
    public TmdbMovieParser(MovieDb movieEntry, TmdbApi api, IProgressMonitor monitor) {
        super(api, monitor);
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
        if (movieEntry.getKeywords() != null) {
            boolean isMovieSerial = !movieEntry.getKeywords().stream().noneMatch(k -> k.getId() == 179991);
            if (isMovieSerial) return MovieType.movieserial;
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
        return getLanguages(movieEntry.getSpokenLanguages());
    }

    @Override
    public ArrayList<Country> getCountries() {
        return getCountriesFromProductionCountries(movieEntry.getProductionCountries());
    }

    @Override
    public ArrayList<Genre> getGenres() {
        return getGenres(movieEntry.getGenres());
    }

    @Override
    public ArrayList<Person> getDirectors() {
        return getDirectors(movieEntry.getCrew());
    }

    @Override
    public ArrayList<Person> getWriters() {
        return getWriters(movieEntry.getCrew());
    }

    @Override
    public ArrayList<ActorInfo> getActors() {
        return getActors(movieEntry.getCast());
    }

    @Override
    public URL getImageURL() {
        try {
            if (!Utils.isNullOrEmpty(movieEntry.getPosterPath()))
                return constructImageUrl(movieEntry.getPosterPath());
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

    @Override
    public String getTagline() {
        return movieEntry.getTagline();
    }
}
