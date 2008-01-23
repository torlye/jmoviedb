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

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Monitors the main window list for selection events.
 * Not to be confused with javax.swing.event.ListSelectionListener
 * @author Tor
 *
 */
public class ListSelectionListener /*implements SelectionListener, KeyListener */{

//	/**
//	 * Sent when a list item is &quot;toggled&quot; or opened. For example
//	 * if the item is double-clicked.
//	 */
//	public void widgetDefaultSelected(SelectionEvent event) {
//		try {
//			MainWindow.getMainWindow().openMovieDialog(MainWindow.getMainWindow().getSelectedItem());
//			
//			
//		} catch (Exception e) {
//			MainWindow.getMainWindow().handleException(e);
//		}
//	}
//
//	/**
//	 * Sent when a list item is selected. Not currently used for anything.
//	 */
//	public void widgetSelected(SelectionEvent event) {
//		//Do nothing
//	}
//
//	//TODO fix cases where Enter keypresses lead to widgetDefaultSelected calls.
//	public void keyPressed(KeyEvent e) {
//		if(e.character=='\r')
//			try {
//				MainWindow.getMainWindow().openMovieDialog(MainWindow.getMainWindow().getSelectedItem());
//
//			} catch (Exception ex) {
//				MainWindow.getMainWindow().handleException(ex);
//			}
//	}
//
//	public void keyReleased(KeyEvent e) {
//		//Do nothing
//	}
}
