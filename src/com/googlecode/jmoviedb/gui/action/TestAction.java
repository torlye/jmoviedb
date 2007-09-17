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
import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class TestAction extends Action {
	private String message;
	private boolean exception;
	
	public TestAction(String message, boolean exception) {
		setText(message);
		setToolTipText(message);
		this.message = message;
		this.exception = exception;
		if(exception)
			setImageDescriptor(ImageDescriptor.createFromFile(null, "resources/icon-silk/exclamation.png"));
		else
			setImageDescriptor(ImageDescriptor.createFromFile(null, CONST.ICON_PREFERENCES));
	}
	
	/**
	 * Runs the action
	 */
	public void run() {
		if(exception)
			MainWindow.getMainWindow().handleException(new Exception("Test exception!"));
		else
			MainWindow.getMainWindow().setStatusLineMessage(message);
	}
}
