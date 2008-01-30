package com.googlecode.jmoviedb.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.googlecode.jmoviedb.enumerated.Genre;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableModel;
import de.kupzog.ktable.renderers.DefaultCellRenderer;

public class MovieTableCellRenderer extends DefaultCellRenderer implements KTableCellRenderer {
	
	private static Color NOT_SEEN_COLOR = new Color(Display.getCurrent(), 250, 247, 221);
	private static Color DEFAULT_COLOR = new Color(Display.getCurrent(), 221, 247, 250);
	
	private static Font FONT = new Font(Display.getCurrent(), 
			Display.getCurrent().getSystemFont().getFontData()[0].getName(), 10, SWT.BOLD);

	public MovieTableCellRenderer(int style) {
		super(style);
		// TODO Auto-generated constructor stub
	}

	public void drawCell(GC gc, Rectangle rect, int col, int row, Object content,
			boolean focus, boolean header, boolean clicked, KTableModel model) {
		AbstractMovie movie = (AbstractMovie)content;
		Image image = new Image(Display.getCurrent(), movie.getImageData().scaledTo(50, 70));
		String title = movie.getDisplayTitle();
		if(movie.getYear() != 0)
			title += " ("+movie.getYear()+")";
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
		String rating = movie.getRating() + "";
		String runtime = movie.getRunTime()+"m";
		
		int leftColumn = rect.x+72;
		int rightColumn = rect.width-50;
		int rowSpacing = 27;
		int firstRow = rect.y+10;
		int secondRow = firstRow+rowSpacing;
		int thirdRow = secondRow+rowSpacing;
		
		if(!movie.isSeen())
			gc.setBackground(NOT_SEEN_COLOR);
		else
			gc.setBackground(DEFAULT_COLOR);
		gc.setForeground(COLOR_TEXT);
		gc.fillRectangle(rect);
		gc.drawRectangle(rect);
		gc.drawImage(image, rect.x+10, rect.y+10);
		gc.setFont(FONT);
		gc.drawText(title, leftColumn, firstRow);
		gc.drawText(genre, leftColumn, secondRow);
		gc.drawText(actors, leftColumn, thirdRow);
		gc.drawText(rating, rightColumn, secondRow);
		gc.drawText(runtime, rightColumn, thirdRow);
		
		//release resources
		image.dispose();
	}

	public int getOptimalWidth(GC gc, int col, int row, Object content,
			boolean fixed, KTableModel model) {
		// TODO Auto-generated method stub
		return 100;
	}

}
