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
import com.googlecode.jmoviedb.gui.ExceptionHandler;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.Moviedb;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class FileNewAction extends Action {
	
	public FileNewAction() {
		setText("New");
		setToolTipText("New"); 
		setImageDescriptor(ImageDescriptor.createFromFile(null, CONST.ICON_NEW));
	}
	
	public void run() {
		//ask for confirmation
		if(MainWindow.getMainWindow().saveOnCloseQuestion() == CONST.ANSWER_CANCEL)
			return;
		
		try {
			MainWindow.getMainWindow().setDB(new Moviedb(null));
		} catch (Exception e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}
}
