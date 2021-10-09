package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.csvreader.CsvWriter;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class CSVexportLetterboxd implements IRunnableWithProgress {

	String filepath;
	private int read;
	private int skipped;
	
	CSVexportLetterboxd(String filepath)
	{
		this.filepath = filepath;
	}
	
	@Override
	public void run(IProgressMonitor progress) throws InvocationTargetException, InterruptedException {
		try {
			ArrayList<AbstractMovie> list = MainWindow.getMainWindow().getDB().getDatabase().getMovieList();
			Iterator<AbstractMovie> filteredList = list.stream().filter((m) -> m.getImdbID() != null && m.getImdbID().length() > 0 && m.isSeen()).iterator();
			
			Map<String, AbstractMovie> map = new HashMap<String, AbstractMovie>();
			while(filteredList.hasNext()) {
				AbstractMovie item = filteredList.next();
				map.put(item.getImdbID(), item);
			}
				
			progress.beginTask("Exporting", map.size());
			
			Iterator<AbstractMovie> iterator = map.values().iterator();
			
			String[] filepathParts = filepath.split("(\\.)(?!.*\\.)", 2);
			int part = 0;
			
			while(iterator.hasNext()) {
				String filesegmentPath = part == 0 ? filepath : filepathParts[0] + "_" + part + "." + filepathParts[1];					
				CsvWriter writer = new CsvWriter(filesegmentPath, ',', Charset.forName("UTF-8"));
				int currentFileWritten = 0;
				
				try
				{					
					writer.writeRecord(new String[]{ "imdbID", /*"Title", "Year"*/ });
					
					while (iterator.hasNext() && currentFileWritten < 1900) {
						try
						{
							AbstractMovie movie = iterator.next();
							
							String[] record = new String[] { 
								"tt"+movie.getImdbID(),
								//movie.getTitle(),
								//movie.getYear() > 1800 ? movie.getYear()+"" : "",
							};
							writer.writeRecord(record);
							currentFileWritten++;
							read++;
						}
						catch (Exception e) {
							skipped++;
						}
						finally
						{
							progress.worked(1);
						}
					}
				}
				finally {
					writer.flush();
					writer.close();
					part++;
				}
			}
			
		}
		// Any other exceptions should spawn an InvocationTargetException.
		catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			progress.done();
		}
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
