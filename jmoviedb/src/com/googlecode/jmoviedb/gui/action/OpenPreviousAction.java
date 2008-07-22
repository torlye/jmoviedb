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
import org.eclipse.jface.dialogs.MessageDialog;

public class OpenPreviousAction extends Action {

	private int number;
	private String filePath;
	
	public OpenPreviousAction(int number, String filePath, Action fileSaveAction) {
		this.number = number;
		this.filePath = filePath;
		setText(number + " " + generateDisplayName(filePath));
	}
	
	public void run() {
		//if the selected file is already open, do nothing
		if(MainWindow.getMainWindow().getDB() != null && MainWindow.getMainWindow().getDB().getSaveFile() != null && MainWindow.getMainWindow().getDB().getSaveFile().equals(filePath))
			return;
		
		//ask for confirmation
		if(MainWindow.getMainWindow().saveOnCloseQuestion() == CONST.ANSWER_CANCEL)
			return;
		
		//open the new file
		if(!MainWindow.getMainWindow().openRecent(number))
			MessageDialog.openError(MainWindow.getMainWindow().getShell(), "File open error", "The file could not be opened:\n"+filePath);
	}
	
	private String generateDisplayName(String filePath) {
		if(filePath.length() == 0)
			return filePath;
		String dName = filePath.substring(filePath.lastIndexOf(System.getProperty("file.separator")) + 1);
		if(dName.length() > 20)
			dName = dName.substring(0, 17) + "...";
		return dName;
	}
}
