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
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.language.GuiLanguage;
import com.googlecode.jmoviedb.model.movietype.Film;
import com.googlecode.jmoviedb.model.movietype.MiniSeries;
import com.googlecode.jmoviedb.model.movietype.MovieSerial;
import com.googlecode.jmoviedb.model.movietype.TVmovie;
import com.googlecode.jmoviedb.model.movietype.TVseries;
import com.googlecode.jmoviedb.model.movietype.VideoMovie;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class AddMovieAction extends Action {

	private int movieType;
	
	public AddMovieAction(int movieType) {
		super("");
		this.movieType = movieType;
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		switch(movieType) {
		case CONST.MOVIETYPE_FILM:
			setText(GuiLanguage.ADDFILM + "...");
			setToolTipText(GuiLanguage.ADDFILM);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDFILM), iconSize, iconSize));
			break;
		case CONST.MOVIETYPE_VIDEOMOVIE:
			setText(GuiLanguage.ADDVIDEOMOVIE + "...");
			setToolTipText(GuiLanguage.ADDVIDEOMOVIE);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDVIDEOMOVIE), iconSize, iconSize));
			break;
		case CONST.MOVIETYPE_TVMOVIE:
			setText(GuiLanguage.ADDTVMOVIE + "...");
			setToolTipText(GuiLanguage.ADDTVMOVIE);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDTVMOVIE), iconSize, iconSize));
			break;
		case CONST.MOVIETYPE_TVSERIES:
			setText(GuiLanguage.ADDTVSERIES + "...");
			setToolTipText(GuiLanguage.ADDTVSERIES);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDTVSERIES), iconSize, iconSize));
			break;
		case CONST.MOVIETYPE_MINISERIES:
			setText(GuiLanguage.ADDMINISERIES + "...");
			setToolTipText(GuiLanguage.ADDMINISERIES);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDMINISERIES), iconSize, iconSize));
			break;
		case CONST.MOVIETYPE_MOVIESERIAL:
			setText(GuiLanguage.ADDMOVIESERIAL + "...");
			setToolTipText(GuiLanguage.ADDMOVIESERIAL);
			setImageDescriptor(Utils.resizeActionIcon(ImageDescriptor.createFromURL(CONST.ICON_ADDMOVIESERIAL), iconSize, iconSize));
			break;
		}
	}
	
	public int getMovieType() {
		return movieType;
	}

	public void run() {
		try {
			switch(movieType) {
			case CONST.MOVIETYPE_FILM:
				MainWindow.getMainWindow().openMovieDialog(new Film());
				break;
			case CONST.MOVIETYPE_VIDEOMOVIE:
				MainWindow.getMainWindow().openMovieDialog(new VideoMovie());
				break;
			case CONST.MOVIETYPE_TVMOVIE:
				MainWindow.getMainWindow().openMovieDialog(new TVmovie());
				break;
			case CONST.MOVIETYPE_TVSERIES:
				MainWindow.getMainWindow().openMovieDialog(new TVseries());
				break;
			case CONST.MOVIETYPE_MINISERIES:
				MainWindow.getMainWindow().openMovieDialog(new MiniSeries());
				break;
			case CONST.MOVIETYPE_MOVIESERIAL:
				MainWindow.getMainWindow().openMovieDialog(new MovieSerial());
				break;
			}
			
		} catch (Exception e) {
			MainWindow.getMainWindow().handleException(e);
		}
	}
}
