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

package com.googlecode.jmoviedb.net;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class TmdbWorker {

	/**
	 * Updates the given movie with new data from IMDb. Presents search results (if any) in a nice dialog. 
	 * @param shell - parent shell 
	 * @param movie - the movie to update
	 * @return the updated movie
	 * @throws IOException
	 */
	public AbstractMovie update(AbstractMovie movie, Shell parentShell) {
		if(!movie.isImdbUrlValid() && !movie.isTmdbUrlValid()) {
			/*
			 * We end up here if the movie doesn't have a URL yet, or if it is malformed.
			 */
			MessageDialog.openInformation(parentShell, "Missing information", "You must enter a TMDB or IMDb URL before you can download information.");
			return movie;
		}

		//At this point we have a valid IMDb URL
		TmdbDownloader downloader = new TmdbDownloader(movie);

		try {
			new ProgressMonitorDialog(parentShell).run(true, false, downloader);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof TmdbImportException) {
				MessageBox messageBox = new MessageBox(MainWindow.getMainWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
				messageBox.setText("TMDB import failed");
				messageBox.setMessage(e.getTargetException().getMessage());
				messageBox.open();
			}
			else {
				MainWindow.getMainWindow().handleException(e);
			}
		} catch (InterruptedException e) {
			//Not used
		}
		
		return downloader.getMovie();
	}
}

