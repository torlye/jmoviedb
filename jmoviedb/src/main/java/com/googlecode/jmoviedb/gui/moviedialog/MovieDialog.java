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

package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.Film;
import com.googlecode.jmoviedb.net.TmdbWorker;

/**
 * A MovieDialog is a tabbed dialog box that is used to display or edit information about an AbstractMovie subclass.
 * When the dialog is closed, it will return one of the following return codes: IDialogConstants.OK_ID, (if the user
 * pressed the OK button) IDialogConstants.CANCEL_ID, (if the user pressed the cancel button) or 
 * IDialogConstants.ABORT_ID (if the user pressed the delete button)
 * @author Tor Arne Lye
 *
 */
public class MovieDialog extends Dialog {
	private AbstractMovie movie;
	
	public static final int MARGIN_WIDTH = 7;
	public static final int MARGIN_HEIGHT = 7;
	public static final int VERTICAL_SPACING = 5;
	public static final int HORIZONTAL_SPACING = 6;
	
	private MainTab mainTab;
	private IMovieDialogTab taglineTab;
	private IMovieDialogTab actorsTab;
	private FormatTab formatTab;
	private IMovieDialogTab audioSubTab;
	private IMovieDialogTab releaseTab;
	
	public MovieDialog(AbstractMovie movie) {
		super(MainWindow.getMainWindow());
		this.setShellStyle(SWT.CLOSE|SWT.RESIZE);
		
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		mainTab = new MainTab(this, iconSize);
		taglineTab = new TaglinePlotTab(iconSize);
		actorsTab = new ActorsTab(iconSize);
		formatTab = new FormatTab(iconSize);
		audioSubTab = new AudioSubTab(iconSize, formatTab);
		releaseTab = new ReleaseTab();
		
		this.movie = movie;
		
		//TODO Sets keyboard focus to the save button
//		saveButton.setFocus();
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
//	
//	protected Point getInitialSize() {
//		return new Point(1000, 900);
//	}
	
    @Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		//shell.setSize(630, 490);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite c = (Composite) super.createDialogArea(parent);
		
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 8;
		gridLayout.marginHeight = 8;
		gridLayout.verticalSpacing = 6;
		gridLayout.horizontalSpacing = 6;
		c.setLayout(gridLayout);
		
		GridData tabFolderGD = new GridData();
		tabFolderGD.horizontalSpan = 3;
		tabFolderGD.horizontalAlignment = SWT.FILL/*|SWT.RESIZE*/;
		tabFolderGD.verticalAlignment = SWT.FILL/*|SWT.RESIZE*/;
		tabFolderGD.grabExcessVerticalSpace = true;
		tabFolderGD.grabExcessHorizontalSpace = true;
				
		final CTabFolder tabFolder = new CTabFolder(c, SWT.BORDER/*|SWT.RESIZE*/);
		tabFolder.setSimple(false);
		tabFolder.setTabHeight(Math.round(24*MainWindow.DPI_SCALE));
		tabFolder.marginHeight = 0;
		tabFolder.marginWidth = 0;
		tabFolder.setBorderVisible(true);
		tabFolder.setSingle(false);
		tabFolder.setLayoutData(tabFolderGD);
		
		//Set tab colors to mimic Eclipse behavior
		tabFolder.setSelectionForeground(this.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND));
		tabFolder.setSelectionBackground(
				new Color[]{
						this.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND),
						this.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)
				},  new int[] {100}, true);
		
		mainTab.createTabArea(tabFolder);
		taglineTab.createTabArea(tabFolder);
		actorsTab.createTabArea(tabFolder);
		releaseTab.createTabArea(tabFolder);
		formatTab.createTabArea(tabFolder);
		audioSubTab.createTabArea(tabFolder);

		tabFolder.setSelection(0); //select the first tab
		
		configureListeners();

		return c;
	}
	
	private void configureListeners() {
		mainTab.configureListeners();
		taglineTab.configureListeners();
		actorsTab.configureListeners();
		releaseTab.configureListeners();
		formatTab.configureListeners();
		audioSubTab.configureListeners();
	}
	
	/**
	 * Sets the model for this MovieDialog
	 * @param m the movie to be used as a model
	 */
	private void setModel(AbstractMovie m) {
		this.getShell().setText("Movie info - " + m.getDisplayTitle() + " (" + m.getYear() + ")");
		mainTab.setModel(m);
		taglineTab.setModel(m);
		actorsTab.setModel(m);
		releaseTab.setModel(m);
		formatTab.setModel(m);
		audioSubTab.setModel(m);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DETAILS_ID, "TMDB update", false);
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
		createButton(parent, IDialogConstants.ABORT_ID, "Remove movie", false);
	}
	
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		if(buttonId == IDialogConstants.CANCEL_ID)
			close();
		
		else if(buttonId == IDialogConstants.OK_ID) {
			if(mainTab.getTitleString().length() == 0)
				MessageDialog.openInformation(this.getShell(), "No title", "You can't save a movie without a title!");
			else {
				save();
				close();
			}
		}
		
		else if(buttonId == IDialogConstants.DETAILS_ID) { //IMDb update
			try {
				save();
				TmdbWorker w = new TmdbWorker();
				movie = w.update(movie, getShell());
				setModel(movie);
			} catch (Exception e) {
				MainWindow.getMainWindow().handleException(e);
			}
		}
		
		else if(buttonId == IDialogConstants.ABORT_ID) { //Delete
			if (MessageDialog.openQuestion(this.getShell(), "Delete movie", "Are you sure you want to delete this movie?"))
				close();
		}
	}
	
	/**
	 * Closes the window. Extended to dispose of certain additional resources.
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close() {
		mainTab.dispose();
		taglineTab.dispose();
		actorsTab.dispose();
		releaseTab.dispose();
		formatTab.dispose();
		audioSubTab.dispose();
		return super.close();
	}
	
	private void save() {
		System.out.println("Movie type: " + MovieType.objectToEnum(movie));
		System.out.println("Selection: " + MovieType.stringToEnum(mainTab.getTypeComboItem()));
		if(!MovieType.objectToEnum(movie).equals(MovieType.stringToEnum(mainTab.getTypeComboItem()))) {
			AbstractMovie newMovie = MovieType.intToAbstractMovie(mainTab.getTypeSelectionIndex());
			newMovie = movie.copyTo(newMovie);
			movie = newMovie;
		}
		mainTab.save(movie);
	    taglineTab.save(movie);
		actorsTab.save(movie);
		releaseTab.save(movie);
		formatTab.save(movie);
		audioSubTab.save(movie);
	}
	
	public AbstractMovie getModel() {
		return movie;
	}
	
	@Override
	public int open() {
		create();
		
		if(movie != null)
			setModel(movie);
		else
			setModel(new Film());
		
		return super.open();
	}
}

