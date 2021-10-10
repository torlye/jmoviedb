package com.googlecode.jmoviedb.net;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind.ExternalSource;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.TmdbTV.TvMethod;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class TmdbDownloader extends AbstractDownloader implements IRunnableWithProgress {
	protected AbstractMovie movie;
	public TmdbDownloader(AbstractMovie m) {
		movie = m;
	}
	
	public AbstractMovie getMovie() {
		return movie;
	}

	private static final String lang = "en";
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			monitor.beginTask("Importing information from TMDB", IProgressMonitor.UNKNOWN);
			TmdbApi api = new TmdbApi(Settings.getSettings().getTmdbApiKey());
			if (movie.isTmdbUrlValid() && movie.getTmdbType().equals(CONST.TMDB_TYPE_MOVIE))
			{
				importMovieData(monitor, api);	
			}
			else if (movie.isTmdbUrlValid() && movie.getTmdbType().equals(CONST.TMDB_TYPE_TV))
			{
				importTvData(monitor, api);
			}
			else if (movie.isImdbUrlValid()) {
				FindResults fr = api.getFind().find("tt"+movie.getImdbID(), ExternalSource.imdb_id, lang);
				List<MovieDb> mList = fr.getMovieResults();
				List<TvSeries> tvList = fr.getTvResults();
				
				if (mList.size() == 1 && tvList.isEmpty()) {
					MovieDb tmdbMovie = mList.get(0);
					IParser parser = new TmdbMovieParser(tmdbMovie, api, monitor);
					movie = importData(parser, movie, monitor);
					importMovieData(monitor, api);
				}
				else if (tvList.size() == 1 && mList.isEmpty()) {
					TvSeries series = tvList.get(0);
					IParser parser = new TmdbSeriesParser(series, api, monitor);
					movie = importData(parser, movie, monitor);
					importTvData(monitor, api);
				}
				else {
					if (mList.isEmpty() && tvList.isEmpty())
						throw new TmdbImportException("No matches found");
					else {
						String errorMsg = "Ambiguous match. More than one result:\n";
						List<String> urls1 = mList.stream().map(m -> CONST.TMDB_BASE_URL + CONST.TMDB_TYPE_MOVIE + "/" + m.getId()).collect(Collectors.toList());
						List<String> urls2 = tvList.stream().map(m -> CONST.TMDB_BASE_URL + CONST.TMDB_TYPE_TV + "/" + m.getId()).collect(Collectors.toList());
						urls1.addAll(urls2);
						errorMsg += String.join("\n", urls1);
						throw new TmdbImportException(errorMsg);
					}
				}
			}
		
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
		}
	}

	private void importTvData(IProgressMonitor monitor, TmdbApi api) {
		TvSeries series = api.getTvSeries().getSeries(movie.getTmdbID(), lang, TvMethod.credits, TvMethod.keywords);
		IParser parser = new TmdbSeriesParser(series, api, monitor);
		movie = importData(parser, movie, monitor);
	}

	private void importMovieData(IProgressMonitor monitor, TmdbApi api) {
		MovieDb tmdbMovie = api.getMovies().getMovie(movie.getTmdbID(), lang, MovieMethod.credits, MovieMethod.keywords);
		IParser parser = new TmdbMovieParser(tmdbMovie, api, monitor);
		movie = importData(parser, movie, monitor);
	}
}
