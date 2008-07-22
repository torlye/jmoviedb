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
import com.googlecode.jmoviedb.language.GuiLanguage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Saves the currently open database without prompting the user.
 * If the database is a new one (it hasn't been saved before)
 * FileSaveAsAction is called instead.
 * @author Tor
 *
 */
public class FileSaveAction extends Action {
	
	private Action saveAsAction;
	
	/**
	 * Default constructor
	 * @param alternativeAction the action to be called when 
	 * the database has no defined save file
	 */
	public FileSaveAction(Action alternativeAction) {
		setText(GuiLanguage.SAVE);
		setToolTipText(GuiLanguage.SAVE);
		setImageDescriptor(ImageDescriptor.createFromURL(CONST.ICON_SAVE));
		this.saveAsAction = alternativeAction;
	}
	
	/**
	 * Runs the action
	 */
	public void run() {
		//If no save file is defined, run saveAsAction instead.
		if(MainWindow.getMainWindow().getDB().getSaveFile() == null) {
			saveAsAction.run();
		} else {
			try {
				//Save the database to the previously used file.
				MainWindow.getMainWindow().getDB().save(MainWindow.getMainWindow().getDB().getSaveFile());
				MainWindow.getMainWindow().setStatusLineMessage(GuiLanguage.SAVESUCCESS);
			} catch (Exception e) {
				//TODO message dialog (save error, check access permissions)
				MainWindow.getMainWindow().handleException(e);
			}
		}
	}
}
