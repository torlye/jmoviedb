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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.AspectRatio;
import com.googlecode.jmoviedb.enumerated.ColorFormat;
import com.googlecode.jmoviedb.enumerated.ContainerFormat;
import com.googlecode.jmoviedb.enumerated.DiscType;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.enumerated.Resolution;
import com.googlecode.jmoviedb.enumerated.TVsystem;
import com.googlecode.jmoviedb.enumerated.VideoCodec;
import com.googlecode.jmoviedb.gui.audiosubtitletable.AudioSubtitleTable;
import com.googlecode.jmoviedb.gui.audiosubtitletable.AudioTable;
import com.googlecode.jmoviedb.gui.audiosubtitletable.SubtitleTable;
import com.googlecode.jmoviedb.gui.moviedialog.ActorsTab;
import com.googlecode.jmoviedb.gui.moviedialog.IMovieDialogTab;
import com.googlecode.jmoviedb.gui.moviedialog.MainTab;
import com.googlecode.jmoviedb.gui.moviedialog.TaglinePlotTab;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.SubtitleTrack;
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
	
	
	private Combo colour;
	//private Text versionText;
	private Button legalCheck;
	private Combo discCombo;
	private Text locationText;
//	private Button myEncodeCheck;
	private Text sceneNameText;
	private Combo aspectCombo;
//	private Text url1Text;
	private Text url2Text;
	
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
	private AudioSubtitleTable<AudioTrack> audioTable;
	private AudioSubtitleTable<SubtitleTrack> subtitleTable;
	
	public static final int MARGIN_WIDTH = 7;
	public static final int MARGIN_HEIGHT = 7;
	public static final int VERTICAL_SPACING = 5;
	public static final int HORIZONTAL_SPACING = 6;
	
	private Image formatTabIcon;
	private Image audioTabIcon;
	private Label regionLabel;
	private SelectionListener formatComboListener;
	private SelectionListener legalCheckListener;
//	private SelectionListener myEncodeCheckListener;
	private SelectionListener r0CheckListener;
	private MainTab mainTab;
	private IMovieDialogTab taglineTab;
	private IMovieDialogTab actorsTab;
	
	public MovieDialog(AbstractMovie movie) {
		super(MainWindow.getMainWindow());
		this.setShellStyle(SWT.CLOSE|SWT.RESIZE);
		
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		mainTab = new MainTab(this, iconSize);
		taglineTab = new TaglinePlotTab(iconSize);
		actorsTab = new ActorsTab(iconSize);
		
		formatTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_FORMATTAB).createImage(), iconSize, iconSize);
		audioTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_AUDIOSUBTAB).createImage(), iconSize, iconSize);
		
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
		formatVideoTab(tabFolder);
		audioSubtitleTab(tabFolder);

		tabFolder.setSelection(0); //select the first tab
		
		configureListeners();

		return c;
	}
	
	private void configureListeners() {
		mainTab.configureListeners();
		taglineTab.configureListeners();
		actorsTab.configureListeners();

		formatComboListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				//Reset
				videoCodecCombo.setEnabled(true);
				containerCombo.setEnabled(true);
				resolutionCombo.setEnabled(true);
				discCombo.setEnabled(true);
				regionLabel.setVisible(false);
				r0.setVisible(false);
				r1.setVisible(false);
				r2.setVisible(false);
				r3.setVisible(false);
				r4.setVisible(false);
				r5.setVisible(false);
				r6.setVisible(false);
				r7.setVisible(false);
				r8.setVisible(false);
				
				FormatType selectedValue = FormatType.values()[formatCombo.getSelectionIndex()];
				
				int selectedColor = colour.getSelectionIndex();
				colour.setItems(
					selectedValue == FormatType.other || selectedValue == FormatType.file || selectedValue == FormatType.uhdbluray 
					? ColorFormat.getAllFormatsStringArray() : ColorFormat.getSDRFormatsStringArray());
				if (selectedColor < colour.getItemCount())
					colour.select(selectedColor);
				else
					colour.select(0);
				
				if(selectedValue == FormatType.dvd) {
					videoCodecCombo.select(VideoCodec.mpeg2.ordinal());
					containerCombo.select(ContainerFormat.vob.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.dvd.ordinal());
					
					videoCodecCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r4.setVisible(true);
					r5.setVisible(true);
					r6.setVisible(true);
					r7.setVisible(true);
					r8.setVisible(true);
					r1.setText("R1");
					r2.setText("R2");
					r3.setText("R3");
					
				} else if(selectedValue == FormatType.bluray || selectedValue == FormatType.bluray3d) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					discCombo.select(DiscType.bd.ordinal());
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r1.setText("A");
					r2.setText("B");
					r3.setText("C");
				} else if(selectedValue == FormatType.uhdbluray) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					discCombo.select(DiscType.uhdbd.ordinal());
					
				} else if(selectedValue == FormatType.hddvd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					discCombo.select(DiscType.hddvd.ordinal());
					containerCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.avchd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					videoCodecCombo.select(VideoCodec.h264.ordinal());
					videoCodecCombo.setEnabled(false);
					if (discCombo.getSelectionIndex() == 0)
						discCombo.select(DiscType.dvdminusr.ordinal());
				} else if(selectedValue == FormatType.vcd) {
					containerCombo.select(ContainerFormat.mpeg.ordinal());
					videoCodecCombo.select(VideoCodec.mpeg1.ordinal());
					resolutionCombo.select(Resolution.cif.ordinal());
					discCombo.select(DiscType.cd.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
				}  else if(selectedValue == FormatType.svcd) {
					containerCombo.select(ContainerFormat.mpeg.ordinal());
					videoCodecCombo.select(VideoCodec.mpeg2.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.cd.ordinal());
					
					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.laserdisc) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.ld.ordinal());
					
					containerCombo.setEnabled(false);
					videoCodecCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.vhs) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.vhs.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.betamax) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.betamax.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.video8) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.video8.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);

				} else if(selectedValue == FormatType.umd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.h264.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.umd.ordinal());
					
					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r4.setVisible(true);
					r5.setVisible(true);
					r6.setVisible(true);
				}
			}
		};
		formatCombo.addSelectionListener(formatComboListener);
		
		legalCheckListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if(legalCheck.getSelection() == true) {
					sceneNameText.setEnabled(false);
//					myEncodeCheck.setSelection(false);
//					myEncodeCheck.setEnabled(false);
				} else {
					sceneNameText.setEnabled(true);
//					myEncodeCheck.setEnabled(true);
				}
			}
		};
		legalCheck.addSelectionListener(legalCheckListener);
		
//		myEncodeCheckListener = new SelectionListener() {
//			public void widgetDefaultSelected(SelectionEvent e) {}
//			public void widgetSelected(SelectionEvent e) {
//				if(myEncodeCheck.getSelection() == true) {
//					sceneNameText.setText("");
//					sceneNameText.setEnabled(false);
//					legalCheck.setSelection(false);
//					legalCheck.setEnabled(false);
//				} else {
//					sceneNameText.setEnabled(true);
//					legalCheck.setEnabled(true);
//				}
//			}
//		};
//		myEncodeCheck.addSelectionListener(myEncodeCheckListener);
		
		r0CheckListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				boolean value = !r0.getSelection();
				r1.setEnabled(value);
				r2.setEnabled(value);
				r3.setEnabled(value);
				r4.setEnabled(value);
				r5.setEnabled(value);
				r6.setEnabled(value);
				r7.setEnabled(value);
				r8.setEnabled(value);
				if(value == false) {
					r1.setSelection(false);
					r2.setSelection(false);
					r3.setSelection(false);
					r4.setSelection(false);
					r5.setSelection(false);
					r6.setSelection(false);
					r7.setSelection(false);
					r8.setSelection(false);
				}
			}
		};
		r0.addSelectionListener(r0CheckListener);
	}
	
	private void formatVideoTab(CTabFolder tabFolder) {
		CTabItem tab4 = new CTabItem(tabFolder, SWT.NULL);
		tab4.setText("Format and video");
		tab4.setImage(formatTabIcon);
		
		Composite c = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(10, false);
		compositeLayout.makeColumnsEqualWidth = false;
		c.setLayout(compositeLayout);
		
		int comboHorizontalAlignment = SWT.BEGINNING;
		boolean comboGrabExcessHorizontalSpace = true;
		int comboHorizontalSpan = 9;
		
		Label formatLabel = new Label(c, SWT.CENTER);
		formatLabel.setText("Format:");
		formatCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		formatCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		formatCombo.setItems(FormatType.getStringArray());
		formatCombo.select(0);
		formatCombo.setVisibleItemCount(FormatType.getStringArray().length); //make all items visible
		
		Label containerLabel = new Label(c, SWT.CENTER);
		containerLabel.setText("Container format:");
		containerCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		containerCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		containerCombo.setItems(ContainerFormat.getStringArray());
		containerCombo.select(0);
		containerCombo.setVisibleItemCount(ContainerFormat.getStringArray().length); //make all items visible
		
		Label videoCodecLabel = new Label(c, SWT.CENTER);
		videoCodecLabel.setText("Video format:");
		videoCodecCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		videoCodecCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		videoCodecCombo.setItems(VideoCodec.getStringArray());
		videoCodecCombo.select(0);
		videoCodecCombo.setVisibleItemCount(VideoCodec.getStringArray().length); //make all items visible
		
		Label resolutionLabel = new Label(c, SWT.CENTER);
		resolutionLabel.setText("Video resolution:");
		resolutionCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		resolutionCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		resolutionCombo.setItems(Resolution.getStringArray());
		resolutionCombo.select(0);
		resolutionCombo.setVisibleItemCount(Resolution.getStringArray().length); //make all items visible
		
		Label aspectLabel = new Label(c, SWT.CENTER);
		aspectLabel.setText("Aspect ratio");
		aspectCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		aspectCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		aspectCombo.setItems(AspectRatio.getStringArray());
		aspectCombo.select(0);
		aspectCombo.setVisibleItemCount(AspectRatio.getStringArray().length); //make all items visible
		
		Label tvSystemLabel = new Label(c, SWT.CENTER);
		tvSystemLabel.setText("TV system:");
		tvSystemCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		tvSystemCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, false, false, comboHorizontalSpan-4, 1));
		tvSystemCombo.setItems(TVsystem.getStringArray());
		tvSystemCombo.select(0);
		tvSystemCombo.setVisibleItemCount(TVsystem.getStringArray().length); //make all items visible
		
		Label colorLabel = new Label(c, SWT.CENTER);
		colorLabel.setText("Colour");
		colour = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		colour.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, false, false, 3, 1));
		colour.setItems(ColorFormat.getAllFormatsStringArray());
		
		regionLabel = new Label(c, SWT.CENTER);
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
		
		Label discLabel = new Label(c, SWT.CENTER);
		discLabel.setText("Storage medium:");
		discCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		discCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		discCombo.setItems(DiscType.getStringArray());
		discCombo.select(0);
		discCombo.setVisibleItemCount(DiscType.getStringArray().length); //make all items visible
		
		Label locationLabel = new Label(c, SWT.CENTER);
		locationLabel.setText("Storage location:");
		locationText = new Text(c, SWT.SINGLE|SWT.BORDER);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		legalCheck = new Button(c, SWT.CHECK);
		legalCheck.setText("Original");
		legalCheck.setSelection(true);
		legalCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, comboHorizontalSpan+1, 1));
		
//		myEncodeCheck = new Button(c, SWT.CHECK);
//		myEncodeCheck.setText("Encoded by me");
//		myEncodeCheck.setSelection(true);
//		myEncodeCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, comboHorizontalSpan+1, 1));
		
		Label sceneNameLabel = new Label(c, SWT.CENTER);
		sceneNameLabel.setText("Scene release name:");
		sceneNameText = new Text(c, SWT.SINGLE|SWT.BORDER);
		sceneNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
//		Label urlLabel1 = new Label(c, SWT.CENTER);
//		urlLabel1.setText("Discogs URL");
//		url1Text = new Text(c, SWT.SINGLE|SWT.BORDER);
//		url1Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		Label urlLabel2 = new Label(c, SWT.CENTER);
		urlLabel2.setText("blu-ray.com/lddb.com");
		url2Text = new Text(c, SWT.SINGLE|SWT.BORDER);
		url2Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		tab4.setControl(c);
	}
	
	private void audioSubtitleTab(CTabFolder tabFolder) {
		CTabItem tab5 = new CTabItem(tabFolder, SWT.NULL);
		tab5.setText("Audio and subtitles");
		tab5.setImage(audioTabIcon);
		
		Composite c = new Composite(tabFolder, SWT.NULL);
		
//		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
//		parent.setLayoutData(gridData);

		// Set numColumns to 3 for the buttons 
		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = 4;
		c.setLayout(layout);
		
		audioTable = new AudioTable(c, formatCombo);
		subtitleTable = new SubtitleTable(c, formatCombo);
		
		tab5.setControl(c);
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
		
		formatCombo.select(m.getFormat().ordinal());
		containerCombo.select(m.getContainer().ordinal());
		videoCodecCombo.select(m.getVideo().ordinal());
		resolutionCombo.select(m.getResolution().ordinal());
		tvSystemCombo.select(m.getTvSystem().ordinal());
		r0.setSelection(m.getDvdRegion()[0]);
		r1.setSelection(m.getDvdRegion()[1]);
		r2.setSelection(m.getDvdRegion()[2]);
		r3.setSelection(m.getDvdRegion()[3]);
		r4.setSelection(m.getDvdRegion()[4]);
		r5.setSelection(m.getDvdRegion()[5]);
		r6.setSelection(m.getDvdRegion()[6]);
		r7.setSelection(m.getDvdRegion()[7]);
		r8.setSelection(m.getDvdRegion()[8]);
		legalCheck.setSelection(m.isLegal());
		discCombo.select(m.getDisc().ordinal());
		aspectCombo.select(m.getAspectRatio().ordinal());
		locationText.setText(m.getLocation());
//		myEncodeCheck.setSelection(m.isMyEncode());
		sceneNameText.setText(m.getSceneReleaseName());
//		url1Text.setText(m.getUrl1String());
		url2Text.setText(m.getUrl2String());
		
		audioTable.setModel(movie.getAudioTracks());
		subtitleTable.setModel(movie.getSubtitles());
		
		formatComboListener.widgetSelected(null);
		legalCheckListener.widgetSelected(null);
//		myEncodeCheckListener.widgetSelected(null);
		r0CheckListener.widgetSelected(null);

		colour.select(m.getColor().ordinal());
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

		formatTabIcon.dispose();
		audioTabIcon.dispose();
		audioTable.dispose();
		subtitleTable.dispose();
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
		movie.setColor(ColorFormat.values()[colour.getSelectionIndex()]);
		movie.setLegal(legalCheck.getSelection());
		movie.setDisc(DiscType.values()[discCombo.getSelectionIndex()]);
		movie.setAspectRatio(AspectRatio.values()[aspectCombo.getSelectionIndex()]);
		movie.setLocation(locationText.getText());
		movie.setSceneReleaseName(sceneNameText.getText());
//		movie.setUrl1(url1Text.getText());
		movie.setUrl2(url2Text.getText());
//		movie.setMyEncode(myEncodeCheck.getSelection());
		
		movie.setAudioTracks(audioTable.getModel());
		movie.setSubtitles(subtitleTable.getModel());
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

