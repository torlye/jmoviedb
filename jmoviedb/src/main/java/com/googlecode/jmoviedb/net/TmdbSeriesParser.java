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

import org.eclipse.core.runtime.IProgressMonitor;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class TmdbSeriesParser extends TmdbParser implements IParser {
    private TvSeries seriesEntry;
    public TmdbSeriesParser(TvSeries seriesEntry, TmdbApi api, IProgressMonitor monitor) {
        super(api, monitor);
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
        Integer yearStart = parseYearFromDate(seriesEntry.getFirstAirDate());
        Integer yearEnd = parseYearFromDate(seriesEntry.getLastAirDate());

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
        // TODO missing access to spoken_languages and languages and original_language
        // TODO Auto-generated method stub
        return new ArrayList<Language>();
    }

    @Override
    public ArrayList<Country> getCountries() {
        return getCountries(seriesEntry.getOriginCountry());
    }

    @Override
    public ArrayList<Genre> getGenres() {
        return getGenres(seriesEntry.getGenres());
    }

    @Override
    public ArrayList<Person> getDirectors() {
        if (seriesEntry.getCredits() != null)
            return getDirectors(seriesEntry.getCredits().getCrew());
        return new ArrayList<Person>();
    }

    @Override
    public ArrayList<Person> getWriters() {
        if (seriesEntry.getCredits() != null)
            return getWriters(seriesEntry.getCredits().getCrew());
        return new ArrayList<Person>();
    }

    @Override
    public ArrayList<ActorInfo> getActors() {
        if (seriesEntry.getCredits() != null)
            return getActors(seriesEntry.getCredits().getCast());
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

    @Override
    public String getTagline() {
        //return seriesEntry.getTagline();
        // TODO missing tagline in API
        return "";
    }
}
