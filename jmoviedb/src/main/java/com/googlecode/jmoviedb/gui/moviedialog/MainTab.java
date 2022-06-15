package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.Completeness;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;
import com.googlecode.jmoviedb.model.movietype.AbstractSeries;

public class MainTab implements IMovieDialogTab {
    private Label imageArea;
	private Combo typeCombo;
	private Text imdbText;
	private Text tmdbText;
	private Text titleText;
	private Text altTitleText;
	private Text yearText;
	private Text year2Text;
	private Combo completenessCombo;
	private Text completenessText;
	private Label completenessLabel;
	private SelectionListener typeComboListener;
	private Combo versionCombo;
	private Text runtimeText;
	private Scale rateScale;
	private Text rateText;
	private Text genreText;
	private Text countryText;
	private Text languageText;
	private Button seenCheck;
	private MouseListener imageClickListener;
	private Button letterboxdButton;
	private Image mainTabIcon;
    private MovieDialog dialog;
	private final static int COVER_WIDTH = Math.round(200 * MainWindow.DPI_SCALE);
	private final static int COVER_HEIGHT = Math.round(300 * MainWindow.DPI_SCALE);

    public MainTab(MovieDialog parentDialog, int iconSize) {
        dialog = parentDialog;
        mainTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_MAINTAB).createImage(), iconSize, iconSize);
    }

    public void createTabArea(CTabFolder tabFolder) {
		CTabItem tab1 = new CTabItem(tabFolder, SWT.NULL);
		tab1.setText("Main   ");
		tab1.setImage(mainTabIcon);
		
		Composite c1 = new Composite(tabFolder, SWT.NULL);
		
		GridLayout gridLayout = new GridLayout(6, false);
		gridLayout.marginHeight = MovieDialog.MARGIN_HEIGHT;
		gridLayout.marginWidth = MovieDialog.MARGIN_WIDTH;
		gridLayout.verticalSpacing = MovieDialog.VERTICAL_SPACING;
		gridLayout.horizontalSpacing = MovieDialog.HORIZONTAL_SPACING;
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
		typeCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 4, 1));
		typeCombo.setItems(MovieType.getStringArray());
		typeCombo.select(0);
		typeCombo.setVisibleItemCount(MovieType.getStringArray().length);
		
		completenessLabel = new Label(c1, SWT.CENTER);
		completenessLabel.setText("Complete:");
		completenessCombo = new Combo(c1, SWT.DROP_DOWN|SWT.READ_ONLY);
		completenessCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		completenessCombo.setItems(Completeness.getStringArray());
		completenessCombo.select(0);
		completenessCombo.setVisibleItemCount(Completeness.getStringArray().length); //make all items visible
		completenessText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		completenessText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label titleLabel = new Label(c1, SWT.CENTER);
		titleLabel.setText("Title:");
		titleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		titleText.setToolTipText("The title of the movie. If this field is changed, it will be overwritten " +
				"on the next IMDb update. If you want to use a custom title, pleas use the \"display title\" field.");
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Label altTitleLabel = new Label(c1, SWT.CENTER);
		altTitleLabel.setText("Display title:");
		altTitleText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		altTitleText.setToolTipText("This is the title that will be shown in the movie list. If empty, " +
				"the main title will be show instead. The display title will not be overwritten on IMDb updates.");
		altTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		Label versionLabel = new Label(c1, SWT.CENTER);
		versionLabel.setText("Version:");
		versionCombo = new Combo(c1, SWT.DROP_DOWN);
		versionCombo.setToolTipText("");
		versionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		versionCombo.setItems(new String[]{"Director's Cut", "Unrated", "Extended version", "Theatrical version"});
		
		Label yearLabel = new Label(c1, SWT.CENTER);
		yearLabel.setText("Year:");
		yearText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		yearText.setTextLimit(4);
		yearText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,1, 1));
		
		year2Text = new Text(c1, SWT.SINGLE|SWT.BORDER);
		year2Text.setTextLimit(4);
		year2Text.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,3, 1));
		
		Label genreLabel = new Label(c1, SWT.CENTER);
		genreLabel.setText("Genre:");
		genreText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		genreText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		Label countryLabel = new Label(c1, SWT.CENTER);
		countryLabel.setText("Country:");
		countryText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		countryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		Label languageLabel = new Label(c1, SWT.CENTER);
		languageLabel.setText("Language:");
		languageText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		languageText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		Label runtimeLabel = new Label(c1, SWT.CENTER);
		runtimeLabel.setText("Runtime:");
		runtimeText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		runtimeText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 4, 1));
		runtimeText.setToolTipText("This field will be overwritten by IMDb updates!");
		
		Label rateLabel = new Label(c1, SWT.CENTER);
		rateLabel.setText("IMDb rating:");
		rateScale = new Scale(c1, SWT.HORIZONTAL);
		rateScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
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
		seenLabel.setText("");
		seenCheck = new Button(c1, SWT.CHECK);
		seenCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,4, 1));
		seenCheck.setText("Have seen it");
		
		Label imdbLabel = new Label(c1, SWT.CENTER);
		imdbLabel.setText("IMDb address:");
		imdbText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		imdbText.setToolTipText("If a valid IMDb URL is present, you won't have to select the correct movie " +
				"when running IMDb updates.");
		imdbText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		Button imdbGotoButton = new Button(c1, SWT.PUSH);
		imdbGotoButton.setText("Open");
		imdbGotoButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!imdbText.getText().isEmpty())
					MainWindow.getMainWindow().launchBrowser(imdbText.getText());
			}
		});
		
		Label tmdbLabel = new Label(c1, SWT.CENTER);
		tmdbLabel.setText("TMDB address:");
		tmdbText = new Text(c1, SWT.SINGLE|SWT.BORDER);
		tmdbText.setToolTipText("If a valid TMDB URL is present, you won't have to select the correct movie " +
				"when running TMDB updates.");
		tmdbText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		Button tmdbGotoButton = new Button(c1, SWT.PUSH);
		tmdbGotoButton.setText("Open");
		tmdbGotoButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!tmdbText.getText().isEmpty())
					MainWindow.getMainWindow().launchBrowser(tmdbText.getText());
			}
		});
		letterboxdButton = new Button(c1, SWT.PUSH);
		letterboxdButton.setText("Letterboxd");
		//letterboxdButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		letterboxdButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!tmdbText.getText().isEmpty())
					MainWindow.getMainWindow().launchBrowser("https://letterboxd.com/tmdb/"+dialog.getModel().getTmdbID());
			}
		});
		
//		Disable widgets until their respective functions are implemented.
		genreText.setEditable(false);
		countryText.setEditable(false);
		languageText.setEditable(false);
		
		tab1.setControl(c1);
		//c1.setSize(700, 300);
	}

    public void configureListeners() {
		typeComboListener = new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				if(typeCombo.getSelectionIndex() < 3) {
					completenessText.setVisible(false);
					completenessCombo.setVisible(false);
					completenessLabel.setVisible(false);
				} else {
					completenessText.setVisible(true);
					completenessCombo.setVisible(true);
					completenessLabel.setVisible(true);
				}
			}
		};
		typeCombo.addSelectionListener(typeComboListener);

		imageClickListener = new MouseListener() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
                    AbstractMovie movie = dialog.getModel();
					movie.setImageBytes(null);
					setImageAreaFromModel(movie);
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {}

			@Override
			public void mouseUp(MouseEvent e) {}
		};
		imageArea.addMouseListener(imageClickListener);
    }

	public void setModel(AbstractMovie m) {
		typeCombo.select(MovieType.abstractMovieToInt(m));
		imdbText.setText(m.getImdbUrl());
		tmdbText.setText(m.getTmdbUrl());
		letterboxdButton.setEnabled(m.isTmdbUrlValid() && m.getTmdbType().equals("movie"));
		titleText.setText(m.getTitle());
		altTitleText.setText(m.getCustomTitle());
		if(m.getYear() != 0)
			yearText.setText(m.getYear() + "");
		if (m.hasYear2())
			year2Text.setText(m.getYear2()+"");
		versionCombo.setText(m.getCustomVersion());
        genreText.setText(m.getGenresAsString());
		countryText.setText(m.getCountriesAsString());
		languageText.setText(m.getLanguagesAsString());
		if(m.getRunTime() != 0)
			runtimeText.setText(m.getRunTime() + "");
		rateScale.setSelection(m.getRatingAsInt());
		rateText.setText(m.getRating() + "");
		seenCheck.setSelection(m.isSeen());

		setImageAreaFromModel(m);
		
		if (m instanceof AbstractSeries) {
			AbstractSeries series = (AbstractSeries)m;
			completenessCombo.select(series.getCompleteness().ordinal());
			completenessText.setText(series.getCompletenessDetail());
		}
		
		//Trigger listeners
		typeComboListener.widgetSelected(null);
    }

	private void setImageAreaFromModel(AbstractMovie movie) {
		imageArea.setImage(new Image(Display.getCurrent(), CONST.scaleImage(movie.getImageData(), true, COVER_WIDTH, COVER_HEIGHT)));
	}

    @Override
    public void save(AbstractMovie movie) {
		movie.setImdbID(imdbText.getText());
		movie.setTmdbID(tmdbText.getText());
		movie.setTitle(titleText.getText());
		movie.setCustomTitle(altTitleText.getText());
		movie.setYear(yearText.getText());
		movie.setYear2(year2Text.getText());
		movie.setRunTime(runtimeText.getText());
		movie.setRatingAsInt(rateScale.getSelection());
		movie.setSeen(seenCheck.getSelection());
		movie.setCustomVersion(versionCombo.getText());

		if (movie instanceof AbstractSeries) {
			AbstractSeries series = (AbstractSeries)movie;
			series.setCompleteness(Completeness.values()[completenessCombo.getSelectionIndex()]);
			series.setCompletenessDetail(completenessText.getText());
		}
    }

    @Override
    public void dispose() {
		if(imageArea.getImage() != null)
			imageArea.getImage().dispose();
		mainTabIcon.dispose();
    }

    public String getTitleString() {
        return titleText.getText();
    }

    public int getTypeSelectionIndex() {
        return typeCombo.getSelectionIndex();
    }

    public String getTypeComboItem() {
        return typeCombo.getItem(getTypeSelectionIndex());
    }
}
