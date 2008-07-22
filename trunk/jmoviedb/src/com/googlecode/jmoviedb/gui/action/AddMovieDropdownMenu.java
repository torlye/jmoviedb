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

import com.googlecode.jmoviedb.gui.MainWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class AddMovieDropdownMenu extends Action implements SelectionListener {
	private AddMovieDropdownMenu actionInstance;
	private AddMovieAction[] actions;
	
	public AddMovieDropdownMenu() {
		super("", IAction.AS_DROP_DOWN_MENU);

		this.actionInstance = this;
		this.actions = MainWindow.getMainWindow().getAddMovieActions();
		
		setText(actions[0].getText());
		setToolTipText(actions[0].getToolTipText());
		setImageDescriptor(actions[0].getImageDescriptor());

		setMenuCreator(new IMenuCreator() {
			public Menu getMenu(Control parent) {
				Menu menu = new Menu(parent);
				for(int i = 0; i<actions.length; i++) {
					MenuItem item = new MenuItem(menu, SWT.NONE);
					item.setData(actions[i].getMovieType());
					item.setText(actions[i].getText());
					item.setImage(actions[i].getImageDescriptor().createImage());
					item.addSelectionListener(actionInstance);
				}
				return menu;
			}
			public Menu getMenu(Menu parent) {
				return null;
			}
			public void dispose() {}
		});

	}

	/**
	 * Runs the default action (add film)
	 */
	public void run() {
		actions[0].run();
	}

	/**
	 * Not used
	 */
	public void widgetDefaultSelected(SelectionEvent e) {}

	/**
	 * Called when an item in the drop-down menu is selected.
	 * Runs the associated AddMovieAction.run() method
	 */
	public void widgetSelected(SelectionEvent e) {
		actions[(Integer)(((MenuItem)(e.getSource())).getData())].run();
	}
}
