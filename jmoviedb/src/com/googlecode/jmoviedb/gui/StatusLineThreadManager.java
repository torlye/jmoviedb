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

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.widgets.Display;

/**
 * When a message is to be placed on the status line, an instance of this 
 * class should be called instead of StatusLineManager.setMessage() directly. 
 * The setMessage() method of this class will forward the message to the
 * StatusLineManager specified in the constructor. If the message is not an 
 * empty String, a new thread is spawned that waits for 5 seconds and removes
 * the message.
 * @author Tor
 *
 */
public class StatusLineThreadManager {
	private StatusLineManager statusLine;
	private int interval;
	TThread thread;
	
	/**
	 * Default constructor. Does nothing except setting the local variables.
	 * @param slm the StatusLineManager messages will be sent to
	 */
	public StatusLineThreadManager(StatusLineManager slm) {
		statusLine = slm;
		interval = 5000;
	}
	
	/**
	 * Sets a new message on the status line
	 * @param message the message to be set
	 */
	@SuppressWarnings("deprecation")
	public void setMessage(String message) {
		if(CONST.DEBUG_MODE)
			System.out.println("Status line: " + message);
		statusLine.setMessage(message);
		
		if(message.length() > 0) {
			if(thread!=null) {
				//Ignore deprecation of Thread.stop, as it should be safe in this case.
				thread.stop();
				thread = null;
			}
			thread = new TThread();
			thread.start();
		}
	}
	
	/**
	 * A thread that waits for a few seconds and then resets the status line message
	 * @author Tor
	 *
	 */
	private class TThread extends Thread {
		public void run() {
			try {
				Thread.sleep(interval);
			} catch(InterruptedException e) {
				System.out.println("interrupted");
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					setMessage("");
				}
			});
		}
	}
}