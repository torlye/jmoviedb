package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.csvreader.CsvWriter;
import com.googlecode.jmoviedb.enumerated.Completeness;
import com.googlecode.jmoviedb.enumerated.Country;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.Person;
import com.googlecode.jmoviedb.model.SubtitleTrack;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.AbstractSeries;

import java.util.Base64;

public class CSVexport implements IRunnableWithProgress {

	String filepath;
	private int read;
	private int skipped;
	
	CSVexport(String filepath)
	{
		this.filepath = filepath;
	}
	
	@Override
	public void run(IProgressMonitor progress) throws InvocationTargetException, InterruptedException {
		try {
			CsvWriter writer = new CsvWriter(filepath, ',', Charset.forName("UTF-8"));
			
			ArrayList<AbstractMovie> list  = MainWindow.getMainWindow().getDB().getDatabase().getMovieList();
				
			progress.beginTask("Exporting", list.size());
			
			writer.writeComment("MOVIEID, TYPE, IMDBID, TITLE, CUSTOMTITLE, MOVIEYEAR, RATING, PLOTOUTLINE, TAGLINE, COLOR, RUNTIME, VERSION, PIRATED, SEEN, "
					+ "LOCATION, FORMAT, DISC,VIDEO,DVDREGION, TVSYSTEM, SCENERELEASENAME,VIDEORESOLUTION,VIDEOASPECT,CONTAINER,COMPLETENESS,YEAR2,"
					+ "COUNTRY, LANGUAGE, GENRE, DIRECTORS, WRITERS, ACTORS,"
					+ "AUDIO, SUBTITLES, NOTES, URL1, URL2, COVER");
			
			for (int i = 0; i < list.size(); i++)
			{
				try
				{
					AbstractMovie movie = list.get(i);
					
					String[] record = new String[] { 
						movie.getID()+"", 
						MovieType.abstractMovieToEnum(movie).getName(),
						movie.getImdbUrl(),
						movie.getTitle(),
						movie.getCustomTitle(),
						movie.getYear() > 1800 ? movie.getYear()+"" : "",
						movie.getRating() > 0 ? movie.getRating()+"" : "",
						movie.getPlotOutline(),
						movie.getTagline(),
						movie.getColorInt() == 0 ? "B&W" : "",
						movie.getRunTime() > 0 ? movie.getRunTime()+"" : "",
						movie.getCustomVersion(),
						!movie.isLegal()+"",
						movie.isSeen()+"",
						movie.getLocation(),
						movie.getFormat().getShortName(),
						movie.getDisc().getName(),
						movie.getVideo().getShortName(),
						getRegion(movie),
						movie.getTvSystem().getShortName(),
						movie.getSceneReleaseName(),
						movie.getResolution().getName(),
						movie.getAspectRatio().getShortName(),
						movie.getContainer().getShortName(),
						getCompleteness(movie),
						movie.getYear2() > 1800 ? movie.getYear2()+"" : "",
						getCountries(movie),
						getLanguages(movie),
						getGenres(movie),
						getDirectors(movie),
						getWriters(movie),
						getActors(movie),
						getAudio(movie),
						getSubtitle(movie),
						movie.getNotes(),
						movie.getUrl1StringOrNull(),
						movie.getUrl2StringOrNull(),
						//getCover(movie)
					};
					
					writer.writeRecord(record);
					read++;
				}
				catch (Exception e) {
					skipped++;
					writer.writeComment(e.getMessage()+"\n"+e.getStackTrace());
				}
				finally
				{
					progress.worked(1);
				}
			}
			writer.flush();
			writer.close();
		}
		// Any other exceptions should spawn an InvocationTargetException.
		catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			progress.done();
		}
	}
	
	public String getAudio(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (AudioTrack track : movie.getAudioTracks()) {
			String type = 
					track.isAudioDescriptive() ? "Audio descriptive" :
						track.isCommentary() ? "Commentary" : "";
			
			values.add(
				"{"
					+ "\"language\":\""+ jsonEncode(track.getLanguage().getImdbID()) + "\","
					+ "\"channels\":\""+ jsonEncode(track.getChannels().getDescription()) + "\","
				    + "\"format\":\""+ jsonEncode(track.getAudio().getShortName()) + "\","
				    + "\"type\":\""+ jsonEncode(type) + "\""
			+ "}");
		}
		
		return "[" + String.join(",", values) + "]";
	}
	
	public String getSubtitle(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (SubtitleTrack track : movie.getSubtitles()) {
			String type = 
					track.isForced() ? "Forced" :
						track.isCommentary() ? "Commentary" :
							track.isHearingImpaired() ? "Hearing impaired" : "";
			
			values.add(
				"{"
					+ "\"language\":\""+ jsonEncode(track.getLanguage().getImdbID()) + "\","
					+ "\"format\":\""+ jsonEncode(track.getFormat().getShortName()) + "\","
				    + "\"type\":\""+ jsonEncode(type) + "\""
			+ "}");
		}
		
		return "[" + String.join(",", values) + "]";
	}
	
	public String getDirectors(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (Person val : movie.getDirectors()) {
			values.add("{\"name\":\""+ jsonEncode(val.getName()) + "\",\"id\":\""+ jsonEncode(val.getID()) + "\"}");
		}
		
		return "[" + String.join(",", values) + "]";
	}
	
	public String getWriters(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (Person val : movie.getWriters()) {
			values.add("{\"name\":\""+ jsonEncode(val.getName()) + "\",\"id\":\""+ jsonEncode(val.getID()) + "\"}");
		}
		
		return "[" + String.join(",", values) + "]";
	}
	
	public String getActors(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		ArrayList<ActorInfo> actors = movie.getActors();
		actors.sort(
			new Comparator<ActorInfo>() {
				@Override
				public int compare(ActorInfo o1, ActorInfo o2) {
					return o2.getId() - o1.getId();
				}
			}
		);
		
		for (ActorInfo val : actors) 
		{
			values.add("{\"name\":\""+ jsonEncode(val.getPerson().getName()) + "\",\"id\":\"" + jsonEncode(val.getPerson().getID()) + "\",\"character\":\"" + jsonEncode(val.getCharacter())+ "\"}");
		}
		
		return "[" + String.join(",", values) + "]";
	}

	public String getCountries(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (Country val : movie.getCountries()) {
			values.add(val.getImdbID());
		}
		
		return String.join(",", values);
	}
	
	public String getLanguages(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (Language val : movie.getLanguages()) {
			values.add(val.getImdbID());
		}
		
		return String.join(",", values);
	}
	
	public String getGenres(AbstractMovie movie) {
		ArrayList<String> values = new ArrayList<String>();
		
		for (Genre val : movie.getGenres()) {
			values.add(val.getIMDBname());
		}
		
		return String.join(",", values);
	}
	
	public String getCompleteness(AbstractMovie movie) {
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
	
	public String getRegion(AbstractMovie movie) {
		FormatType format = movie.getFormat();
		ArrayList<String> regions = new ArrayList<String>();
		boolean[] regionBool = movie.getDvdRegion();
		
		if (format == FormatType.dvd) {
			for (int i = 0; i < regionBool.length; i++) {
				if (regionBool[i])
					regions.add(i+"");
			}
		}
		else if (format == FormatType.bluray || format == FormatType.bluray3d)
		{
			if (regionBool[0])
				regions.add("A");
			if (regionBool[1])
				regions.add("B");
			if (regionBool[2])
				regions.add("C");
		}
	
		return String.join(",", regions);
	}
	
	public String getCover(AbstractMovie movie) {
		try
		{
			byte[] data = movie.getImageBytes();
			
			if (data == null || data.length == 0)
				return "";
			
			return new String(Base64.getEncoder().encode(data));
		}
		catch (Exception e)
		{
		}
		return "";
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
	
	private static String jsonEncode(String input)
	{
		return input.replace("\\", "\\\\").replace("\"", "\\\"").replaceAll("\n", " ");
	}
}
