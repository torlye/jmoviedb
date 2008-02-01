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
import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.enumerated.MovieType;
import com.googlecode.jmoviedb.net.ImdbSearchResult;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;

public class SearchResultDialog extends Dialog implements Listener, SelectionListener {

	private ImdbSearchResult[] resultSet;
	private Composite container;
	private Composite contents;
	private ScrollBar hBar;
	private ScrollBar vBar;
	private Button[] buttons;
	private ArrayList<Image> imageList = new ArrayList<Image>();
	
	public SearchResultDialog(Shell parentShell, ImdbSearchResult[] resultSet) {
		super(parentShell);
		
		this.resultSet = resultSet;
		buttons = new Button[resultSet.length];
		
		//if the dialog is killed, the return code should be -1 
		setReturnCode(-1);
	}
	
	protected Control createDialogArea(Composite parent) {
		container = new Composite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		GridLayout containerLayout = new GridLayout();
		containerLayout.marginHeight = 0;
		containerLayout.marginWidth = 0;
		containerLayout.verticalSpacing = 0;
		containerLayout.horizontalSpacing = 0;
		container.setLayout(containerLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		hBar = container.getHorizontalBar ();
		hBar.addListener(SWT.Selection, this);
		vBar = container.getVerticalBar ();
		vBar.addListener(SWT.Selection, this);
		getShell().addListener (SWT.Resize, this);
		
		contents = new Composite(container, SWT.NONE);

		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 7;
		gridLayout.marginWidth = 7;
		gridLayout.verticalSpacing = 7;
		gridLayout.horizontalSpacing = 7;
		contents.setLayout(gridLayout);
		
		GridData radioLayout = new GridData();
		radioLayout.verticalSpan = 1;
		radioLayout.horizontalAlignment = SWT.CENTER;
		radioLayout.verticalAlignment = SWT.CENTER;
		
		for(int i = 0; i < resultSet.length; i++) {
			Button radio = new Button(contents, SWT.RADIO);
			radio.setLayoutData(radioLayout);
			if(i == 0)
				radio.setSelection(true);
			
			GridData imageLayout = new GridData();
			imageLayout.verticalSpan = 1;
//			imageLayout.heightHint = 32;
//			imageLayout.widthHint = 50;
			imageLayout.horizontalAlignment = SWT.CENTER;
			imageLayout.verticalAlignment = SWT.CENTER;
			
			Label imageLabel = new Label(contents, SWT.CENTER);
			imageLabel.setLayoutData(imageLayout);
			ImageData imgData = null;
			try {
				imgData = resultSet[i].getImageData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(imgData != null) {
				Image img = new Image(Display.getCurrent(), imgData);
				imageLabel.setImage(img);
				//add image to a private list so that it can be disposed later
				imageList.add(img);
				if(CONST.DEBUG_MODE)
					System.out.println("Image: " + img.getImageData().width + "x" + img.getImageData().height);
			}
			
			GridData textLayout = new GridData();
			textLayout.grabExcessHorizontalSpace = true;
			textLayout.horizontalAlignment = SWT.FILL;
			textLayout.verticalAlignment = SWT.CENTER;
//			textLayout.widthHint = 500;
//			textLayout.heightHint = 50;
			
				Link title = new Link(contents, SWT.NONE);
				title.setLayoutData(textLayout);
				title.setText("<A HREF=\"" + Settings.getSettings().getImdbUrl() + resultSet[i].getImdbId() + "\">" + resultSet[i].getTitle() + " (" + resultSet[i].getYear() + ")</A>");
				title.addSelectionListener(this);
				
				if(resultSet[i].getType() != MovieType.film)
					title.setText(title.getText() + " - " + resultSet[i].getType().toString());
				
				String akaString = "";
				if(resultSet[i].getAltTitles() != null) {
					int j=0;
					for(String t : resultSet[i].getAltTitles()) {
						akaString += "\naka " + t;
						j++;
						System.out.println(j + " alt title(s)");
					}
					title.setText(title.getText() + akaString); 
				}
			
			
			buttons[i] = radio;
		}//end for
		
		container.setSize(contents.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		return contents;
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("IMDb search results");
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
	}
	
	protected void buttonPressed(int buttonId) {
		if(buttonId == IDialogConstants.OK_ID)
			setReturnCode(getSelectedItem());
		close();
	}
	
	private int getSelectedItem() {
		for(int i=0; i<buttons.length; i++) {
			if(buttons[i].getSelection()) {
				return i;
			}
		}
		return -1;
	}

	public void handleEvent(Event event) {
		if(event.type == SWT.Selection) {
			Point location = contents.getLocation();
			location.x = -hBar.getSelection();
			location.y = -vBar.getSelection();
			System.out.println(location);
			contents.setLocation(location);
		}
		else if(event.type == SWT.Resize) {
			Point contentSize = contents.getSize();
			Rectangle rectangle = container.getClientArea();
			hBar.setMaximum(contentSize.x);
			vBar.setMaximum(contentSize.y);
			hBar.setThumb (Math.min (contentSize.x, rectangle.width));
			vBar.setThumb (Math.min (contentSize.y, rectangle.height));
			int hPage = contentSize.x - rectangle.width;
			int vPage = contentSize.y - rectangle.height;
			int hSelection = hBar.getSelection();
			int vSelection = vBar.getSelection();
			Point location = contents.getLocation ();
			System.out.println("old "+location);
			if (hSelection >= hPage) {
				if (hPage <= 0) hSelection = 0;
				location.x = -hSelection;
			}
			if (vSelection >= vPage) {
				if (vPage <= 0) vSelection = 0;
				location.y = -vSelection;
			}
			System.out.println("new "+location);
			contents.setLocation (location);
			System.out.println("after set "+contents.getLocation());
		}
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
		System.out.println(e.text);
		MainWindow.getMainWindow().launchBrowser(e.text);
	}
	
	/**
	 * Dispose images in order to release their resources
	 */
	public void dispose() {
		for(Image img : imageList)
			img.dispose();
	}
}
