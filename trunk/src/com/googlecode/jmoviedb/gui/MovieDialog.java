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
import com.googlecode.jmoviedb.enumerated.*;
import com.googlecode.jmoviedb.model.*;
import com.googlecode.jmoviedb.model.movietype.*;
import com.googlecode.jmoviedb.net.ImdbWorker;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * A MovieDialog is a tabbed dialog box that is used to display or edit information about an AbstractMovie subclass.
 * When the dialog is closed, it will reaturn one of the following return codes: IDialogConstants.OK_ID, (if the user
 * pressed the OK button) IDialogConstants.CANCEL_ID, (if the user pressed the cancel button) or 
 * IDialogConstants.ABORT_ID (if the user pressed the delete button)
 * @author Tor Arne Lye
 *
 */
public class MovieDialog extends Dialog {
	private AbstractMovie movie;
	private Label imageArea;
	private Combo typeCombo;
	private Text imdbText;
	private Text titleText;
	private Text altTitleText;
	private Text yearText;
	private Text directorText;
	private Text writerText;
	private Text genreText;
	private Text countryText;
	private Text languageText;
	private Text taglineText;
	private Text plotText;
	private Text runtimeText;
	private Scale rateScale;
	private Text rateText;
	private Button seenCheck;
	private Button colorCheck;
	private Text NotesText;
	private Combo versionCombo;
	private Text versionText;
	private Button legalCheck;
	
	private Table actorTable;
	private TableColumn actorNameColumn;
	private TableColumn asColumn;
	private TableColumn characterNameColumn;
	
	private Combo formatCombo;
	private Combo videoCodecCombo;
	private Combo containerCombo;
	private Combo resolutionCombo;
	private Combo tvSystemCombo;
	private Button r0;
	private Button r1;
	private Button r2;
	private Button r3;
	private Button r4;
	private Button r5;
	private Button r6;
	private Button r7;
	private Button r8;
	private AudioSubtitleTable audioTable;
	private AudioSubtitleTable subtitleTable;
	
	private static final int MARGIN_WIDTH = 7;
	private static final int MARGIN_HEIGHT = 7;
	private static final int VERTICAL_SPACING = 5;
	private static final int HORIZONTAL_SPACING = 6;
	private static final int HEIGHT_HINT = 14;
	
	private Image mainTabIcon;
	private Image taglineTabIcon;
	private Image actorTabIcon;
	private Image formatTabIcon;
	private Image audioTabIcon;
	
	private final static int COVER_WIDTH = 100;
	private final static int COVER_HEIGHT = 150;
	
	public MovieDialog(AbstractMovie movie) {
		super(MainWindow.getMainWindow());
		
		mainTabIcon = ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_MAINTAB).createImage();
		taglineTabIcon = ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_TAGLINEPLOTTAB).createImage();
		actorTabIcon = ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_ACTORSTAB).createImage();
		formatTabIcon = ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_FORMATTAB).createImage();
		audioTabIcon = ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_AUDIOSUBTAB).createImage();
		
		this.movie = movie;
		
		//TODO Sets keyboard focus to the save button
//		saveButton.setFocus();
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
//		shell.setSize(630, 490);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout(3, false);
		c.setLayout(gridLayout);
		gridLayout.marginWidth = 8;
		gridLayout.marginHeight = 8;
		gridLayout.verticalSpacing = 6;
		gridLayout.horizontalSpacing = 6;
		
		GridData tabFolderGD = new GridData();
		tabFolderGD.horizontalSpan = 3;
		tabFolderGD.horizontalAlignment = SWT.FILL;
		tabFolderGD.verticalAlignment = SWT.FILL;
		tabFolderGD.grabExcessVerticalSpace = true;
				
		final CTabFolder tabFolder = new CTabFolder(c, SWT.BORDER);
		tabFolder.setSimple(false);
		tabFolder.setTabHeight(24);
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
		
		
		MainTab(tabFolder);
		TaglinePlotTab(tabFolder);
		ActorsTab(tabFolder);
		formatVideoTab(tabFolder);
		audioSubtitleTab(tabFolder);

		tabFolder.setSelection(0); //select the first tab

		if(movie != null)
			setModel(movie);
		else
			setModel(new Film());
		
		return c;
	}
	
	private void MainTab(CTabFolder tabFolder) {
		CTabItem tab1 = new CTabItem(tabFolder, SWT.NULL);
		tab1.setText("Main   ");
		tab1.setImage(mainTabIcon);
		
		Composite c1 = new Composite(tabFolder, SWT.NULL);
		
		GridLayout gridLayout = new GridLayout(5, true);
		gridLayout.marginHeight = MARGIN_HEIGHT;
		gridLayout.marginWidth = MARGIN_WIDTH;
		gridLayout.verticalSpacing = VERTICAL_SPACING;
		gridLayout.horizontalSpacing = HORIZONTAL_SPACING;
		c1.setLayout(gridLayout);
		
		imageArea = new Label(c1, SWT.NONE);
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 14);
		gridData.widthHint = COVER_WIDTH;
		gridData.heightHint = COVER_HEIGHT;
		imageArea.setLayoutData(gridData);
		imageArea.setAlignment(SWT.CENTER);
		
		Label typeLabel = new Label(c1, SWT.CENTER);
		typeLabel.setText("Type:");
		typeCombo = new Combo(c1, SWT.DROP_DOWN|SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		typeCombo.setItems(MovieType.getStringArray());
		typeCombo.select(0);
		typeCombo.setVisibleItemCount(7); //make all items visible
		
		Label titleLabel = new Label(c1, SWT.CENTER);
		titleLabel.setText("Title:");
		titleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		titleText.setToolTipText("The title of the movie. If this field is changed, it will be overwritten " +
				"on the next IMDb update. If you want to use a custom title, pleas use the \"display title\" field.");
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label altTitleLabel = new Label(c1, SWT.CENTER);
		altTitleLabel.setText("Display title:");
		altTitleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		altTitleText.setToolTipText("This is the title that will be shown in the movie list. If empty, " +
				"the main title will be show instead. The display title will not be overwritten on IMDb updates.");
		altTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label yearLabel = new Label(c1, SWT.CENTER);
		yearLabel.setText("Year:");
		yearText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		yearText.setTextLimit(4);
		yearText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,3, 1));
		
		Label genreLabel = new Label(c1, SWT.CENTER);
		genreLabel.setText("Genre:");
		genreText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		genreText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label countryLabel = new Label(c1, SWT.CENTER);
		countryLabel.setText("Country:");
		countryText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		countryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label languageLabel = new Label(c1, SWT.CENTER);
		languageLabel.setText("Language:");
		languageText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		languageText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label runtimeLabel = new Label(c1, SWT.CENTER);
		runtimeLabel.setText("Runtime:");
		runtimeText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		runtimeText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		runtimeText.setToolTipText("This field will be overwritten by IMDb updates!");
		
		Label rateLabel = new Label(c1, SWT.CENTER);
		rateLabel.setText("Rate:");
		rateScale = new Scale(c1, SWT.HORIZONTAL);
		rateScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		rateScale.setIncrement(1);
		rateScale.setMinimum(0);
		rateScale.setMaximum(100);
		rateScale.setToolTipText("The rating will be overwritten by IMDb updates!");
		rateScale.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				//Updates the text in rateText when the slider is moved
				rateText.setText((0.0 + rateScale.getSelection()) / 10 + "");
			}
		});
		rateText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		rateText.setLayoutData(new GridData());
		rateText.setEditable(false);
		
		Label seenLabel = new Label(c1, SWT.CENTER);
		seenLabel.setText("I have seen this movie");
		seenCheck = new Button(c1, SWT.CHECK);
		seenCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,3, 1));
		
		Label imdbLabel = new Label(c1, SWT.CENTER);
		imdbLabel.setText("IMDb address:");
		imdbText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		imdbText.setToolTipText("If a valid IMDb URL is present, you won't have to select the correct movie " +
				"when running IMDb updates.");
		imdbText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		Button imdbGotoButton = new Button(c1, SWT.PUSH);
		imdbGotoButton.setText("Go to website");
		imdbGotoButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				MainWindow.getMainWindow().launchBrowser(imdbText.getText());
			}
		});
		
//		Disable widgets until their respective functions are implememnted.
		genreText.setEditable(false);
		countryText.setEditable(false);
		languageText.setEditable(false);
		
		tab1.setControl(c1);
		c1.setSize(700, 300);
	}
	
	
	private void TaglinePlotTab(CTabFolder tabFolder) {
		CTabItem tab2 = new CTabItem(tabFolder, SWT.NULL);
		tab2.setText("Tagline and plot");
		tab2.setImage(taglineTabIcon);
		
		Composite c2 = new Composite(tabFolder, SWT.NULL);
		GridLayout gridLayout2 = new GridLayout(2, false);
		gridLayout2.marginHeight = MARGIN_HEIGHT;
		gridLayout2.marginWidth = MARGIN_WIDTH;
		gridLayout2.verticalSpacing = VERTICAL_SPACING;
		gridLayout2.horizontalSpacing = HORIZONTAL_SPACING;
		c2.setLayout(gridLayout2);
		
		Label taglineLabel = new Label(c2, SWT.LEFT);
		taglineLabel.setText("Tagline:");
		GridData gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		gd.widthHint = 70;
		taglineLabel.setLayoutData(gd);
		taglineText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		//taglineText.setSize(100, 100);
		taglineText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label plotLabel = new Label(c2, SWT.LEFT);
		plotLabel.setText("Plot outline:");
		gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		gd.widthHint = 70;
		plotLabel.setLayoutData(gd);
		plotText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		plotText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		tab2.setControl(c2);
	}
	
	private void ActorsTab(CTabFolder tabFolder) {
		CTabItem tab3 = new CTabItem(tabFolder, SWT.NULL);
		tab3.setText("Actors, directors and writers");
		tab3.setImage(actorTabIcon);
		
		Composite c3 = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(2, false);
		c3.setLayout(compositeLayout);
		
		Label directorLabel = new Label(c3, SWT.CENTER);
		directorLabel.setText("Directed by:");
		directorText = new Text(c3, SWT.SINGLE|SWT.BORDER);
		directorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label writerLabel = new Label(c3, SWT.CENTER);
		writerLabel.setText("Written by:");
		writerText = new Text(c3, SWT.SINGLE|SWT.BORDER);
		writerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		actorTable = new Table (c3, SWT.BORDER | SWT.MULTI | SWT.SINGLE);
		actorTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		actorTable.setHeaderVisible(true);
		actorTable.setLinesVisible(true);
		actorTable.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(MovieDialog.this.getShell(), "Unimplemented feature!",
				"The completed version will do something cool when selecting an actor name, like " +
				"opening the actor's IMDb page or maybe displaying other movies with the same actor.");
			}
		});
		actorNameColumn = new TableColumn(actorTable, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		asColumn = new TableColumn(actorTable, SWT.NONE);
		characterNameColumn = new TableColumn(actorTable, SWT.NONE);
		actorNameColumn.setText("Actor");
		asColumn.setText("");
		characterNameColumn.setText("Character");
		
		//TODO find out why auto column width is a little too small
//		actorNameColumn.setResizable(false);
//		asColumn.setResizable(false);
//		characterNameColumn.setResizable(false);
		
		
		directorText.setEditable(false);
		writerText.setEditable(false);
		tab3.setControl(c3);
	}
	
	private void formatVideoTab(CTabFolder tabFolder) {
		CTabItem tab4 = new CTabItem(tabFolder, SWT.NULL);
		tab4.setText("Format and video");
		tab4.setImage(formatTabIcon);
		
		Composite c = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(10, false);
		compositeLayout.makeColumnsEqualWidth = false;
		c.setLayout(compositeLayout);
		
		GridData comboLayout = new GridData();
		comboLayout.horizontalSpan = 9;
		comboLayout.horizontalAlignment = SWT.FILL;
		comboLayout.grabExcessHorizontalSpace = true;
		
		Label formatLabel = new Label(c, SWT.CENTER);
		formatLabel.setText("Format:");
		formatCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		formatCombo.setLayoutData(comboLayout);
		formatCombo.setItems(FormatType.getStringArray());
		formatCombo.select(0);
		formatCombo.setVisibleItemCount(FormatType.getStringArray().length); //make all items visible
		
		Label containerLabel = new Label(c, SWT.CENTER);
		containerLabel.setText("Container format:");
		containerCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		containerCombo.setLayoutData(comboLayout);
		containerCombo.setItems(ContainerFormat.getStringArray());
		containerCombo.select(0);
		containerCombo.setVisibleItemCount(ContainerFormat.getStringArray().length); //make all items visible
		
		Label videoCodecLabel = new Label(c, SWT.CENTER);
		videoCodecLabel.setText("Video format:");
		videoCodecCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		videoCodecCombo.setLayoutData(comboLayout);
		videoCodecCombo.setItems(VideoCodec.getStringArray());
		videoCodecCombo.select(0);
		videoCodecCombo.setVisibleItemCount(VideoCodec.getStringArray().length); //make all items visible
		
		Label resolutionLabel = new Label(c, SWT.CENTER);
		resolutionLabel.setText("Video resolution:");
		resolutionCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		resolutionCombo.setLayoutData(comboLayout);
		resolutionCombo.setItems(Resolution.getStringArray());
		resolutionCombo.select(0);
		resolutionCombo.setVisibleItemCount(Resolution.getStringArray().length); //make all items visible
		
		Label tvSystemLabel = new Label(c, SWT.CENTER);
		tvSystemLabel.setText("TV system:");
		tvSystemCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		tvSystemCombo.setLayoutData(comboLayout);
		tvSystemCombo.setItems(TVsystem.getStringArray());
		tvSystemCombo.select(0);
		tvSystemCombo.setVisibleItemCount(TVsystem.getStringArray().length); //make all items visible
		
		Label regionLabel = new Label(c, SWT.CENTER);
		regionLabel.setText("Region:");
		r0 = new Button(c, SWT.CHECK);
		r0.setText("Region free");
		r1 = new Button(c, SWT.CHECK);
		r1.setText("R1");
		r2 = new Button(c, SWT.CHECK);
		r2.setText("R2");
		r3 = new Button(c, SWT.CHECK);
		r3.setText("R3");
		r4 = new Button(c, SWT.CHECK);
		r4.setText("R4");
		r5 = new Button(c, SWT.CHECK);
		r5.setText("R5");
		r6 = new Button(c, SWT.CHECK);
		r6.setText("R6");
		r7 = new Button(c, SWT.CHECK);
		r7.setText("R7");
		r8 = new Button(c, SWT.CHECK);
		r8.setText("R8");
		
		tab4.setControl(c);
	}
	
	private void audioSubtitleTab(CTabFolder tabFolder) {
		CTabItem tab5 = new CTabItem(tabFolder, SWT.NULL);
		tab5.setText("Audio and subtitles");
		tab5.setImage(audioTabIcon);
		
		Composite c = new Composite(tabFolder, SWT.NULL);
//		GridLayout compositeLayout = new GridLayout(2, false);
//		c.setLayout(compositeLayout);
		audioTable = new AudioSubtitleTable(c, true);
//		subtitleTable = new AudioSubtitleTable(c, false);
		
		tab5.setControl(c);
	}
	
	/**
	 * Sets the model for this MovieDialog
	 * @param m the movie to be used as a model
	 */
	private void setModel(AbstractMovie m) {
		this.getShell().setText("Movie info - " + m.getDisplayTitle() + " (" + m.getYear() + ")");
		typeCombo.select(MovieType.abstractMovieToInt(m));
		imdbText.setText(m.getImdbUrl());
		titleText.setText(m.getTitle());
		altTitleText.setText(m.getCustomTitle());
		if(m.getYear() != 0)
			yearText.setText(m.getYear() + "");
		directorText.setText(m.getDirectorsAsString());
		writerText.setText(m.getWritersAsString());
		genreText.setText(m.getGenresAsString());
		countryText.setText(m.getCountriesAsString());
		languageText.setText(m.getLanguagesAsString());
		if(m.getRunTime() != 0)
			runtimeText.setText(m.getRunTime() + "");
		rateScale.setSelection(m.getRatingAsInt());
		rateText.setText(m.getRating() + "");
		seenCheck.setSelection(m.isSeen());
	
		taglineText.setText(m.getTagline());
		plotText.setText(m.getPlotOutline());
		
		for(ActorInfo a : movie.getActors()) {
			TableItem i = new TableItem(actorTable, SWT.NONE);
			i.setText(0, a.getPerson().getName());
			i.setText(1, "as");
			i.setText(2, a.getCharacter());
		}
		
		formatCombo.select(m.getFormat().getID());
		containerCombo.select(m.getContainer().getID());
		videoCodecCombo.select(m.getVideo().getID());
		resolutionCombo.select(m.getResolution().getID());
		tvSystemCombo.select(m.getTvSystem().getID());
		r0.setSelection(m.getDvdRegion()[0]);
		r1.setSelection(m.getDvdRegion()[1]);
		r2.setSelection(m.getDvdRegion()[2]);
		r3.setSelection(m.getDvdRegion()[3]);
		r4.setSelection(m.getDvdRegion()[4]);
		r5.setSelection(m.getDvdRegion()[5]);
		r6.setSelection(m.getDvdRegion()[6]);
		r7.setSelection(m.getDvdRegion()[7]);
		r8.setSelection(m.getDvdRegion()[8]);
		
		actorNameColumn.pack();
		asColumn.pack();
		characterNameColumn.pack();
		
		audioTable.setAudioModel(movie.getAudioTracks());
//		subtitleTable.setSubModel(movie.getSubtitles());
		
		imageArea.setImage(new Image(MainWindow.getMainWindow().getShell().getDisplay(), 
				CONST.scaleImage(movie.getImageData(), false, COVER_WIDTH, COVER_HEIGHT)));
		
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DETAILS_ID, "IMDb update", false);
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
		createButton(parent, IDialogConstants.ABORT_ID, "Remove movie", false);
	}
	
	protected  void	buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		if(buttonId == IDialogConstants.CANCEL_ID)
			close();
		
		else if(buttonId == IDialogConstants.OK_ID) {
			if(titleText.getText().length() == 0)
				MessageDialog.openInformation(this.getShell(), "No title", "You can't save a movie without a title!");
			else {
				save();
				close();
			}
		}
		
		else if(buttonId == IDialogConstants.DETAILS_ID) {
			try {
				save();
				ImdbWorker w = new ImdbWorker();
				movie = w.update(movie, getShell());
				setModel(movie);
			} catch (Exception e) {
				MainWindow.getMainWindow().handleException(e);
			}
		}
		
		else if(buttonId == IDialogConstants.ABORT_ID) {
			MessageDialog.openInformation(this.getShell(), "Oops", "That button doesn't do anyting yet");
		}
	}
	
	/**
	 * Closes the window. Extended to dispose of certain additional resources.
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close() {
		if(imageArea.getImage() != null)
			imageArea.getImage().dispose();
		mainTabIcon.dispose();
		taglineTabIcon.dispose();
		actorTabIcon.dispose();
		formatTabIcon.dispose();
		audioTabIcon.dispose();
		audioTable.dispose();
//		subtitleTable.dispose();
		return super.close();
	}
	
	private void save() {
		System.out.println("Movie type: " + MovieType.objectToEnum(movie));
		System.out.println("Selection: " + MovieType.stringToEnum(typeCombo.getItem(typeCombo.getSelectionIndex())));
		if(!MovieType.objectToEnum(movie).equals(MovieType.stringToEnum(typeCombo.getItem(typeCombo.getSelectionIndex())))) {
			AbstractMovie newMovie = MovieType.intToAbstractMovie(typeCombo.getSelectionIndex());
			System.out.println("_old id " + movie.getID()); //TODO whats going on here?
			newMovie.setID(movie.getID());
			System.out.println("_new id " + newMovie.getID());
			movie = newMovie;
			System.out.println("_new copied to old id " + movie.getID());
		}
		
		movie.setImdbID(imdbText.getText());
		movie.setTitle(titleText.getText());
		movie.setCustomTitle(altTitleText.getText());
		movie.setYear(yearText.getText());
		movie.setRunTime(runtimeText.getText());
		movie.setRatingAsInt(rateScale.getSelection());
		movie.setSeen(seenCheck.getSelection());

		movie.setTagline(taglineText.getText());
		movie.setPlotOutline(plotText.getText());
		
		movie.setFormat(FormatType.values()[formatCombo.getSelectionIndex()]);
		movie.setContainer(ContainerFormat.values()[containerCombo.getSelectionIndex()]);
		movie.setVideo(VideoCodec.values()[videoCodecCombo.getSelectionIndex()]);
		movie.setResolution(Resolution.values()[resolutionCombo.getSelectionIndex()]);
		movie.setTvSystem(TVsystem.values()[tvSystemCombo.getSelectionIndex()]);
		movie.setDvdRegion(new boolean[]
				{	r0.getSelection(), 
					r1.getSelection(),
					r2.getSelection(),
					r3.getSelection(),
					r4.getSelection(),
					r5.getSelection(),
					r6.getSelection(),
					r7.getSelection(),
					r8.getSelection(),
				});
	}
	
	public AbstractMovie getModel() {
		return movie;
	}
}

