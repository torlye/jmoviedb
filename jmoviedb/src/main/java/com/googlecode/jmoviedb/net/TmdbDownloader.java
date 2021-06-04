package com.googlecode.jmoviedb.net;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.TVmovie;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.TmdbFind.ExternalSource;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class TmdbDownloader implements IRunnableWithProgress {
	protected AbstractMovie movie;
	public TmdbDownloader(AbstractMovie m) {
		movie = m;
	}
	
	public AbstractMovie getMovie() {
		return movie;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			monitor.beginTask("Importing information from TMDb", IProgressMonitor.UNKNOWN);
			
			TmdbFind find = new TmdbApi("c51bf3dd883efc813c9d6b2477465bf8").getFind();
			FindResults fr = find.find("tt"+movie.getImdbID(), ExternalSource.imdb_id, "en");
			List<MovieDb> mList = fr.getMovieResults();
			List<TvSeries> tvList = fr.getTvResults();
			
			if (mList.size() == 1) {
				MovieDb tmdbEntry = mList.get(0);
				
				if (movie.getTmdbID() == null)
					movie.setTmdbID(tmdbEntry.getId());
				if (movie.getTmdbType() == null)
					movie.setTmdbType("movie");
				if (movie.getImdbID() == null || movie.getImdbID().isEmpty())
					movie.setImdbID(tmdbEntry.getImdbID());
				
				String title = tmdbEntry.getTitle();
				String originalTitle = tmdbEntry.getOriginalTitle();
				
				if(originalTitle != null) {
					movie.setTitle(originalTitle);
					if(title != null && title != originalTitle)
						movie.setCustomTitle(title);
				} 
				else if(title != null)
					movie.setTitle(title);
				
				Matcher yearMatcher = Pattern.compile("d{4}").matcher(tmdbEntry.getReleaseDate());
				if (yearMatcher.find())
					movie.setYear(yearMatcher.group(0));
				
				movie.setRunTime(tmdbEntry.getRuntime());
				
				movie.setRating(tmdbEntry.getVoteAverage());
				movie.setPlotOutline(tmdbEntry.getOverview());
				
				if (tmdbEntry.getGenres() != null) {
					movie.setGenres(tmdbEntry.getGenres().stream()
							.map(g -> Genre.tmdbGenreToEnum(g))
							.filter(v -> v != null)
							.collect(Collectors.toList()));
					
					boolean isTvMovie = !tmdbEntry.getGenres().stream().noneMatch(g -> g.getId() == 10770);
					if (isTvMovie && !(movie instanceof TVmovie)) {
						TVmovie tvm = new TVmovie();
						movie.copyTo(tvm);
						movie = tvm;
					}
				}
			
			}
		
		} catch (Exception e) {
			System.out.println(e);
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
		}
	}

}
