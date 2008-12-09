package com.googlecode.jmoviedb.gui.action.search;

import ca.odell.glazedlists.TextFilterator;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import java.util.List;

/**
 *
 * @author mvejen
 */
public class ActorTextFilterator implements TextFilterator<AbstractMovie> {
        //Search using actor as parameter
	public void getFilterStrings(List<String> baseList, AbstractMovie element) {
		baseList.add(element.getActorsAsString());
	}
}
