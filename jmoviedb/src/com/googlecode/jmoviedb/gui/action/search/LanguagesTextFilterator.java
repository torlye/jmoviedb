/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.jmoviedb.gui.action.search;

import ca.odell.glazedlists.TextFilterator;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import java.util.List;

/**
 *
 * @author mvejen
 */
public class LanguagesTextFilterator implements TextFilterator<AbstractMovie> {
        //Search using year as parameter
	public void getFilterStrings(List<String> baseList, AbstractMovie element) {
		baseList.add(element.getCountriesAsString());
                baseList.add(element.getLanguagesAsString());
	}

}
