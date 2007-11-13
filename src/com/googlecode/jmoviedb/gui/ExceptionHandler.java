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

import com.googlecode.jmoviedb.CONST;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window.IExceptionHandler;

/**
 * A global exception handler.
 * @author Tor
 *
 */
public class ExceptionHandler implements IExceptionHandler {
	
//	/**
//	 * {@inheritDoc}
//	 */
//	public ExceptionHandler(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage, int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
//		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels, defaultIndex);
//	}

	/**
	 * Handle an exception
	 * @param e the exception to be handled
	 */
	public void handleException(Throwable e) {
		if(CONST.DEBUG_MODE) {
			System.out.println("Exceptionhandler caught an exception");
			System.out.println(e.getClass().toString());
			e.printStackTrace();
		}
		
		//TODO make a proper message
		String message = "TODO: Insert a sensible message here";
		
		MessageDialog dialog = new ExceptionDialog(MainWindow.getMainWindow().getShell(), "Error!", null, message, MessageDialog.ERROR, new String[]{"OK", "Send email"}, 0, e);
		int returnCode = dialog.open();
		if(returnCode == 1) {
			//TODO send email option
		}
	}
}
