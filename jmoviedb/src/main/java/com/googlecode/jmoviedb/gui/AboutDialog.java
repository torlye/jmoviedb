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
import com.googlecode.jmoviedb.Settings;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class AboutDialog extends MessageDialog implements SelectionListener {
	
	public AboutDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage, int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
	}

	/**
	 * Creates all the widgets to be drawn on the dialog box.
	 * @return a Composite containing the widgets.
	 */
	protected Control createCustomArea(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c.setLayout (layout);
		
//		GridData grabHorizontal = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		GridData grabHorizontal = new GridData(SWT.FILL, SWT.CENTER, true, false);

		Label progName = new Label(c, SWT.NONE);
		progName.setText("JMoviedb\n" + CONST.MAJOR_VERSION + "." + CONST.MINOR_VERSION + 
				"." + CONST.RELEASE_VERSION + " " + CONST.DEVELOPMENT_STAGE);
		
		Link progLink = new Link(c, SWT.NONE);
		progLink.setText("<A>" + CONST.WEBSITE + "</A>");
		progLink.addSelectionListener(this);
		
		Label h1 = new Label(c, SWT.SEPARATOR | SWT.HORIZONTAL);
		h1.setLayoutData(grabHorizontal);
		
		Label l1 = new Label(c, SWT.NONE);
		l1.setText("JMoviedb uses the following software:");
		
		Link swtLink = new Link(c, SWT.NONE);
		swtLink.setText("<A>Eclipse SDK " + CONST.VERSION_SWT + "</A>");
		swtLink.addSelectionListener(this);
		
		Link derbyLink = new Link(c, SWT.NONE);
		derbyLink.setText("<A>Apache Derby " + CONST.VERSION_DERBY + "</A>");
		derbyLink.addSelectionListener(this);
		
		Link glazedLink = new Link(c, SWT.NONE);
		glazedLink.setText("<A>Glazed Lists " + CONST.VERSION_GLAZED + "</A>");
		glazedLink.addSelectionListener(this);
		
		Link ktableLink = new Link(c, SWT.NONE);
		ktableLink.setText("<A>KTable " + CONST.VERSION_KTABLE + "</A>");
		ktableLink.addSelectionListener(this);
		
		Link browserLink = new Link(c, SWT.NONE);
		browserLink.setText("<A>BrowserLauncher2 " + CONST.VERSION_BROWSERLAUNCHER + "</A>");
		browserLink.addSelectionListener(this);
		
		Link csvLink = new Link(c, SWT.NONE);
		csvLink.setText("<A>Java CSV " + CONST.VERSION_CSV + "</A>");
		csvLink.addSelectionListener(this);
		
		Label h2 = new Label(c, SWT.SEPARATOR | SWT.HORIZONTAL);
		h2.setLayoutData(grabHorizontal);
		
		Label l2 = new Label(c, SWT.NONE);
		l2.setText("JMoviedb uses the following works of art:");
		
		Link droplineLink = new Link(c, SWT.NONE);
		droplineLink.setText("<A>Dropline Nuovo icons</A>");
		droplineLink.addSelectionListener(this);
		
		Link silkLink = new Link(c, SWT.NONE);
		silkLink.setText("<A>Silk icons</A>");
		silkLink.addSelectionListener(this);
		
		Label h3 = new Label(c, SWT.SEPARATOR | SWT.HORIZONTAL);
		h3.setLayoutData(grabHorizontal);
		
		Label homeDirLabel0 = new Label(c, SWT.NONE);
		homeDirLabel0.setText("Settings file:");
		Label homeDirLabel1 = new Label(c, SWT.NONE);
		homeDirLabel1.setText(Settings.getSettings().getSettingsFile());
		
		Label tempDirLabel0 = new Label(c, SWT.NONE);
		tempDirLabel0.setText("Directory for temporary files:");
		Label tempDirLabel1 = new Label(c, SWT.NONE);
		tempDirLabel1.setText(System.getProperty("java.io.tmpdir"));
		
		Label osLabel0 = new Label(c, SWT.NONE);
		osLabel0.setText("Operating system: "
				+ System.getProperty("os.name") + " "
				+ System.getProperty("os.version") + " "
				+ System.getProperty("os.arch"));
		
		Label javaVendorLabel0 = new Label(c, SWT.NONE);
		javaVendorLabel0.setText("Java VM vendor: " + System.getProperty("java.vendor"));
		
		Label javaVersionLabel0 = new Label(c, SWT.NONE);
		javaVersionLabel0.setText("Java VM version: " + System.getProperty("java.version"));
		
		c.layout();
		c.pack();
		
		return c;
	}

	/**
	 * Not used
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		//do nothing
	}

	/**
	 * Is called when the user selects a URL. The URL is passed to BrowserLauncher,
	 * which will open the website in a new browser window or tab.
	 */
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().toString().contains("moviedb"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE);
		else if(e.getSource().toString().contains("Eclipse"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_SWT);
		else if(e.getSource().toString().contains("Apache"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_DERBY);
		else if(e.getSource().toString().contains("Glazed"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_GLAZED);
		else if(e.getSource().toString().contains("BrowserLauncher2"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_BROWSERLAUNCHER);
		else if(e.getSource().toString().contains("CSV"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_CSV);
		else if(e.getSource().toString().contains("KTable"))
			MainWindow.getMainWindow().launchBrowser(CONST.WEBSITE_KTABLE);
		else if(e.getSource().toString().contains("Dropline"))
			MainWindow.getMainWindow().launchBrowser("http://www.silvestre.com.ar/?p=5");
		else if(e.getSource().toString().contains("Silk"))
			MainWindow.getMainWindow().launchBrowser("http://famfamfam.com/");
	}
}
