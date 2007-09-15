/*
 * This file is part of JMoviedb.
 * 
 * Copyright (C) Tor Arne Lye torarnelye@gmail.com
 * 
 * JMoviedb is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMoviedb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.jmoviedb.gui.action;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

public class SortParameterAction extends Action {
	
	private int sortParameter;
	
	public SortParameterAction(int sortParameter) {
		super("", IAction.AS_RADIO_BUTTON);
		this.sortParameter = sortParameter;
		switch(sortParameter) {
			case CONST.SORT_ASCENDING: setText("Sort Ascending"); break;
			case CONST.SORT_DESCENDING: setText("Sort Descending"); break;
			case CONST.SORT_BY_ID: setText("Sort by ID"); break;
			case CONST.SORT_BY_TITLE: setText("Sort by title"); break;
			case CONST.SORT_BY_YEAR: setText("Sort by year"); break;
			case CONST.SORT_BY_TYPE: setText("Sort by type"); break;
			case CONST.SORT_BY_RATING: setText("Sort by rating"); break;
		}
	}
	
	public void run() {
		Settings s = Settings.getSettings();
		if(sortParameter < 0 && s.getSortDirection() != sortParameter) {
			s.setSortDirection(sortParameter);
			MainWindow.getMainWindow().getDB().sort();
		}
		
		else if(sortParameter >= 0 && s.getSortBy() != sortParameter) {
			s.setSortBy(sortParameter);
			MainWindow.getMainWindow().getDB().sort();
		}
	}
}
