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

import com.googlecode.jmoviedb.model.Moviedb;

import org.eclipse.jface.viewers.*;

public class MovieContentProvider implements IStructuredContentProvider {
	
	public MovieContentProvider() {
	}

	public Object[] getElements(Object input) {
		System.out.println("GETTING ARRAY ELEMENTS");
		return ((Moviedb)input).getMovieArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		System.out.println("Change input: " + newInput);
		
		if(oldInput != null) {
			System.out.println("CLOSING DATABASE");
			((Moviedb)oldInput).removeListener(MainWindow.getMainWindow());
			((Moviedb)oldInput).closeDatabase();
		}
		if(newInput != null) {
			System.out.println("OPENING NEW DATABASE");
			((Moviedb)newInput).addListener(MainWindow.getMainWindow());
			MainWindow.getMainWindow().setEnabled(true);
		} else {
			MainWindow.getMainWindow().setEnabled(false);
		}
	}
}
