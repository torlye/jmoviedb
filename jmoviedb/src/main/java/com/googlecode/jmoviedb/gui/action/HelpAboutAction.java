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
import com.googlecode.jmoviedb.gui.AboutDialog;
import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

public class HelpAboutAction extends Action {
	private MainWindow window;

	public HelpAboutAction(MainWindow window) {
		this.window = window;
		setText("About JMoviedb");
		setToolTipText("About JMoviedb");
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		setImageDescriptor(Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_ABOUT), iconSize, iconSize));
	}
	
	public void run() {
		AboutDialog dialog = new AboutDialog(window.getShell(), "About JMoviedb", getImageDescriptor().createImage(), null, MessageDialog.NONE, new String[]{"OK"}, 0);		
		dialog.open();
	}
}
