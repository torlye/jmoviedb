package com.googlecode.jmoviedb.net;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind.ExternalSource;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class TmdbUrlAdd extends AbstractDownloader implements IRunnableWithProgress {
	public String failedMsg = "";
	public int skipped = 0;
	public int completed = 0;
	public int failed = 0;
	public List<AbstractMovie> moviesToSave;

	public TmdbUrlAdd() {
		moviesToSave = new ArrayList<AbstractMovie>();
	}
	
	private static final String lang = "en";
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			List<AbstractMovie> list = MainWindow.getMainWindow().getDB().getDatabase().getMovieList();
			monitor.beginTask("Getting TMDB URLs", list.size());
			
			
			for (AbstractMovie movie : list)
			{
				if (monitor.isCanceled())
					throw new InterruptedException();
				
				monitor.subTask(movie.getTitle());
				TmdbApi api = new TmdbApi(Settings.getSettings().getTmdbApiKey());
				if (movie.isTmdbUrlValid()) {
					skipped++;
				}
				else if (movie.isImdbUrlValid()) {
					FindResults fr = api.getFind().find("tt"+movie.getImdbID(), ExternalSource.imdb_id, lang);
					List<MovieDb> mList = fr.getMovieResults();
					List<TvSeries> tvList = fr.getTvResults();
					
					if (mList.size() == 1 && tvList.isEmpty()) {
						MovieDb tmdbMovie = mList.get(0);
						movie.setTmdbID(tmdbMovie.getId());
						movie.setTmdbType(CONST.TMDB_TYPE_MOVIE);
						moviesToSave.add(movie);
						completed++;
					}
					else if (tvList.size() == 1 && mList.isEmpty()) {
						TvSeries series = tvList.get(0);
						movie.setTmdbID(series.getId());
						movie.setTmdbType(CONST.TMDB_TYPE_TV);
						moviesToSave.add(movie);
						completed++;
					}
					else {
						if (mList.isEmpty() && tvList.isEmpty())
							failedMsg += "Failed: " + movie.getTitle() + ". No matches found\n\n";
						else {
							failedMsg += "Failed: " + movie.getTitle() + ". Ambiguous match. More than one result:\n";
							List<String> urls1 = mList.stream().map(m -> CONST.TMDB_BASE_URL + CONST.TMDB_TYPE_MOVIE + "/" + m.getId()).collect(Collectors.toList());
							List<String> urls2 = tvList.stream().map(m -> CONST.TMDB_BASE_URL + CONST.TMDB_TYPE_TV + "/" + m.getId()).collect(Collectors.toList());
							urls1.addAll(urls2);
							failedMsg += String.join("\n", urls1);
							failedMsg += "\n\n";
						}
						failed++;
					}
				}
				else {
					failedMsg += "Failed: " + movie.getTitle() + ". No IMDb URL\n\n";
					failed++;
				}
				monitor.worked(1);
			}
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
		}
	}
}
