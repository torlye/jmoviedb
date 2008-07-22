package com.googlecode.jmoviedb.gui.action.search;

import java.util.List;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import ca.odell.glazedlists.TextFilterator;

public class TitleTextFilterator implements TextFilterator<AbstractMovie> {
  
	public void getFilterStrings(List<String> baseList, AbstractMovie element) {
		baseList.add(element.getDisplayTitle());
                //baseList.add(element.getYear() + "");
	}

}
