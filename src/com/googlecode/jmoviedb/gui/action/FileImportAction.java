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

import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

public class FileImportAction extends Action {
	
	public FileImportAction() {
		setText("Import...");
		setToolTipText("Import"); 
	}
	
	public void run() {
		String filePath = openFile();
				
		if(filePath != null) {
			try {
				//Create and run a dialog with a progress monitor
				CSVimport importWorker = new CSVimport(filePath);
				new ProgressMonitorDialog(MainWindow.getMainWindow().getShell()).run(false, false, importWorker);
				
				// Display message box to show how the operation went
				MessageBox messageBox = new MessageBox(MainWindow.getMainWindow().getShell(), SWT.ICON_INFORMATION | SWT.OK);
				messageBox.setMessage(importWorker.getNumberOfReadRecords() + " records read successfully, " + importWorker.getNumberOfSkippedRecords() + " were skipped");
				messageBox.open();
				
				importWorker = null;
				
			} catch (InvocationTargetException e) {
				MainWindow.getMainWindow().handleException(e);
			} catch (InterruptedException e) {
				//The cancel button was pressed.
			}
		}
	}
	
	private String openFile() {
		FileDialog dialog = new FileDialog(MainWindow.getMainWindow().getShell(), SWT.OPEN);
		dialog.setText("Import CSV file");
        String[] fileExtensions = {"*.csv"};
        dialog.setFilterExtensions(fileExtensions);
        String path = dialog.open();
        return path;
	}
}
