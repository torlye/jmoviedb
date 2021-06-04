package com.googlecode.jmoviedb.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Completeness;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.AbstractSeries;
import com.googlecode.jmoviedb.model.movietype.MiniSeries;
import com.googlecode.jmoviedb.model.movietype.TVmovie;
import com.googlecode.jmoviedb.model.movietype.TVseries;
import com.googlecode.jmoviedb.model.movietype.VideoMovie;
import com.googlecode.jmoviedb.model.movietype.WebSeries;

import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableModel;
import de.kupzog.ktable.renderers.DefaultCellRenderer;

public class MovieTableCellRenderer extends DefaultCellRenderer implements KTableCellRenderer {
	
	private final static Color SELECTED_COLOR = new Color(Display.getCurrent(), 255, 159, 117); //ff9f75
	private final static Color NOT_SEEN_COLOR = new Color(Display.getCurrent(), 250, 247, 221); //faf7dd
	private final static Color DEFAULT_COLOR = new Color(Display.getCurrent(), 221, 247, 250); //ddf7fa
	private final static Color DVD_COLOR = new Color(Display.getCurrent(), 220, 128, 128); //dc8080
	private final static Color VCD_COLOR = new Color(Display.getCurrent(), 240, 221, 250); //f0ddfa
	private final static Color SVCD_COLOR = new Color(Display.getCurrent(), 250, 221, 240); //faddf0
	private final static Color BLURAY_COLOR = new Color(Display.getCurrent(), 0, 149, 213); //0095d5, the colour in the Blu-Ray logo
	private final static Color HDDVD_COLOR = new Color(Display.getCurrent(), 160, 37, 45); //approximate colour of the HD-DVD cover - too dark?
	private final static Color UHD_COLOR = new Color(Display.getCurrent(), 0, 0, 0);
	private final static Color LIGHT_TEXT_COLOR = new Color(Display.getCurrent(), 255, 255, 255);
	private final static int IMAGE_WIDTH = 50;
	private final static int IMAGE_HEIGHT = 70;
	
	static Font FONT = new Font(Display.getCurrent(), 
			Display.getCurrent().getSystemFont().getFontData()[0].getName(), 10, SWT.BOLD);

	public MovieTableCellRenderer(int style) {
		super(style);
		// TODO Auto-generated constructor stub
	}

	public void drawCell(GC gc, Rectangle rect, int col, int row, Object content,
			boolean focus, boolean header, boolean clicked, KTableModel model) {
		AbstractMovie movie = (AbstractMovie)content;
		String title = movie.getDisplayTitle();
		
		if(movie.getYear() != 0) {
			String yearString = ""+movie.getYear();
			if (movie.hasYear2()) {
				String separator = "/";
				if (movie instanceof TVseries || movie instanceof WebSeries)
					separator = "\u2013";
				yearString += separator+movie.getYear2();
			}
			title += " ("+yearString+")";
		}
		
		if(movie.getCustomVersion().length() > 0)
			title += " [" + movie.getCustomVersion() + "]";
		
		if (movie instanceof VideoMovie)
			title += " (V)";
		
		else if(movie instanceof TVmovie)
			title += " (TV)";
		
		else if(movie instanceof AbstractSeries) {
			AbstractSeries series = (AbstractSeries)movie;
			if(series instanceof MiniSeries)
				title += " (mini)";
			else if(series instanceof TVseries)
				title += " (TV-series)";
			else if(series instanceof WebSeries)
				title += " (Web)";
			
			Completeness complete = series.getCompleteness();
			String detail = series.getCompletenessDetail();
			if(complete==Completeness.other) {}
			else if(complete==Completeness.complete)
				title += " Complete series";
			else if(complete==Completeness.one_season)
				title += " Season "+detail;
			else if(complete==Completeness.seasons)
				title += " Seasons "+detail;
			else if(complete==Completeness.one_episode)
				title += " Episode "+detail;
			else if(complete==Completeness.episodes)
				title += " Episodes "+detail;
			else
				title += " "+detail;
		}
		if(movie.getResolution().isHD())
			title += " "+movie.getResolution().getName();
		
		String genre = "";
		for(Genre g : movie.getGenres()) {
			if(genre.length()>0)
				genre += " / ";
			genre += g.getIMDBname();
		}
		
		String actors = "";
		if(movie.getActors().size()>0)
			actors += movie.getActors().get(0).getPerson().getName();
		if(movie.getActors().size()>1)
			actors += ", "+movie.getActors().get(1).getPerson().getName();
		if(movie.getActors().size()>2)
			actors += ", "+movie.getActors().get(2).getPerson().getName();
		
		String rating = "";
		if(movie.getRating()!=0.0)
			rating += movie.getRating();
		
		String runtime = "";
		if(movie.getRunTime()!=0)
			runtime = movie.getRuntimeAsHourMinuteString();
		
		String format = "";
		if(movie.getFormat() != FormatType.other)
			format = movie.getFormat().getShortName();
		
		gc.setFont(FONT);
		FontMetrics fm = gc.getFontMetrics();
		int fontHeight = fm.getHeight();
		int rowSpacing = fontHeight + 10;
		int firstRow = rect.y+10;
		int secondRow = firstRow+rowSpacing;
		int thirdRow = secondRow+rowSpacing;
		int imgHeight = -10 + (fontHeight+10) * 3;
		int imgWidth = (int) Math.round(imgHeight * 0.71);
		int leftColumn = rect.x+20+imgWidth;
		int rightColumn = rect.width-10;
		
		Image image = Utils.resizePreserveAspect(movie.getImageData(), imgWidth, imgHeight);
		
		//Configure table cell background colour
		gc.setForeground(COLOR_TEXT);
		
		if(focus)
			gc.setBackground(SELECTED_COLOR);
		else if(!movie.isSeen())
			gc.setBackground(NOT_SEEN_COLOR);
		else if(movie.getFormat() == FormatType.dvd)
			gc.setBackground(DVD_COLOR);
		else if(movie.getFormat() == FormatType.bluray || movie.getFormat() == FormatType.bluray3d)
			gc.setBackground(BLURAY_COLOR);
		else if(movie.getFormat() == FormatType.vcd)
			gc.setBackground(VCD_COLOR);
		else if(movie.getFormat() == FormatType.svcd)
			gc.setBackground(SVCD_COLOR);
		else if(movie.getFormat() == FormatType.hddvd)
			gc.setBackground(HDDVD_COLOR);
		else if (movie.getFormat() == FormatType.uhdbluray)
			gc.setBackground(UHD_COLOR);
		else
			gc.setBackground(DEFAULT_COLOR);
		
		gc.fillRectangle(rect);
		gc.drawRectangle(rect);
		gc.drawImage(image, 
				rect.x+10+(imgWidth-image.getBounds().width)/2, 
				rect.y+10+(imgHeight-image.getBounds().height)/2);
		
		if(focus || !movie.isSeen()) {
		} else if (movie.getFormat() == FormatType.bluray || movie.getFormat() == FormatType.bluray3d 
				|| movie.getFormat() == FormatType.hddvd || movie.getFormat() == FormatType.uhdbluray) {
			gc.setForeground(LIGHT_TEXT_COLOR);
		}
		
		gc.drawString(title, leftColumn, firstRow);
		gc.drawString(genre, leftColumn, secondRow);
		gc.drawString(actors, leftColumn, thirdRow);
		gc.drawString(format, rightColumn-gc.textExtent(format).x, firstRow);
		gc.drawString(rating, rightColumn-gc.textExtent(rating).x, secondRow);
		gc.drawString(runtime, rightColumn-gc.textExtent(runtime).x, thirdRow);
		
		//release resources
		image.dispose();
	}

	public int getOptimalWidth(GC gc, int col, int row, Object content,
			boolean fixed, KTableModel model) {
		// TODO Auto-generated method stub
		return 100;
	}

}
