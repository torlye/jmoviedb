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
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

public class FileOpenAction extends Action {
	
	public FileOpenAction() {
		setText("Open...");
		setToolTipText("Open");
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		setImageDescriptor(Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_OPEN), iconSize, iconSize));
	}
	
	private String openFileDialog() {
		FileDialog dialog = new FileDialog(MainWindow.getMainWindow().getShell(), SWT.OPEN);
		dialog.setText("Open file");
        String[] fileExtensions = {"*." + CONST.FILE_EXTENSION};
        dialog.setFilterExtensions(fileExtensions);
        return dialog.open();
	}
	
	public void run() {
		//ask for confirmation
		if(MainWindow.getMainWindow().saveOnCloseQuestion() == CONST.ANSWER_CANCEL)
			return;
		
		try {
			String openFile = openFileDialog();
			if(openFile == null)
				return;
			MainWindow.getMainWindow().openDB(openFile);
		} catch (Exception e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}
}
