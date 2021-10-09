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

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.WarningDetailDialog;
import com.googlecode.jmoviedb.net.TmdbUrlAdd;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class TmdbUrlAction extends Action {
	private static String message = "Add TMDb URLs";
	
	public TmdbUrlAction() {
		setText(message);
		setToolTipText(message);
	}
	
	/**
	 * Runs the action
	 */
	public void run() {
		try
		{
			MainWindow.getMainWindow().setStatusLineMessage(message);

			TmdbUrlAdd worker = new TmdbUrlAdd();
			new ProgressMonitorDialog(MainWindow.getMainWindow().getShell()).run(true, true, worker);

			MainWindow.getMainWindow().getDB().massUpdateMovies(worker.moviesToSave);

			String msg1 = "TMDb URLs successfully added to " + worker.completed + " records. " + worker.skipped + " were skipped as they already have a URL.";

			if (worker.failed == 0)
			{
				MessageBox messageBox = new MessageBox(MainWindow.getMainWindow().getShell(), SWT.ICON_INFORMATION | SWT.OK);
				messageBox.setMessage(msg1);
				messageBox.open();
			}
			else {
				String stats = msg1 + " " + worker.failed + " records could not be updated.";
				MessageDialog dialog = new WarningDetailDialog(MainWindow.getMainWindow().getShell(), "Some movies could not be updated", stats, worker.failedMsg);
				dialog.open();
			}
		} catch (InvocationTargetException e) {
			MainWindow.getMainWindow().handleException(e);
		} catch (InterruptedException e) {
			//The cancel button was pressed.
		} catch (SQLException e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}
}
