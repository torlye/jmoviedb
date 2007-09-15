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

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.gui.action.*;
import com.googlecode.jmoviedb.model.Moviedb;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class MainWindow extends ApplicationWindow implements IPropertyChangeListener {
	
	//setup
	private static final int coolBarStyle = SWT.FLAT;
	private static final int toolBarStyle = SWT.FLAT;
	
	private static MainWindow instance;
	private static Settings settings;
	
	//actions
	private FileNewAction newAction;
	private FileOpenAction fileOpenAction;
	private FileSaveAction fileSaveAction;
	private FileSaveAsAction fileSaveAsAction;
	private PrintAction printAction;
	private OpenPreviousAction openPreviousAction1;
	private OpenPreviousAction openPreviousAction2;
	private OpenPreviousAction openPreviousAction3;
	private OpenPreviousAction openPreviousAction4;
	private FileImportAction fileImportAction;
	private FileExitAction fileExitAction;
	
	private SortParameterAction sortByIdAction;
	private SortParameterAction sortByTitleAction;
	private SortParameterAction sortByYearAction;
	private SortParameterAction sortByTypeAction;
	private SortParameterAction sortByRatingAction;
	private SortParameterAction sortAscendingAction;
	private SortParameterAction sortDescendingAction;
	
	private AddMovieAction addFilmAction;
	private AddMovieAction addMovieSerialAction;
	private AddMovieAction addTvSeriesAction;
	private AddMovieAction addTvMovieAction;
	private AddMovieAction addMiniSeriesAction;
	private AddMovieAction addVideomovieAction;
	
	private HelpHelpAction helpHelpAction;
	private HelpAboutAction helpAboutAction;
	
	private MenuManager menuManager;
	private MenuManager fileMenu;
	
	private List list;
	private ListViewer viewer;
	
	private StatusLineThreadManager statusLine;
	
	private ExceptionHandler exceptionHandler;

	public MainWindow() {
		super(null);
		
		instance = this;
		Settings.getSettings().addListener(this);
		exceptionHandler = new ExceptionHandler();
		setExceptionHandler(exceptionHandler);
		
		setDefaultImages(new Image[]{
				ImageDescriptor.createFromFile(null, CONST.ICON_MAIN_16).createImage(), 
				ImageDescriptor.createFromFile(null, CONST.ICON_MAIN_32).createImage(), 
				ImageDescriptor.createFromFile(null, CONST.ICON_MAIN_64).createImage(), 
				ImageDescriptor.createFromFile(null, CONST.ICON_MAIN_128).createImage(), 
				ImageDescriptor.createFromFile(null, CONST.ICON_MAIN_256).createImage()});
		
		fileSaveAsAction = new FileSaveAsAction();
		fileSaveAction = new FileSaveAction(fileSaveAsAction);
		newAction = new FileNewAction(fileSaveAction);
		fileOpenAction = new FileOpenAction(fileSaveAction);
		printAction = new PrintAction();
		fileImportAction = new FileImportAction();
		createRecentFilesMenu();
		fileExitAction = new FileExitAction();
		sortAscendingAction = new SortParameterAction(CONST.SORT_ASCENDING);
		sortDescendingAction = new SortParameterAction(CONST.SORT_DESCENDING);
		sortByIdAction = new SortParameterAction(CONST.SORT_BY_ID);
		sortByTitleAction = new SortParameterAction(CONST.SORT_BY_TITLE);
		sortByYearAction = new SortParameterAction(CONST.SORT_BY_YEAR);
		sortByTypeAction = new SortParameterAction(CONST.SORT_BY_TYPE);
		sortByTypeAction.setEnabled(false);
		sortByRatingAction = new SortParameterAction(CONST.SORT_BY_RATING);
		helpHelpAction = new HelpHelpAction();
		helpAboutAction = new HelpAboutAction(this);
		helpHelpAction.setEnabled(false);
		
		addFilmAction = new AddMovieAction(CONST.MOVIETYPE_FILM);
		addMovieSerialAction = new AddMovieAction(CONST.MOVIETYPE_MOVIESERIAL);
		addVideomovieAction = new AddMovieAction(CONST.MOVIETYPE_VIDEOMOVIE);
		addTvMovieAction = new AddMovieAction(CONST.MOVIETYPE_TVMOVIE);
		addTvSeriesAction = new AddMovieAction(CONST.MOVIETYPE_TVSERIES);
		addMiniSeriesAction = new AddMovieAction(CONST.MOVIETYPE_MINISERIES);
		
		addMenuBar();
		addCoolBar(coolBarStyle);
		addStatusLine();
		
		statusLine = new StatusLineThreadManager(getStatusLineManager());
		
		setSearchParameters();
	}

	protected MenuManager createMenuManager(){
		menuManager = new MenuManager();
		
		fileMenu = new MenuManager("&File");
		fileMenu.add(newAction);
		fileMenu.add(fileOpenAction);
		fileMenu.add(new Separator());
		fileMenu.add(fileSaveAction);
		fileMenu.add(fileSaveAsAction);
		fileMenu.add(new Separator());
		fileMenu.add(printAction);
		fileMenu.add(new Separator());
		fileMenu.add(fileImportAction);
		fileMenu.add(new Separator());
		fileMenu.add(openPreviousAction1);
		fileMenu.add(openPreviousAction2);
		fileMenu.add(openPreviousAction3);
		fileMenu.add(openPreviousAction4);
		fileMenu.add(new Separator());
		fileMenu.add(fileExitAction);
		menuManager.add(fileMenu);
		
		MenuManager editMenu = new MenuManager("&Edit");
		editMenu.add(new TestAction("Test!"));
		menuManager.add(editMenu);
		
		MenuManager viewMenu = new MenuManager("&View");
		viewMenu.add(new TestAction("Test!"));
		menuManager.add(viewMenu);
		
		MenuManager sortMenu = new MenuManager("&Sort");
		sortMenu.add(sortByIdAction);
		sortMenu.add(sortByTitleAction);
		sortMenu.add(sortByYearAction);
		sortMenu.add(sortByTypeAction);
		sortMenu.add(sortByRatingAction);
		sortMenu.add(new Separator());
		sortMenu.add(sortAscendingAction);
		sortMenu.add(sortDescendingAction);
		menuManager.add(sortMenu);
		
		MenuManager actionMenu = new MenuManager("&Action");
		actionMenu.add(addFilmAction);
		actionMenu.add(addVideomovieAction);
		actionMenu.add(addTvMovieAction);
		actionMenu.add(addTvSeriesAction);
		actionMenu.add(addMiniSeriesAction);
		actionMenu.add(addMovieSerialAction);
		menuManager.add(actionMenu);
		
		MenuManager helpMenu = new MenuManager("&Help");
		helpMenu.add(helpHelpAction);
		helpMenu.add(new Separator());
		helpMenu.add(helpAboutAction);
		menuManager.add(helpMenu);
		
		return menuManager;
	}

	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);
		coolBarManager.add(createToolBarManager(0));
		coolBarManager.add(createToolBarManager(1));
//		coolBarManager.add(createToolBarManager(2));
		return coolBarManager;
	}

	protected ToolBarManager createToolBarManager(int number){
		ToolBarManager toolBarManager = new ToolBarManager(toolBarStyle);
		switch (number){
		case 0:
			toolBarManager.add(newAction);
			toolBarManager.add(fileOpenAction);
			toolBarManager.add(fileSaveAction);
			break;
		case 1:
			toolBarManager.add(new AddMovieDropdownMenu());
			toolBarManager.add(new TestAction("Test!"));
			break;
		case 2:
			//
			break;
		}
		return toolBarManager;
	}

	protected void initializeBounds(){
		//read the stored window size from settings file
		getShell().setSize(settings.getWindowSize());
		
		//if the window position is undefined, put the window in the middle of the screen. 
		if(settings.getWindowPosition().x == -1)
			getShell().setLocation(getCenterScreenPos(settings.getWindowSize()));
		else
			getShell().setLocation(settings.getWindowPosition());
	}

	protected Control createContents(Composite parent) {
		viewer = new ListViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new MovieContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		try {
			viewer.setInput(null);
		} catch (Exception e) {
			//TODO find out if this is ever thrown
			e.printStackTrace();
		}
		list = viewer.getList();
		list.addSelectionListener(new ListSelectionListener());
		return list;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("JMoviedb");
	}
	
//	protected StatusLineManager createStatusLineManager() {
//		StatusLineManager slm = new StatusLineManager();
//		slm.setErrorMessage("This is an error");
//		slm.setMessage("This is a message");
//		return slm;
//	}
	
	private void saveSettings() {
		settings.setWindowSize(getShell().getSize());
		settings.setWindowPosition(getShell().getLocation());
		
		settings.save();
	}
	
	public Point getCenterScreenPos(Point dimension) {
		Point screenSize = new Point(
				getShell().getDisplay().getClientArea().width,
				getShell().getDisplay().getClientArea().height);
		return new Point((screenSize.x-dimension.x)/2, (screenSize.y-dimension.y)/2);
	}
	
	/**
	 * Returns all the "add movie" actions
	 * @return an array of actions
	 */
	public AddMovieAction[] getAddMovieActions() {
		return new AddMovieAction[]{
				addFilmAction, 
				addVideomovieAction, 
				addTvMovieAction, 
				addTvSeriesAction, 
				addMiniSeriesAction, 
				addMovieSerialAction};
	}
	
	/**
	 * Sets a new message in the main window status line
	 * @param message the message to be set
	 */
	public void setStatusLineMessage(String message) {
		statusLine.setMessage(message);
	}
	
	public void setDB(Moviedb db) {
		viewer.setInput(db);
		
		String filePath = db.getSaveFile();
		String fileName;
		if(filePath != null) {
			int beginIndex = filePath.lastIndexOf(File.separator);
			fileName = filePath.substring(beginIndex + 1);
		} else {
			fileName = "New file";
		}
		
		getShell().setText("JMoviedb - " + fileName);
	}
	
	/**
	 * Returns the currently open database
	 * @return the database
	 */
	public Moviedb getDB() {
		return (Moviedb)viewer.getInput();
	}
	
	public void openMovieDialog(AbstractMovie m) throws SQLException {
		MovieDialog d = new MovieDialog(m);
		int returnCode = d.open();
		
		if(CONST.DEBUG_MODE) {
			String s = "The movie dialog was closed using the ";
			switch(returnCode) {
				case IDialogConstants.OK_ID:
					s += "OK button";
					break;
				case IDialogConstants.CANCEL_ID:
					s += "Cancel button";
					break;
				case IDialogConstants.ABORT_ID:
					s += "Delete button";
					break;
			}
			System.out.println(s);
		}
		
		switch (returnCode) {
			case IDialogConstants.OK_ID:
//				getDB().saveMovie(d.getModel());
				break;
			case IDialogConstants.ABORT_ID:
				//TODO MainWindow.getMainWindow().getDB().deleteMovie();
				break;
		}
	}
	
	/**
	 * Enable or disable certain GUI elements.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
//		if(getList() != null) //TODO dirty hack, fix this
//			getList().setEnabled(b); //org.eclipse.swt.SWTException: Invalid thread access????
		fileSaveAction.setEnabled(enabled);
		fileSaveAsAction.setEnabled(enabled);
		printAction.setEnabled(enabled);
//		addFilmAction.setEnabled(enabled); //TODO enable
		
		sortByIdAction.setEnabled(enabled);
		sortByTitleAction.setEnabled(enabled);
		sortByYearAction.setEnabled(enabled);
//		sortByTypeAction.setEnabled(enabled); //TODO enable this when sort by type is implemented
		sortByRatingAction.setEnabled(enabled);
		sortAscendingAction.setEnabled(enabled);
		sortDescendingAction.setEnabled(enabled);
	}

	/**
	 * Runs the application
	 */
	public void run() {
		// Don't return from open() until window closes
		setBlockOnOpen(true);

		// Open the main window
		open();

		// Dispose the display when exiting
		Display.getCurrent().dispose();
	}
	
	public void handleShellCloseEvent() {
		int saveAnswer = saveOnCloseQuestion();
		if(saveAnswer == CONST.ANSWER_SAVE)
			fileSaveAction.run();
		else if(saveAnswer == CONST.ANSWER_CANCEL)
			return;
		
		saveSettings();
		close();
		
		// Shut down Derby
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			//SQLException is always thrown on successful shutdown
			if(CONST.DEBUG_MODE)
				System.out.println("Derby was shut down");
		}
	}
	
	/**
	 * Asks the user whether or not (s)he wants to save the currently open file before closing it.
	 * @return CONST.ANSWER_SAVE, CONST.ANSWER_DONT_SAVE or CONST.ANSWER_CANCEL
	 */
	public int saveOnCloseQuestion() {
		int returnCode = CONST.ANSWER_DONT_SAVE;
		
		if(getDB() != null && getDB().isSaved() == false) {
			returnCode = new MessageDialog(MainWindow.getMainWindow().getShell(), "JMoviedb", null, "Do you want to save changes?", MessageDialog.QUESTION, new String[]{"Save", "Don't save", "Cancel"}, 0).open();
			if(returnCode == CONST.ANSWER_SAVE)
				fileSaveAction.run();
		}
		return returnCode;
	}
	
	public static MainWindow getMainWindow() {
		return instance;
	}
	
//	public Action getSaveAction() {
//		return fileSaveAction;
//	}
	
	/**
	 * Workaround to enable MainWindow's ExceptionHandler to handle exceptions thrown
	 * insisde other classes.
	 * @param t - the Exception to be thrown
	 * @throws Throwable
	 */
	public void handleException(Throwable t) {
		this.exceptionHandler.handleException(t);
	}
	
	/**
	 * Create or rebuild the recent files menu (rebuild each time the contents change)
	 */
	private void createRecentFilesMenu() {
		System.out.println("UPDATING RECENT FILES MENU");
		
		String[] recentFiles = settings.getRecentFiles();
		boolean[] recentEnabled = new boolean[4];
		for(int i = 0; i < recentFiles.length; i++) {
			if(recentFiles[i].length() == 0)
				recentEnabled[i] = false;
			else
				recentEnabled[i] = true;
		}
		
		openPreviousAction1 = new OpenPreviousAction(1, recentFiles[0], fileSaveAction);
		System.out.println(recentFiles[0]);
		openPreviousAction1.setEnabled(recentEnabled[0]);
		openPreviousAction2 = new OpenPreviousAction(2, recentFiles[1], fileSaveAction);
		openPreviousAction2.setEnabled(recentEnabled[1]);
		openPreviousAction3 = new OpenPreviousAction(3, recentFiles[2], fileSaveAction);
		openPreviousAction3.setEnabled(recentEnabled[2]);
		openPreviousAction4 = new OpenPreviousAction(4, recentFiles[3], fileSaveAction);
		openPreviousAction4.setEnabled(recentEnabled[3]);
	}
	
	/**
	 * Checks whether or not a browser window can be opened
	 * @return true if browser launching is supported, false otherwise
	 */
	public boolean canLaunchBrowser() {
		//TODO needs to actually do something
		return true;
	}

	/**
	 * The main method that launches the application.
	 * @param args - not currently used
	 */
	public static void main(String[] args) {
		for(String s : args)
			System.out.println(s);
		
		settings = Settings.getSettings();
		MainWindow m = new MainWindow();
		m.run();
	}

	public void propertyChange(PropertyChangeEvent pce) {
		if(pce.getProperty().equals(Moviedb.MOVIE_LIST_PROPERTY_NAME)) {
			if(CONST.DEBUG_MODE)
				System.out.println("PCE: REFRESH MOVIE LIST");
			viewer.refresh();
		}
		else if(pce.getProperty().equals(Moviedb.SAVE_STATUS_PROPERTY_NAME)) {
			if((Boolean)(pce.getNewValue())) {
				fileSaveAction.setEnabled(false);
				if(CONST.DEBUG_MODE)
					System.out.println("PCE: SAVE STATUS - SAVED");
			} else {
				fileSaveAction.setEnabled(true);
				if(CONST.DEBUG_MODE)
					System.out.println("PCE: SAVE STATUS - NOT SAVED");
			}
		}
		else if(pce.getProperty().equals(CONST.RECENT_FILES_PROPERTY_NAME)) {
			if(CONST.DEBUG_MODE)
				System.out.println("PCE: REFRESH RECENT FILES LIST");
			
			createRecentFilesMenu();
			System.out.println("new name should now be " + openPreviousAction1.getText());
			menuManager.updateAll(true); //TODO why does this not work?
		}
	}
	
	/**
	 * Upon startup, sets the correct checked/unchecked status of the sort menu items
	 * according to the previously stored settings.
	 */
	private void setSearchParameters() {
		Settings s = Settings.getSettings();
		if(s.getSortDirection() == CONST.SORT_ASCENDING)
			sortAscendingAction.setChecked(true);
		else
			sortDescendingAction.setChecked(true);
		
		switch(s.getSortBy()) {
			case CONST.SORT_BY_ID: sortByIdAction.setChecked(true); break;
			case CONST.SORT_BY_TITLE: sortByTitleAction.setChecked(true); break;
			case CONST.SORT_BY_YEAR: sortByYearAction.setChecked(true); break;
			case CONST.SORT_BY_TYPE: sortByTypeAction.setChecked(true); break;
			case CONST.SORT_BY_RATING: sortByRatingAction.setChecked(true); break;
		}
	}
	
}