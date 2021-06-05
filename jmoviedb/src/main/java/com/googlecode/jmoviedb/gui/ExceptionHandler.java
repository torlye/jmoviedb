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

import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherErrorHandler;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window.IExceptionHandler;

/**
 * A global exception handler. Displays a MessageDialog containing information and a stack trace.
 *
 */
public class ExceptionHandler implements IExceptionHandler, BrowserLauncherErrorHandler {

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
		String message = "Oops! JMoviedb has just crashed. You may try to keep using it, but the " +
				"application is likely to crash again, cause data corruption or exhibit strange " +
				"behaviour. It is highly recommended to restart JMoviedb at this point. Don't " +
				"forget to backup your data.\n\nYou are encouraged to file a bug report at "+
				CONST.WEBSITE+" If you do, please include the following information:";
		
		MessageDialog dialog = new ExceptionDialog(MainWindow.getMainWindow().getShell(), "Crash!", 
				message, e);
		dialog.open();
	}

	/**
	 * Handle an exception. Required for BrowserLauncherErrorHandler compatibility.
	 * @param ex the exception to be handled
	 */
	public void handleException(Exception ex) {
		handleException((Throwable)ex);
	}
}

