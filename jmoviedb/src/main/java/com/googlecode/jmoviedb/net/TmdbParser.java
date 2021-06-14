package com.googlecode.jmoviedb.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.Person;

import org.eclipse.core.runtime.IProgressMonitor;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.ProductionCountry;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.people.PersonPeople;

public abstract class TmdbParser {
    protected TmdbApi api;
    protected IProgressMonitor monitor;

    public TmdbParser(TmdbApi api, IProgressMonitor monitor)
    {
        this.api = api;
        this.monitor = monitor;
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

    protected ArrayList<Person> getDirectors(List<PersonCrew> crew)
    {
        ArrayList<Person> directorsList = new ArrayList<Person>();
        TmdbPeople people = api.getPeople();

        if (crew != null)
        {
            List<PersonCrew> directors = crew.stream()
                .filter(c -> c.getJob().equals("Director"))
                .collect(Collectors.toList());
            
            this.monitor.beginTask("Getting director info", directors.size());
            for (int i = 0; i < directors.size(); i++) {
                PersonCrew pc = directors.get(i);
                PersonPeople pp = people.getPersonInfo(pc.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId())) {
                    Person p = new Person(pp.getImdbId(), pc.getName());
                    if (!directorsList.contains(p))
                        directorsList.add(p);
                }
                this.monitor.subTask(pc.getName());
                this.monitor.worked(1);
            }
        }
        return directorsList;
    }

    public ArrayList<Person> getWriters(List<PersonCrew> crew) {
        ArrayList<Person> writersList = new ArrayList<Person>();
        TmdbPeople people = api.getPeople();

        if (crew != null)
        {
            List<PersonCrew> writers = crew.stream()
                .filter(c -> c.getDepartment().equals("Writing"))
                .collect(Collectors.toList());
            
            this.monitor.beginTask("Getting writer info", writers.size());
            for (int i = 0; i < writers.size(); i++) {
                PersonCrew pc = writers.get(i);
                PersonPeople pp = people.getPersonInfo(pc.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId())) {
                    Person p = new Person(pp.getImdbId(), pc.getName());
                    if (!writersList.contains(p))
                        writersList.add(p);
                }
                this.monitor.subTask(pc.getName());
                this.monitor.worked(1);
            }
        }
        return writersList;
    }

    public ArrayList<ActorInfo> getActors(List<PersonCast> cast) {
        TmdbPeople people = api.getPeople();
        ArrayList<ActorInfo> actors = new ArrayList<ActorInfo>();
        if (cast != null) {
            this.monitor.beginTask("Getting actor info", cast.size());
            for (int i = 0; i < cast.size(); i++) {
                PersonCast castInfo = cast.get(i);
                PersonPeople pp = people.getPersonInfo(castInfo.getId());
                if (!Utils.isNullOrEmpty(pp.getImdbId())) {
                    Optional<ActorInfo> existingActorInfo = actors.stream().filter(ai -> ("nm"+ai.getPerson().getID()).equals(pp.getImdbId())).findAny();
                    if (existingActorInfo.isPresent())
                        existingActorInfo.get().setCharacter(existingActorInfo.get().getCharacter() + ", " + castInfo.getCharacter());
                    else
                        actors.add(new ActorInfo(i, new Person(pp.getImdbId(), castInfo.getName()), castInfo.getCharacter()));
                }
                this.monitor.subTask(castInfo.getName());
                this.monitor.worked(1);
            }
        }
        return actors;
    }

    public ArrayList<Genre> getGenres(List<info.movito.themoviedbapi.model.Genre> genres) {
        if (genres != null) {
            List<Genre> list = genres.stream()
                .map(g -> Genre.tmdbGenreToEnum(g))
                .filter(v -> v != null)
                .distinct()
                .collect(Collectors.toList());
            return new ArrayList<Genre>(list);
        }
        return new ArrayList<Genre>();
    }

    public ArrayList<Country> getCountries(List<String> countryCodes) {
        if (countryCodes != null)
        {
            List<Country> list = countryCodes.stream()
                .map(c -> Country.iso3166ToEnum(c))
                .filter(c -> c != null)
                .distinct()
                .collect(Collectors.toList());
            return new ArrayList<Country>(list);
        }
        return new ArrayList<Country>();
    }

    public ArrayList<Country> getCountriesFromProductionCountries(List<ProductionCountry> countryCodes) {
        if (countryCodes != null)
        {
            List<Country> list = countryCodes.stream()
                .map(c -> Country.tmdbCountryToEnum(c))
                .filter(c -> c != null)
                .distinct()
                .collect(Collectors.toList());
            return new ArrayList<Country>(list);
        }
        return new ArrayList<Country>();
    }

    public ArrayList<Language> getLanguages(List<info.movito.themoviedbapi.model.Language> languages) {
        if (languages != null)
        {
            List<Language> list = languages.stream()
                .map(c -> Language.tmdbLanguageToEnum(c))
                .filter(c -> c != null)
                .distinct()
                .collect(Collectors.toList());
            return new ArrayList<Language>(list);
        }
        return new ArrayList<Language>();
    }
}
