package com.googlecode.jmoviedb.gui.action;

import java.util.List;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import ca.odell.glazedlists.TextFilterator;

public class MovieTextFilterator implements TextFilterator<AbstractMovie> {

	public void getFilterStrings(List<String> baseList, AbstractMovie element) {
		baseList.add(element.getDisplayTitle());
		baseList.add(element.getYear() + "");
	}

}
