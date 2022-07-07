package com.googlecode.jmoviedb.gui.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.json.JSONArray;
import org.json.JSONObject;

import com.csvreader.CsvWriter;
import com.googlecode.jmoviedb.enumerated.Completeness;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.SubtitleTrack;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.AbstractSeries;

public class CSVexportReleases implements IRunnableWithProgress {

	String filepath;
	private int read;
	private int skipped;
	CsvWriter releaseWriter;
	CsvWriter unofficialReleaseWriter;
	CsvWriter movieReleaseWriter;
	CsvWriter unofficialMovieReleaseWriter;
	CsvWriter movieWriter;
	List<AbstractMovie> moviesToWrite;
	
	CSVexportReleases(String filepath)
	{
		this.filepath = filepath;
		movieWriter = new CsvWriter(filepath + "-movie.csv", ',', Charset.forName("UTF-8"));
		releaseWriter = new CsvWriter(filepath + "-release.csv", ',', Charset.forName("UTF-8"));
		unofficialReleaseWriter = new CsvWriter(filepath + "-unofficialRelease.csv", ',', Charset.forName("UTF-8"));
		movieReleaseWriter = new CsvWriter(filepath+"-movieRelease.csv", ',', Charset.forName("UTF-8"));
		unofficialMovieReleaseWriter = new CsvWriter(filepath+"-unofficialMovieRelease.csv", ',', Charset.forName("UTF-8"));
		moviesToWrite = new ArrayList<AbstractMovie>();
	}
	
	@Override
	public void run(IProgressMonitor progress) throws InvocationTargetException, InterruptedException {
		try {
			ArrayList<AbstractMovie> list  = MainWindow.getMainWindow().getDB().getDatabase().getMovieList();
			progress.beginTask("Exporting", list.size());
			
			writeHeaderLine();
			
			Map<String, List<AbstractMovie>> officialReleases = list.stream()
				.filter(t -> t.isLegal())
				.collect(Collectors.groupingBy(AbstractMovie::getUrl2String));

			List<AbstractMovie> unofficialMovies = list.stream()
				.filter(t -> !t.isLegal()).collect(Collectors.toList());
			

			for (String key : officialReleases.keySet())
			{
				try
				{
					List<AbstractMovie> movies = officialReleases.get(key);

					if (key == null || key.isEmpty()) {
						for (AbstractMovie mov : movies) {
							ArrayList<AbstractMovie> singleMovieCollection = new ArrayList<AbstractMovie>();
							singleMovieCollection.add(mov);
							writeRecordForRelease(singleMovieCollection, mov, releaseWriter, movieReleaseWriter);
							read++;
							progress.worked(1);
						}
					}
					else {
						AbstractMovie first = movies.get(0);
						writeRecordForRelease(movies, first, releaseWriter, movieReleaseWriter);
						read++;
						progress.worked(1);
					}
				}
				catch (Exception e) {
					skipped++;
					releaseWriter.writeComment(e.getMessage()+"\n"+e.getStackTrace());
				}
				finally
				{
					progress.worked(1);
				}
			}
			releaseWriter.flush();
			releaseWriter.close();
			movieReleaseWriter.flush();
			movieReleaseWriter.close();
			
			try {
				for (AbstractMovie mov : unofficialMovies) {
					ArrayList<AbstractMovie> singleMovieCollection = new ArrayList<AbstractMovie>();
					singleMovieCollection.add(mov);
					writeRecordForRelease(singleMovieCollection, mov, unofficialReleaseWriter, unofficialMovieReleaseWriter);
					read++;
					progress.worked(1);
				}
				unofficialReleaseWriter.flush();
				unofficialReleaseWriter.close();
				unofficialMovieReleaseWriter.flush();
				unofficialMovieReleaseWriter.close();
			
				if (moviesToWrite.size() > 0) {

					movieWriter.writeRecord(new String[] {
						"id", "title", "imdb", "tmdb", "type"
					});

					for (AbstractMovie movie : moviesToWrite) {
						String[] record = new String[5];
						record[0] = movie.getID()+"";
						record[1] = movie.getTitle();
						record[2] = movie.getImdbUrl();
						record[3] = movie.getTmdbUrl();
						record[4] = MovieType.abstractMovieToEnum(movie).getName();
						movieWriter.writeRecord(record);
					}

					movieWriter.flush();
					movieWriter.close();
				}
			}
			finally {}
		}
		// Any other exceptions should spawn an InvocationTargetException.
		catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			progress.done();
		}
	}

	private void writeHeaderLine() throws IOException {
		String[] releaseHeader = new String[] {
			"id", "title", "territories", "classification", "year", //0-4
			"identifiers", "urls", "media", "releaseType", "edition" //5-9
		};
		releaseWriter.writeRecord(releaseHeader);
		unofficialReleaseWriter.writeRecord(releaseHeader);

		String [] movieReleaseHeader = new String[] {
			"id", "releaseid", "movieid", "format", "container", //0-4
			"videoformat", "videoresolution", "aspectratio", "videosystem", "color", //5-9
			"audiotracks", "subtitles", "regioncode", "notes", "completeness" //10-14
		};

		movieReleaseWriter.writeRecord(movieReleaseHeader);
		unofficialMovieReleaseWriter.writeRecord(movieReleaseHeader);
	}

	private void writeRecordForRelease(List<AbstractMovie> movies, AbstractMovie firstMovie, CsvWriter releaseWriter, CsvWriter movieReleaseWriter)
			throws IOException {
		String[] releaseRecord = new String[10];
		String releaseId = firstMovie.getID()+"";
		releaseRecord[0] = releaseId;
		releaseRecord[1] = firstMovie.getReleaseTitle();
		if (releaseRecord[1] == null || releaseRecord[1].isBlank()) {
			releaseRecord[1] = firstMovie.getTitle();
		}
		releaseRecord[2] = firstMovie.getTerritories();
		releaseRecord[3] = firstMovie.getClassifications();
		releaseRecord[4] = firstMovie.getReleaseYear() + "";

		releaseRecord[5] = firstMovie.getIdentifiers();
		releaseRecord[6] = firstMovie.getReleaseUrls();
		releaseRecord[7] = firstMovie.getMedia();
		releaseRecord[8] = firstMovie.getReleaseType();
		releaseRecord[9] = firstMovie.getCustomVersion();
							
		releaseWriter.writeRecord(releaseRecord);

		for (AbstractMovie movie : movies) {
			String[] movieReleaseRecord = new String[15];
			movieReleaseRecord[0] = movie.getID()+"";
			movieReleaseRecord[1] = releaseId;
			movieReleaseRecord[3] = movie.getFormat().getShortName();
			movieReleaseRecord[4] = movie.getContainer().getShortName();
			
			movieReleaseRecord[5] = movie.getVideo().getShortName();
			movieReleaseRecord[6] = movie.getResolution().getName();
			movieReleaseRecord[7] = movie.getAspectRatio().getShortName();
			movieReleaseRecord[8] = movie.getTvSystem().getShortName();
			movieReleaseRecord[9] = movie.getColor().getName();

			Optional<AbstractMovie> existingMovie = moviesToWrite.stream()
				.filter(m -> m.isImdbUrlValid() && m.getImdbID().equals(movie.getImdbID())).findFirst();
			if (existingMovie.isPresent()) {
				movieReleaseRecord[2] = existingMovie.get().getID()+"";
			}
			else {
				moviesToWrite.add(movie);
				movieReleaseRecord[2] = movie.getID()+"";
			}

			movieReleaseRecord[10] = getAudio(movie);
			movieReleaseRecord[11] = getSubtitle(movie);
			movieReleaseRecord[12] = getRegion(movie);
			movieReleaseRecord[13] = movie.getNotesJson();
			movieReleaseRecord[14] = getCompleteness(movie);
			
			movieReleaseWriter.writeRecord(movieReleaseRecord);
		}
	}
	
	private String getAudio(AbstractMovie movie) {
		JSONArray values = new JSONArray();
		
		for (AudioTrack track : movie.getAudioTracks()) {
			String type = 
					track.isAudioDescriptive() ? "Audio descriptive" :
						track.isCommentary() ? "Commentary" : null;
			
			JSONObject obj = new JSONObject();
			obj.put("language", track.getLanguage().getIso639());
			obj.put("channels", track.getChannels().getDescription());
			obj.put("format", track.getAudio().getShortName());
			obj.put("type", type);
			values.put(obj);
		}
		
		return values.toString();
	}
	
	private String getSubtitle(AbstractMovie movie) {
		JSONArray values = new JSONArray();
		
		for (SubtitleTrack track : movie.getSubtitles()) {
			String type = 
					track.isForced() ? "Forced" :
						track.isCommentary() ? "Commentary" :
							track.isHearingImpaired() ? "Hearing impaired" : "";
			
			JSONObject obj = new JSONObject();
			obj.put("language", track.getLanguage().getIso639());
			obj.put("format", track.getFormat().getShortName());
			obj.put("type", type);
			values.put(obj);
		}
		
		return values.toString();
	}
	
	private String getCompleteness(AbstractMovie movie) {
		String title ="";
		if (movie instanceof AbstractSeries)
		{
			AbstractSeries series = (AbstractSeries)movie;
			Completeness complete = series.getCompleteness();
			String detail = series.getCompletenessDetail();
			
			if(complete==Completeness.other) {}
			else if(complete==Completeness.complete)
				title += "Complete series";
			else if(complete==Completeness.one_season)
				title += "Season "+detail;
			else if(complete==Completeness.seasons)
				title += "Seasons "+detail;
			else if(complete==Completeness.one_episode)
				title += "Episode "+detail;
			else if(complete==Completeness.episodes)
				title += " Episodes "+detail;
			else
				title += detail;
		}
		return title;
	}
	
	private String getRegion(AbstractMovie movie) {
		FormatType format = movie.getFormat();
		JSONArray regions = new JSONArray();
		boolean[] regionBool = movie.getDvdRegion();
		
		if (format == FormatType.dvd) {
			for (int i = 0; i < regionBool.length; i++) {
				if (regionBool[i])
					regions.put(i);
			}
		}
		else if (format == FormatType.bluray || format == FormatType.bluray3d)
		{
			if (regionBool[0])
				regions.put("A");
			if (regionBool[1])
				regions.put("B");
			if (regionBool[2])
				regions.put("C");
		}
	
		return regions.toString();
	}
	
	/**
	 * Returns the number of records that were read during the CSV import operation,
	 * or 0 if it has not yet been run.
	 * @return the number of records that were read
	 */
	public int getNumberOfReadRecords() {
		return read;
	}
	
	/**
	 * Returns the number of records that were skipped during the CSV import operation,
	 * or 0 if it has not yet been run.
	 * @return the number of records that were skipped
	 */
	public int getNumberOfSkippedRecords() {
		return skipped;
	}
}
