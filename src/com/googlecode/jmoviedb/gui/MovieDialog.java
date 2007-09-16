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

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.*;
import com.googlecode.jmoviedb.model.*;
import com.googlecode.jmoviedb.model.movietype.*;
import com.googlecode.jmoviedb.net.ImdbWorker;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

/**
 * A MovieDialog is a tabbed dialog box that is used to display or edit information about an AbstractMovie subclass.
 * When the dialog is closed, it will reaturn one of the following return codes: IDialogConstants.OK_ID, (if the user
 * pressed the OK button) IDialogConstants.CANCEL_ID, (if the user pressed the cancel button) or 
 * IDialogConstants.ABORT_ID (if the user pressed the delete button)
 * @author Tor Arne Lye
 *
 */
public class MovieDialog extends Dialog implements org.eclipse.swt.events.SelectionListener {
	private AbstractMovie movie;
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
	
	private static final int MARGIN_WIDTH = 7;
	private static final int MARGIN_HEIGHT = 7;
	private static final int VERTICAL_SPACING = 5;
	private static final int HORIZONTAL_SPACING = 6;
	private static final int HEIGHT_HINT = 14;
	
	
	public MovieDialog(AbstractMovie movie) {
		super(MainWindow.getMainWindow());
//	public MovieDialog(AbstractMovie movie, Shell shell) {
//		super(shell);
		
		this.movie = movie;
		
		//Sets keyboard focus to the save button
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

		tabFolder.setLayoutData(tabFolderGD);
		tabFolder.setSelection(0); //select the first tab

		if(movie != null)
			setModel(movie);
		else
			setModel(new Film());
		
		return c;
	}
	
	private void MainTab(CTabFolder tabFolder) {
		Image image = new Image(null, CONST.ICON_MOVIEDIALOG_MAINTAB);
		
		CTabItem tab1 = new CTabItem(tabFolder, SWT.NULL);
		tab1.setText("Main    ");
		tab1.setImage(image);
		
		Composite c1 = new Composite(tabFolder, SWT.NULL);
		
		
		
		GridLayout gridLayout1 = new GridLayout(5, true);
		c1.setLayout(gridLayout1);
		gridLayout1.marginHeight = MARGIN_HEIGHT;
		gridLayout1.marginWidth = MARGIN_WIDTH;
		gridLayout1.verticalSpacing = VERTICAL_SPACING;
		gridLayout1.horizontalSpacing = HORIZONTAL_SPACING;

		GridData imageLayout = new GridData();
		imageLayout.verticalSpan = 14;
		imageLayout.horizontalAlignment = SWT.CENTER;
		imageLayout.verticalAlignment = SWT.CENTER;
//		imageGD.minimumWidth = 1000; //doesn't work
		imageLayout.widthHint = 100;
		imageLayout.heightHint = 150;

		GridData longTextFieldLayout = new GridData();
		longTextFieldLayout.horizontalSpan = 3;
		longTextFieldLayout.horizontalAlignment = SWT.FILL;
//		longTextFieldLayout.heightHint = HEIGHT_HINT;
		
		GridData shortTextFieldLayout = new GridData();
		shortTextFieldLayout.horizontalSpan = 1;
		shortTextFieldLayout.horizontalAlignment = SWT.LEFT;
//		shortTextFieldLayout.heightHint = HEIGHT_HINT;
		shortTextFieldLayout.widthHint = 50;
		
		GridData scaleLayout = new GridData();
		scaleLayout.horizontalSpan = 2;
		scaleLayout.horizontalAlignment = SWT.FILL;
		
		GridData fillerLayout1 = new GridData();
		fillerLayout1.horizontalSpan = longTextFieldLayout.horizontalSpan - shortTextFieldLayout.horizontalSpan;
		fillerLayout1.verticalAlignment = SWT.CENTER;
		fillerLayout1.heightHint = 1;
		

		Composite imageArea = new Composite(c1, SWT.BORDER);
		imageArea.setLayoutData(imageLayout);
		
		Label typeLabel = new Label(c1, SWT.CENTER);
		typeLabel.setText("Type:");
		typeCombo = new Combo(c1, SWT.DROP_DOWN|SWT.READ_ONLY);
		typeCombo.setLayoutData(longTextFieldLayout);
		typeCombo.setItems(MovieType.getStringArray());
		typeCombo.select(0);
		typeCombo.setVisibleItemCount(7); //make all items visible
		
		Label titleLabel = new Label(c1, SWT.CENTER);
		titleLabel.setText("Title:");
		titleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		titleText.setToolTipText("The title of the movie.");
		//System.out.println(titleText.getLineHeight());
		titleText.setLayoutData(longTextFieldLayout);
		
		Label altTitleLabel = new Label(c1, SWT.CENTER);
		altTitleLabel.setText("Display title:");
		altTitleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		altTitleText.setToolTipText("If empty, title is displayed.");
		altTitleText.setLayoutData(longTextFieldLayout);
		
		Label yearLabel = new Label(c1, SWT.CENTER);
		yearLabel.setText("Year:");
		yearText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		yearText.setTextLimit(4);
		yearText.setLayoutData(shortTextFieldLayout);
		Composite yearFiller = new Composite(c1, SWT.NONE);
		yearFiller.setLayoutData(fillerLayout1);
		
		Label directorLabel = new Label(c1, SWT.CENTER);
		directorLabel.setText("Directed by:");
		directorText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		directorText.setLayoutData(longTextFieldLayout);
		
		Label writerLabel = new Label(c1, SWT.CENTER);
		writerLabel.setText("Written by:");
		writerText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		writerText.setLayoutData(longTextFieldLayout);
		
		Label genreLabel = new Label(c1, SWT.CENTER);
		genreLabel.setText("Genres:");
		genreText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		genreText.setLayoutData(longTextFieldLayout);
		
		Label countryLabel = new Label(c1, SWT.CENTER);
		countryLabel.setText("Country:");
		countryText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		countryText.setLayoutData(longTextFieldLayout);
		
		Label languageLabel = new Label(c1, SWT.CENTER);
		languageLabel.setText("Language:");
		languageText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		languageText.setLayoutData(longTextFieldLayout);
		
		Label runtimeLabel = new Label(c1, SWT.CENTER);
		runtimeLabel.setText("Runtime:");
		runtimeText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		runtimeText.setLayoutData(shortTextFieldLayout);
		Composite runtimeFiller = new Composite(c1, SWT.NONE);
		runtimeFiller.setLayoutData(fillerLayout1);
		
		Label rateLabel = new Label(c1, SWT.CENTER);
		rateLabel.setText("Rate:");
		rateScale = new Scale(c1, SWT.HORIZONTAL);
		rateScale.setLayoutData(scaleLayout);
		rateScale.setIncrement(1);
		rateScale.setMinimum(0);
		rateScale.setMaximum(100);
		rateScale.addSelectionListener(this);
		rateText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		rateText.setLayoutData(shortTextFieldLayout);
		rateText.setEditable(false);
		
		Label seenLabel = new Label(c1, SWT.CENTER);
		seenLabel.setText("I have seen this movie");
		seenCheck = new Button(c1, SWT.CHECK);
		Composite seenFiller = new Composite(c1, SWT.NONE);
		seenFiller.setLayoutData(fillerLayout1);
		
		Label imdbLabel = new Label(c1, SWT.CENTER);
		imdbLabel.setText("IMDb address:");
		imdbText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		//		imdbText.setSize(200, 50); //fungerer ikke
		imdbText.setToolTipText("An 8-digit ID number. You can also paste the IMDb URL here");
		imdbText.setLayoutData(shortTextFieldLayout);
		Button imdbGotoButton = new Button(c1, SWT.PUSH);
		imdbGotoButton.setText("Go to website");
		
//		Disable widgets until their respective functions are implememnted.
		directorText.setEnabled(false);
		writerText.setEnabled(false);
		genreText.setEnabled(false);
		countryText.setEnabled(false);
		languageText.setEnabled(false);
		
		tab1.setControl(c1);
		c1.setSize(700, 300);
	}
	
	
	private void TaglinePlotTab(CTabFolder tabFolder) {
		Image image = new Image(null, CONST.ICON_MOVIEDIALOG_TAGLINEPLOTTAB);
		
		CTabItem tab2 = new CTabItem(tabFolder, SWT.NULL);
		tab2.setText("Tagline / plot");
		tab2.setImage(image);
		
		Composite c2 = new Composite(tabFolder, SWT.NULL);
		
		GridLayout gridLayout2 = new GridLayout(2, false);
		c2.setLayout(gridLayout2);
		gridLayout2.marginHeight = MARGIN_HEIGHT;
		gridLayout2.marginWidth = MARGIN_WIDTH;
		gridLayout2.verticalSpacing = VERTICAL_SPACING;
		gridLayout2.horizontalSpacing = HORIZONTAL_SPACING;
		
		GridData gdFill = new GridData();
		gdFill.verticalAlignment = SWT.FILL;
		gdFill.horizontalAlignment = SWT.FILL;
		gdFill.grabExcessHorizontalSpace = true;
		gdFill.grabExcessVerticalSpace = true;
		
		GridData gd3 = new GridData();
		gd3.verticalAlignment = SWT.TOP;
		gd3.horizontalAlignment = SWT.LEFT;
		gd3.widthHint = 70;
		
		Label taglineLabel = new Label(c2, SWT.LEFT);
		taglineLabel.setText("Tagline:");
		taglineLabel.setLayoutData(gd3);
		taglineText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		//taglineText.setSize(100, 100);
		taglineText.setLayoutData(gdFill);
		
		Label plotLabel = new Label(c2, SWT.LEFT);
		plotLabel.setText("Plot outline:");
		plotLabel.setLayoutData(gd3);
		plotText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		plotText.setLayoutData(gdFill);
		
		tab2.setControl(c2);
	}
	
	private void ActorsTab(CTabFolder tabFolder) {
		Image image = new Image(null, CONST.ICON_MOVIEDIALOG_ACTORSTAB);
		CTabItem tab3 = new CTabItem(tabFolder, SWT.NULL);
		tab3.setText("Actors");
		tab3.setImage(image);
		
		Composite c3 = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(2, false);
		c3.setLayout(compositeLayout);
		
		GridData gdFill = new GridData();
		gdFill.verticalAlignment = SWT.FILL;
		gdFill.horizontalAlignment = SWT.FILL;
		gdFill.grabExcessHorizontalSpace = true;
		gdFill.grabExcessVerticalSpace = true;
		
		actorTable = new Table (c3, SWT.BORDER | SWT.MULTI | SWT.SINGLE); //TODO can be VIRTUAL
		actorTable.setLayoutData(gdFill);
		actorTable.setHeaderVisible(true);
		TableColumn actorNameColumn = new TableColumn(actorTable, SWT.NONE);
		TableColumn asColumn = new TableColumn(actorTable, SWT.NONE);
		TableColumn characterNameColumn = new TableColumn(actorTable, SWT.NONE);
		actorNameColumn.setText("Actor");
		asColumn.setText("");
		characterNameColumn.setText("Character");
		
		//TODO move to setModel
//		for(ActorInfo a : movie.getActors()) {
//			TableItem i = new TableItem(actorTable, SWT.NONE);
//			i.setText(0, a.getPerson().getName());
//			i.setText(1, "as");
//			i.setText(2, a.getCharacter());
//		}

		//TODO find out why auto column width is a little too small
//		actorNameColumn.setResizable(false);
//		asColumn.setResizable(false);
//		characterNameColumn.setResizable(false);
		
		actorNameColumn.pack();
		asColumn.pack();
		characterNameColumn.pack();
		
		tab3.setControl(c3);
	}
	
	private void formatVideoTab(CTabFolder tabFolder) {
		Image image = new Image(null, CONST.ICON_MOVIEDIALOG_FORMATTAB);
		CTabItem tab4 = new CTabItem(tabFolder, SWT.NULL);
		tab4.setText("Format and video");
		tab4.setImage(image);
	}
	
	private void audioSubtitleTab(CTabFolder tabFolder) {
		Image image = new Image(null, CONST.ICON_MOVIEDIALOG_AUDIOSUBTAB);
		CTabItem tab5 = new CTabItem(tabFolder, SWT.NULL);
		tab5.setText("Audio and subtitles");
		tab5.setImage(image);
		
		Composite c5 = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(2, false);
		c5.setLayout(compositeLayout);
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
//		directorText.setText(m.getDirectors());
//		writerText.setText(m.getWriters());
//		genreText.setText(m.getGenres());
//		countryText.setText(m.getCountries());
//		languageText.setText(m.getImdbLanguages());
		if(m.getRunTime() != 0)
			runtimeText.setText(m.getRunTime() + "");
		rateScale.setSelection(m.getRatingAsInt());
		rateText.setText(m.getRating() + "");
		seenCheck.setSelection(m.isSeen());
	
		taglineText.setText(m.getTagline());
		plotText.setText(m.getPlotOutline());
		
		
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
				//TODO cant save a movie without a title
				System.out.println("error");
			else {
				save();
			
				close();
			}
		}
		
		else if(buttonId == IDialogConstants.DETAILS_ID) {
			try {
				movie = ImdbWorker.update(this, movie);
				setModel(movie);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void save() {
		System.out.println("Movie type: " + MovieType.objectToEnum(movie));
		System.out.println("Selection: " + MovieType.stringToEnum(typeCombo.getItem(typeCombo.getSelectionIndex())));
		if(!MovieType.objectToEnum(movie).equals(MovieType.stringToEnum(typeCombo.getItem(typeCombo.getSelectionIndex())))) {
			AbstractMovie newMovie = MovieType.intToAbstractMovie(typeCombo.getSelectionIndex());
			System.out.println("_old id " + movie.getID());
			newMovie.setID(movie.getID());
			System.out.println("_new id " + newMovie.getID());
			movie = newMovie;
			System.out.println("_new copied to old id " + movie.getID());
		}
		
		movie.setImdbIDfromURL(imdbText.getText());
		movie.setTitle(titleText.getText());
		movie.setCustomTitle(altTitleText.getText());
		movie.setYear(yearText.getText());
		movie.setRunTime(runtimeText.getText());
		movie.setRatingAsInt(rateScale.getSelection());
		movie.setSeen(seenCheck.getSelection());

		movie.setTagline(taglineText.getText());
		movie.setPlotOutline(plotText.getText());
	}
	
	public AbstractMovie getModel() {
		return movie;
	}

	/**
	 * Is never called, but must remain for compatibility with the
	 * SelectionListener interface
	 */
	public void widgetDefaultSelected(SelectionEvent e) {}

	public void widgetSelected(SelectionEvent e) {
		rateText.setText((0.0 + rateScale.getSelection()) / 10 + "");
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("New movie");
		TVmovie f = new TVmovie();
		f.setID(5);
		f.setTitle("The Big Red One");
		f.setCustomTitle("Tha Big Red One");
		f.setFormat(FormatType.dvd);
//		f.setImdbID("0080437");
		f.setImdbID("0401792");
		ArrayList<Genre> g = new ArrayList<Genre>();
		g.add(Genre.war);
		g.add(Genre.drama);
		f.setGenres(g);
		f.setTagline("This is a tagline");
		f.setPlotOutline("This is a plot outline");
		ArrayList<Country> c = new ArrayList<Country>();
		c.add(Country.sweden);
		f.setCountries(c);
		ArrayList<Language> l = new ArrayList<Language>();
		l.add(Language.english);
		f.setImdbLanguages(l);
		f.setLegal(false);
		f.setSeen(true);
		f.setYear(1989);
		f.setRunTime(140);
		f.setRating(8.7);
		f.setVersion(FilmVersion.directors);
		f.setCustomVersion("omgz");
		Person p1 = new Person("65465464", "Jean-Claude Van Damme");
		Person p2 = new Person("65465474", "Bruce Willis");
		Person p3 = new Person("23424", "Director 1");
		Person p4 = new Person("3245345", "Director 2");
		Person p5 = new Person("234234", "Writer 1");
		Person p6 = new Person("32234234", "Writer 2");
		ArrayList<ActorInfo> actors = new ArrayList<ActorInfo>();
		actors.add(new ActorInfo(0, p1, "the Duke"));
		actors.add(new ActorInfo(0, p2, "the Man"));
		f.setActors(actors);
		ArrayList<Person> directors = new ArrayList<Person>();
		directors.add(p3);
		directors.add(p4);
		f.setDirectors(directors);
		ArrayList<Person> writers = new ArrayList<Person>();
		writers.add(p5);
		writers.add(p6);
		
		f.setAspectRatio(AspectRatio.unspecified);
		f.setFormat(FormatType.file);
		f.setContainer(ContainerFormat.matroska);
		f.setVideo(VideoCodec.h264);
		AudioTrack t1 = new AudioTrack(Language.english, AudioCodec.ac3, false);
		AudioTrack t2 = new AudioTrack(Language.finnish, AudioCodec.dts, false);
		ArrayList<AudioTrack> audio = new ArrayList<AudioTrack>();
		audio.add(t1);
		audio.add(t2);
		f.setAudioTracks(audio);
		SubtitleTrack s1 = new SubtitleTrack(Language.norwegian, SubtitleFormat.subviewer, false, false);
		SubtitleTrack s2 = new SubtitleTrack(Language.afrikaans, SubtitleFormat.subrip, false, true);
		ArrayList<SubtitleTrack> subs = new ArrayList<SubtitleTrack>();
		subs.add(s1);
		subs.add(s2);
		f.setSubtitles(subs);
		f.setTvSystem(TVsystem.pal);
		f.setSceneReleaseName("sceneReleaseName");
		f.setColor(true);
		f.setResolution(Resolution.p1080);
		
//		try {
//			ImdbWorker.update(new MovieDialog(f, shell), f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		new MovieDialog(f, shell).open();
		
		System.out.println(f.getTitle());
		
		
//		while(!shell.isDisposed())
//			if(!display.readAndDispatch())
//				display.sleep();
//		display.dispose();
	}
}

