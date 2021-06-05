package com.googlecode.jmoviedb.net;

import java.io.IOException;
import java.net.URL;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import org.eclipse.core.runtime.IProgressMonitor;

public abstract class AbstractDownloader {
    AbstractMovie importData(IParser parser, AbstractMovie movie, IProgressMonitor monitor) {
        MovieType imdbType = parser.getType(MovieType.objectToEnum(movie));
        
        System.out.println("Testing movie type, old: "+MovieType.objectToEnum(movie).getName() + 
                " new: " + imdbType.getName());
        if(MovieType.objectToEnum(movie) != imdbType) {
            if(CONST.DEBUG_MODE)
                System.out.println("Switching movie type from "+MovieType.objectToEnum(movie).getName() + 
                        " to " + imdbType.getName());
            AbstractMovie newMovie = MovieType.intToAbstractMovie(imdbType.getId());
            newMovie = movie.copyTo(newMovie);
            movie = newMovie;
        }

        handleIds(parser, movie);
        
        handleTitles(parser, movie);
        
        handleYear(parser, movie);
        
        int runtime = parser.getRuntime();
        if(runtime>0) movie.setRunTime(runtime);
        
        double rating = parser.getRating();
        if(rating>0) movie.setRating(rating);
        
        //boolean color = parser.isColor();
        //if (!color)
        //	movie.setColor(color);
        
        movie.setPlotOutline(parser.getPlot());
        movie.setTagline(parser.getTagline());

        movie.setLanguages(parser.getLanguages());
        movie.setCountries(parser.getCountries());
        movie.setGenres(parser.getGenres());
        movie.setDirectors(parser.getDirectors());
        movie.setWriters(parser.getWriters());
        movie.setActors(parser.getActors());

        URL imageUrl = parser.getImageURL();
        if (imageUrl != null) {
            monitor.subTask("Downloading cover image");
            try {
                movie.setImageBytes(new DownloadWorker(imageUrl).downloadImage());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to download image.");
            }
        }
        return movie;
    }

    private void handleYear(IParser parser, AbstractMovie movie) {
        int[] year = parser.getYear();
        if (year != null) {
            if(year.length>0) movie.setYear(year[0]);
            if(year.length>1) movie.setYear2(year[1]);
        }
    }

    private void handleTitles(IParser parser, AbstractMovie movie) {
        String title = parser.getTitle();
        String originalTitle = parser.getOriginalTitle();
        if(originalTitle != null) {
            movie.setTitle(originalTitle);
            if(title != null && !title.equals(originalTitle))
                movie.setCustomTitle(title);
        } 
        else if(title != null)
            movie.setTitle(title);
    }

    private void handleIds(IParser parser, AbstractMovie movie) {
        if (movie.getTmdbID() == null)
            movie.setTmdbID(parser.getTmdbID());
        
        if (Utils.isNullOrEmpty(movie.getTmdbType()))
            movie.setTmdbType(parser.getTmdbType());
        
        String imdbId = parser.getImdbID();
        if (Utils.isNullOrEmpty(movie.getImdbID()) && !Utils.isNullOrEmpty(imdbId))
            movie.setImdbID(imdbId);
    }
}
