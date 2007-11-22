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

package com.googlecode.jmoviedb.gui;

import java.io.IOException;
import java.sql.SQLException;

import com.googlecode.jmoviedb.CONST;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

/**
 * Monitors the main window list for selection events.
 * Not to be confused with javax.swing.event.ListSelectionListener
 * @author Tor
 *
 */
public class ListSelectionListener implements SelectionListener {

	/**
	 * Sent when a list item is &quot;toggled&quot; or opened. For example
	 * if the item is double-clicked.
	 */
	public void widgetDefaultSelected(SelectionEvent event) {
		try {
			int selection = ((List)(event.widget)).getSelectionIndex();
			if(CONST.DEBUG_MODE) {
				System.out.println("List item " + selection + " opened, movieID " + MainWindow.getMainWindow().getDB().getMovie(selection).getID());
			}
			MainWindow.getMainWindow().openMovieDialog(MainWindow.getMainWindow().getDB().getMovie(selection));
			
		} catch (SQLException e) {
			MainWindow.getMainWindow().handleException(e);
		} catch (IOException e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}

	/**
	 * Sent when a list item is selected. Not currently used for anything.
	 */
	public void widgetSelected(SelectionEvent arg0) {
		//Do nothing
	}
}
