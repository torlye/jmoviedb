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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Comparator;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import ca.odell.glazedlists.swt.EventKTableModel;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.gui.action.*;
import com.googlecode.jmoviedb.gui.action.sort.IdSorter;
import com.googlecode.jmoviedb.gui.action.sort.RatingSorter;
import com.googlecode.jmoviedb.gui.action.sort.TitleSorter;
import com.googlecode.jmoviedb.gui.action.sort.YearSorter;
import com.googlecode.jmoviedb.model.Moviedb;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellDoubleClickListener;
import de.kupzog.ktable.SWTX;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import edu.stanford.ejalbert.launching.IBrowserLaunching;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow extends ApplicationWindow implements IPropertyChangeListener {
	private String[] cmdLineArgs;
        
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
	private AddMovieDropdownMenu addMovieDropdownMenu;
	
	private HelpHelpAction helpHelpAction;
	private HelpAboutAction helpAboutAction;
	
	private MenuManager menuManager;
	private MenuManager fileMenu;
	
	private Moviedb currentlyOpenDb;
//	private List list;
//	private EventListViewer viewer;
	private SortedList<AbstractMovie> sortedList;
	private FilterList<AbstractMovie> filteredList;
	private KTable table;
	private EventKTableModel viewer;
	
	private SearchField searchField;
        private SearchDropDownMenu dropdownMenu;
//	private ClearSearchfieldAction clearSearchfieldAction;
        
        
	private StatusLineThreadManager statusLine;
	
	private ExceptionHandler exceptionHandler;
	private BrowserLauncher browserLauncher;

	public MainWindow(String[] args) {
		super(null);
		instance = this;
		cmdLineArgs = args;
		Settings.getSettings().addListener(this);
		exceptionHandler = new ExceptionHandler();
		setExceptionHandler(exceptionHandler);
		try {
			browserLauncher = new BrowserLauncher(null, exceptionHandler);
			/*
			 * If one of the following exceptions are thrown and caught,
			 * the system will not be able to open browsers. 
			 */
		} catch (BrowserLaunchingInitializingException e) {handleException(e);
		} catch (UnsupportedOperatingSystemException e) {handleException(e);}
		
		setDefaultImages(new Image[]{
				ImageDescriptor.createFromURL(CONST.ICON_MAIN_16).createImage(), 
				ImageDescriptor.createFromURL(CONST.ICON_MAIN_32).createImage(), 
				ImageDescriptor.createFromURL(CONST.ICON_MAIN_64).createImage(), 
				ImageDescriptor.createFromURL(CONST.ICON_MAIN_128).createImage(), 
				ImageDescriptor.createFromURL(CONST.ICON_MAIN_256).createImage()});
		
		fileSaveAsAction = new FileSaveAsAction();
		fileSaveAction = new FileSaveAction(fileSaveAsAction);
		newAction = new FileNewAction();
		fileOpenAction = new FileOpenAction();
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
		printAction.setEnabled(false);
		searchField = new SearchField();
                searchField.setEventReceiver(this); //MainWindow receives events from searchField
                dropdownMenu = new SearchDropDownMenu();
                dropdownMenu.setEventReceiver(searchField); //SearchWindow receives events from dropdownMenu  
		
		addFilmAction = new AddMovieAction(CONST.MOVIETYPE_FILM);
		addMovieSerialAction = new AddMovieAction(CONST.MOVIETYPE_MOVIESERIAL);
		addVideomovieAction = new AddMovieAction(CONST.MOVIETYPE_VIDEOMOVIE);
		addTvMovieAction = new AddMovieAction(CONST.MOVIETYPE_TVMOVIE);
		addTvSeriesAction = new AddMovieAction(CONST.MOVIETYPE_TVSERIES);
		addMiniSeriesAction = new AddMovieAction(CONST.MOVIETYPE_MINISERIES);
		addMovieDropdownMenu = new AddMovieDropdownMenu();
		
		addMenuBar();
		addCoolBar(coolBarStyle);
		addStatusLine();
		
		statusLine = new StatusLineThreadManager(getStatusLineManager());
		
		setSearchParameters();
	}

	/**
	 * Creates the program menus
	 */
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
		editMenu.add(new TestAction("Test!", false));
		menuManager.add(editMenu);
		
		MenuManager viewMenu = new MenuManager("&View");
		viewMenu.add(new TestAction("Test!", false));
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
		actionMenu.add(new MassUpdateAction());
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

	/**
	 * Creates the CoolBar
	 */
	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);
		coolBarManager.add(createToolBarManager(0));
		coolBarManager.add(createToolBarManager(1));
		coolBarManager.add(createToolBarManager(2));
		return coolBarManager;
	}

	/**
	 * Creates the ToolBar
	 */
	protected ToolBarManager createToolBarManager(int number){
		ToolBarManager toolBarManager = new ToolBarManager(toolBarStyle);
		switch (number){
		case 0:
			toolBarManager.add(newAction);
			toolBarManager.add(fileOpenAction);
			toolBarManager.add(fileSaveAction);
			break;
		case 1:
			toolBarManager.add(addMovieDropdownMenu);
			toolBarManager.add(new TestAction("Test!", false));
			toolBarManager.add(new TestAction("Crash!", true));
			break;
		case 2:
			toolBarManager.add(searchField);
                        toolBarManager.add(dropdownMenu);
			break;
		}
		return toolBarManager;
	}

	/**
	 * Sets windows size and location on startup
	 */
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
		
//Bug id 1796283 in sourceforge
//		private int getFullyVisibleRowCount(int fixedHeight) {
//			Rectangle rect = getClientArea();
//			ScrollBar sb = getHorizontalBar();
//			if (sb != null)
//			rect.height-=sb.getSize().y;
//
//			int count = 0;
//			int heightSum = fixedHeight;
//			heightSum+=m_Model.getRowHeight(m_TopRow);
//			for (int i=m_TopRow + 1; heightSum<rect.height; i++) {
//			count++;
//			heightSum+=m_Model.getRowHeight(i);
//			}
//			return count;
//			}
		
		table = new KTable(parent, SWT.V_SCROLL|SWTX.FILL_WITH_LASTCOL|SWT.BORDER|SWT.FULL_SELECTION);
		table.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		table.addCellDoubleClickListener(new KTableCellDoubleClickListener(){
			public void cellDoubleClicked(int col, int row, int statemask) {
				try {
					openMovieDialog(null);
				} catch (Exception ex) {
					handleException(ex);
				}	
			}
			public void fixedCellDoubleClicked(int col, int row, int statemask) {}
		});
		table.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(e.character=='\r')
					try {
						openMovieDialog(null);
					} catch (Exception ex) {
						handleException(ex);
					}
			}
			public void keyReleased(KeyEvent e) {
				//Do nothing
			}});
		
		try {
			if(cmdLineArgs.length>0) {
				File f = new File(cmdLineArgs[0]);
				if(f.canRead())
					openDB(cmdLineArgs[0]);
				else {
					openDB(null);
				}
			} else {
				openDB(null);
			}
		} catch(Exception e) {
			handleException(e);
		}
		
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("JMoviedb");
	}
	
	/**
	 * Saves the configuration file
	 */
	private void saveSettings() {
		settings.setWindowSize(getShell().getSize());
		settings.setWindowPosition(getShell().getLocation());
		
		settings.save();
	}
	
	/**
	 * Calculates the screen coordinates the window should move to if it is to be centered on the screen.
	 * @param dimension
	 * @return screen coordinates
	 */
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
	
	/**
	 * Opens a new database in the main window
	 * @param path to the database, or null
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void openDB(String path) throws ClassNotFoundException, SQLException, IOException {
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, new OpenDBWorker(path));
			table.setModel(viewer);
			Settings.getSettings().updateRecentFiles(path);
			updateShellText();
			if(currentlyOpenDb.getMovieCount()>0)
				setStatusLineMessage("Opened "+currentlyOpenDb.getMovieCount()+" movies");
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        /**
         * Updates filteredList and sortedList, called when search-parameter is changed,
         * is needed because filteredList needs to use new matcherEditor created in SearchField
         */
        public void updateLists() {
          try {
            //dbWorker.rebuildLists();
            UpdateListsWorker updateWorker = new UpdateListsWorker();
            new ProgressMonitorDialog(getShell()).run(true, false, updateWorker);
            table.setModel(viewer);
            
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

	private class OpenDBWorker implements IRunnableWithProgress {
		private String path;
		public OpenDBWorker(String path) {
			this.path = path;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException {
			try {
				monitor.beginTask("Loading database", IProgressMonitor.UNKNOWN);
				if(currentlyOpenDb != null) {
					monitor.subTask("Closing previous database");
					sortedList.dispose();
					filteredList.dispose();
					currentlyOpenDb.removeListener(MainWindow.getMainWindow());
					currentlyOpenDb.closeDatabase();
					currentlyOpenDb = null;
				}
				monitor.subTask("Opening database");
				Moviedb db = new Moviedb(path);

				monitor.subTask("Importing data");
				sortedList = new SortedList<AbstractMovie>(db.getMovieList(), getComparator());
                                filteredList = new FilterList<AbstractMovie>(sortedList, searchField.getMatcherEditor()); //TODO how do I fix this?
                                viewer = new EventKTableModel(table, filteredList, new MovieTableFormat());

				filteredList.addListEventListener(
						new ListEventListener<AbstractMovie>() {
							public void listChanged(ListEvent<AbstractMovie> arg0) {
                                                            filteredList = new FilterList<AbstractMovie>(sortedList, searchField.getMatcherEditor()); //TODO how do I fix this?
//								if(filteredList.size() != currentlyOpenDb.getMovieCount())
//									setStatusLineMessage("Showing "+filteredList.size()+" of "+currentlyOpenDb.getMovieCount()+" movies");
//								else
//									setStatusLineMessage(filteredList.size()+" movies");
							}
						});

				currentlyOpenDb = db;
				currentlyOpenDb.addListener(MainWindow.getMainWindow());
				fileSaveAction.setEnabled(false);
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}
		}                
	}
        
        /**
         * Used when the lists needs to be updated, and no new database needs to be loaded
         */
        private class UpdateListsWorker implements IRunnableWithProgress {
		public UpdateListsWorker() {
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException {
			try {   
                                //Unregister listeners
                                sortedList.dispose();
                                filteredList.dispose();
                          
				monitor.subTask("Rebuilding lists");
				sortedList = new SortedList<AbstractMovie>(currentlyOpenDb.getMovieList(), getComparator());
                                filteredList = new FilterList<AbstractMovie>(sortedList, searchField.getMatcherEditor());
                                viewer = new EventKTableModel(table, filteredList, new MovieTableFormat());
                                
                                //Register new event listener
				filteredList.addListEventListener(
						new ListEventListener<AbstractMovie>() {
							public void listChanged(ListEvent<AbstractMovie> arg0) {
                                                            filteredList = new FilterList<AbstractMovie>(sortedList, searchField.getMatcherEditor());
							}
						});
				fileSaveAction.setEnabled(false);
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}
		}
        }
	
	/**
	 * Returns the currently open database
	 * @return the database
	 */
	public Moviedb getDB() {
		return currentlyOpenDb;
	}
	
	/**
	 * Sorts the movie list
	 */
	public void reSort() {
		sortedList.setComparator(getComparator());
	}
	
	/**
	 * Creates a new Comparator according to the stored sort parameters
	 * @return a comparator
	 */
	private Comparator<AbstractMovie> getComparator() {
		int sortBy = Settings.getSettings().getSortBy();
		int sortDir = Settings.getSettings().getSortDirection();
		
		boolean descending = false;
		if(sortDir == CONST.SORT_DESCENDING)
			descending = true;
		
		//sorting according to the correct parameter
		if(sortBy == CONST.SORT_BY_ID) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by ID, descending: " + descending);
			return new IdSorter(descending);
		} else if(sortBy == CONST.SORT_BY_TITLE) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by title, descending: " + descending);
			return new TitleSorter(descending);
		} else if(sortBy == CONST.SORT_BY_YEAR) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by year, descending: " + descending);
			return new YearSorter(descending);
		} else if(sortBy == CONST.SORT_BY_RATING) {
			if(CONST.DEBUG_MODE)
				System.out.println("Sorting by rating, descending: " + descending);
			return new RatingSorter(descending);
		}
		return null;
	}
	
	/**
	 * Updates the text on the title bar (i.e. the shelltext)
	 * Call this whenever a new file is opened, or if an open file
	 * is saved in a new location/under a new name
	 */
	public void updateShellText() {
		String filePath = getDB().getSaveFile();
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
	 * Open a movie dialog
	 * @param movie a fresh AbstractMovie subclass if creating a new movie, or null if opening the selected item in the movie list. 
	 * @throws SQLException
	 * @throws IOException
	 */
	public void openMovieDialog(AbstractMovie movie) throws SQLException, IOException {
		AbstractMovie liteMovie = null;
		
		if(movie==null) {
			if(table.getRowSelection().length <= 0)
				return;
			liteMovie = filteredList.get(table.getRowSelection()[0]);
			movie=currentlyOpenDb.getMovie(liteMovie.getID());
		}
		
		MovieDialog d = new MovieDialog(movie);
		int returnCode = d.open();

		switch (returnCode) {
		case IDialogConstants.OK_ID:
			getDB().saveMovie(d.getModel(), liteMovie);//TODO maybe use movie instead of getModel
			break;
		case IDialogConstants.ABORT_ID:
			//TODO MainWindow.getMainWindow().getDB().deleteMovie();
			break;
		}
		d = null;
	}

	/**
	 * Enable or disable certain GUI widgets. This widgets should be enabled when a database file is open.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
//		if(getList() != null) //TODO dirty hack, fix this
//			getList().setEnabled(b); //org.eclipse.swt.SWTException: Invalid thread access????
		fileSaveAction.setEnabled(enabled);
		fileSaveAsAction.setEnabled(enabled);
//		printAction.setEnabled(enabled);//TODO enable when feature is implemented
		addFilmAction.setEnabled(enabled);
		addMovieSerialAction.setEnabled(enabled);
		addVideomovieAction.setEnabled(enabled);
		addTvMovieAction.setEnabled(enabled);
		addTvSeriesAction.setEnabled(enabled);
		addMiniSeriesAction.setEnabled(enabled);
		addMovieDropdownMenu.setEnabled(enabled);
		
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
	
	/**
	 * Called when the application is shutting down
	 */
	public void handleShellCloseEvent() {
		int saveAnswer = saveOnCloseQuestion();
		if(saveAnswer == CONST.ANSWER_SAVE)
			fileSaveAction.run();
		else if(saveAnswer == CONST.ANSWER_CANCEL)
			return;
		saveSettings();
		close();
		getDB().closeDatabase();
	}
	
	/**
	 * Asks the user whether or not (s)he wants to save the currently open file before closing it.
	 * @return CONST.ANSWER_SAVE, CONST.ANSWER_DONT_SAVE or CONST.ANSWER_CANCEL
	 */
	public int saveOnCloseQuestion() {
		int returnCode = CONST.ANSWER_DONT_SAVE;
		
		if(getDB() != null && getDB().isSaved() == false) {
			returnCode = new MessageDialog(getShell(), "JMoviedb", null, "Do you want to save changes?", MessageDialog.QUESTION, new String[]{"Save", "Don't save", "Cancel"}, 0).open();
			if(returnCode == CONST.ANSWER_SAVE)
				fileSaveAction.run();
		}
		return returnCode;
	}
	
	/**
	 * Returns the running instance of MainWindow
	 * @return
	 */
	public static MainWindow getMainWindow() {
		return instance;
	}
	
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
		System.out.println("BUILDING RECENT FILES MENU");
		
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
	 * Opens the specified URL in the system's web browser
	 * @param url the URL to open
	 */
	public void launchBrowser(String url) {
		System.out.println("Browse to " + url);
		if(browserLauncher!=null) {
			System.out.println(browserLauncher.getBrowserList().get(0));
			System.out.println(browserLauncher.getBrowserList().get(1));
			System.out.println(IBrowserLaunching.BROWSER_DEFAULT);
			browserLauncher.openURLinBrowser(url);
		}
		else
			MessageDialog.openError(this.getShell(), "Browser error!", 
					"Unable to open a web browser on this platform. Please browse to " + url + " manually.");
	}

	public void propertyChange(PropertyChangeEvent pce) {

		if(pce.getProperty().equals(Moviedb.SAVE_STATUS_PROPERTY_NAME)) {
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
		if(pce.getProperty().equals(CONST.RECENT_FILES_PROPERTY_NAME)) {
			createRecentFilesMenu();

			if(CONST.DEBUG_MODE) {
				System.out.println("PCE: REFRESH RECENT FILES LIST");
				System.out.println("The topmost item should now be " + openPreviousAction1.getText());
			}

			getMenuBarManager().updateAll(true); //TODO why does this not work?
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
	
//	public AbstractMovie getSelectedItem() throws SQLException, IOException {
//		return filteredList.get(list.getSelectionIndex());
//		
////		if(list.getSelectionIndex() != -1)
////			return currentlyOpenDb.getMovie(filteredList.get(list.getSelectionIndex()).getID());			
////		return null;
//	}

	/**
	 * The main method that launches the application.
	 * @param args - not currently used
	 */
	public static void main(String[] args) {
		for(String s : args)
			System.out.println("CmdLineArg: "+s);
		
		//Disable creation of the derby.log file
		System.getProperties().put("derby.stream.error.file", "");
		
		settings = Settings.getSettings();
		
		MainWindow m = new MainWindow(new String[0]);

		MessageDialog.openInformation(m.getShell(), "Warning", 
				"This is a very early test version of JMoviedb. " +
				"It has lots of errors and missing features. " +
				"It is meant for testing only!");
		m.run();
		
		
		/**
		 * More magic
		 */
		new org.eclipse.jface.wizard.Wizard() {
			public boolean performFinish() {
				new org.eclipse.jface.dialogs.TitleAreaDialog(null);
				new org.eclipse.jface.preference.PreferenceDialog(null, null);
				return false;
			}
		};
	}
}
