package com.googlecode.jmoviedb.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class TmdbSeriesParser implements IParser {
    private TvSeries seriesEntry;
    public TmdbSeriesParser(TvSeries seriesEntry) {
        this.seriesEntry = seriesEntry;
    }

    @Override
    public String getImdbID() {
        if (seriesEntry.getExternalIds() != null)
            return seriesEntry.getExternalIds().getImdbId();
        return null;
    }

    @Override
    public String getTitle() {
        return seriesEntry.getName();
    }

    @Override
    public String getOriginalTitle() {
        return seriesEntry.getOriginalName();
    }

    @Override
    public MovieType getType(MovieType objectToEnum) {
        // if (seriesEntry.getMediaType())
        return MovieType.tvseries;
    }

    @Override
    public int[] getYear() {
        Integer yearStart = TmdbMovieParser.parseYearFromDate(seriesEntry.getFirstAirDate());
        Integer yearEnd = TmdbMovieParser.parseYearFromDate(seriesEntry.getLastAirDate());

        if (yearStart != null && yearEnd == null)
            return new int[] { yearStart };
        if (yearStart != null && yearEnd != null)
            return new int[] { yearStart, yearEnd };
        return null;
    }

    @Override
    public int getRuntime() {
        if (seriesEntry.getEpisodeRuntime() != null && !seriesEntry.getEpisodeRuntime().isEmpty())
            return seriesEntry.getEpisodeRuntime().get(0);
        return 0;
    }

    @Override
    public double getRating() {
        int rating10 = (int)(seriesEntry.getVoteAverage()*10);
        return ((double)rating10) / 10;
    }

    @Override
    public String getPlot() {
        return seriesEntry.getOverview();
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
        if (seriesEntry.getGenres() != null) {
            List<Genre> list = seriesEntry.getGenres().stream()
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
            if (!Utils.isNullOrEmpty(seriesEntry.getPosterPath()))
                return new URL("https://image.tmdb.org/t/p/" + "w342" + seriesEntry.getPosterPath());
        } catch (MalformedURLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Integer getTmdbID() {
        return seriesEntry.getId();
    }

    @Override
    public String getTmdbType() {
        return CONST.TMDB_TYPE_TV;
    }
}
