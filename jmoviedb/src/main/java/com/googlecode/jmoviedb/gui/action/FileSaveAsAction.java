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
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.language.GuiLanguage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

/**
 * Prompts the user for a file name and then saves the database
 * in the specified file.
 * @author Tor
 *
 */
public class FileSaveAsAction extends Action {
	
	public FileSaveAsAction() {
		setText(GuiLanguage.SAVEAS + "...");
		setToolTipText(GuiLanguage.SAVEAS);
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		setImageDescriptor(Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_SAVEAS), iconSize, iconSize));
	}
	
	private String saveFileDialog() {
		FileDialog dialog = new FileDialog(MainWindow.getMainWindow().getShell(), SWT.SAVE);
		dialog.setText("Save file");
        String[] fileExtensions = {"*." + CONST.FILE_EXTENSION};
        dialog.setFilterExtensions(fileExtensions);
        return dialog.open();
	}
	
	public void run() {
		try {
			String saveFile = saveFileDialog();
			if(saveFile == null)
				return;
			if(!saveFile.endsWith("." + CONST.FILE_EXTENSION))
				saveFile = saveFile + "." + CONST.FILE_EXTENSION;
			
			MainWindow.getMainWindow().getDB().setSaveFile(saveFile);
			MainWindow.getMainWindow().getDB().save(saveFile);
			MainWindow.getMainWindow().setStatusLineMessage(GuiLanguage.SAVESUCCESS);
			MainWindow.getMainWindow().updateShellText();
			Settings.getSettings().updateRecentFiles(saveFile);
			//TODO refresh the main window shell text
		} catch (Exception e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}
}
