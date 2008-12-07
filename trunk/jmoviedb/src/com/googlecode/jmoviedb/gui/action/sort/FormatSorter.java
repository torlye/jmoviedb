package com.googlecode.jmoviedb.gui.action.sort;

import java.util.Comparator;

import javax.sound.sampled.spi.FormatConversionProvider;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class FormatSorter implements Comparator<AbstractMovie> {
	private boolean descending;
	private int modifier;
	
	public FormatSorter(boolean descending) {
		this.descending = descending;
		
		//if sort direction is descending, the default sort direction must be reversed
		if(descending)
			modifier = -1;
		else
			modifier = 1;
	}

	public int compare(AbstractMovie movie1, AbstractMovie movie2) {
		
		int formatcompare = movie1.getFormat().getShortName().compareTo(movie2.getFormat().getShortName());
		
		if (formatcompare == 0) {
			return new TitleSorter(descending).compare(movie1, movie2);
		}
		
		return formatcompare*modifier;
	}

}
