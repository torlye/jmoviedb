package com.googlecode.jmoviedb.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ImdbWizard extends Wizard {
	
	// Minimum dialog width (in dialog units) (value from class TitleAreaDialog)
	private static final int MIN_DIALOG_WIDTH = 350;
	
	public Label label0;
	public Composite c0;
	int labelWidth;
	
	public ImdbWizard() {
		super();
		setWindowTitle("IMDb import wizard");
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		Composite c = new Composite(getShell(), SWT.NONE);
		GC gc = new GC(c);
		gc.setFont(c.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		c.dispose();
		
		labelWidth = Dialog.convertHorizontalDLUsToPixels(fontMetrics, MIN_DIALOG_WIDTH);
		System.out.println("labelWidth "+labelWidth);

		WizardPage wiz2 = new WizardPage2();
		WizardPage wiz3 = new WizardPage3();
		addPage(wiz2);
		addPage(wiz3);
	}

	public boolean canFinish() {
//		if(this.getContainer().getCurrentPage().get....ImdbWizard.;
		return false;
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean getKeepTitles() {
		return check1.getSelection();
	}
	
	public boolean getSkipSearching() {
		return check2.getSelection();
	}
	
	public boolean getSkipUpdatedMovies() {
		return check3.getSelection();
	}
	
	private Button check1;
	private Button check2;
	private Button check3;
	
	private class WizardPage2 extends WizardPage {
		public WizardPage2() {
			super("Preferences");
			setTitle(getName());
			setPageComplete(true);
		}
		public void createControl(Composite parent) {
			Composite c =  new Composite(parent, SWT.BORDER);
			GridLayout gl = new GridLayout(2, false);
			c.setLayout(gl);
			
			check1 = new Button(c, SWT.CHECK);
			check1.setSelection(true);
			Label l2 = new Label(c, SWT.NONE);
			l2.setText("Keep titles");
			Label l4 = new Label(c, SWT.WRAP);
			l4.setText("If a movie's title is different from the title used at IMDb, your custom title will be copied to the custom title field while the new title will be stored in the title field. This prevents a movie like \"Seven Samurai\" from being renamed to \"Shichinin no samurai\" (IMDb uses the movie's title in the original language). If the box is unchecked, all titles will be overwritten with IMDb's titles.");
			GridData gd1 = new GridData(labelWidth, SWT.DEFAULT);
			gd1.grabExcessVerticalSpace = true;
			gd1.horizontalSpan = 2;
			gd1.verticalAlignment = SWT.TOP;
			l4.setLayoutData(gd1);
			
			
			check2 = new Button(c, SWT.CHECK);
			check2.setSelection(true);
			Label l3 = new Label(c, SWT.NONE);
			l3.setText("Skip movies with no IMDb URL");
			Label l5 = new Label(c, SWT.WRAP);
			l5.setText("If this box is unckecked, JMoviedb will try to search for blah blah blah...");
			GridData gd2 = new GridData(labelWidth, SWT.DEFAULT);
			gd2.grabExcessVerticalSpace = true;
			gd2.horizontalSpan = 2;
			gd2.verticalAlignment = SWT.TOP;
			l5.setLayoutData(gd2);
			
			check3 = new Button(c, SWT.CHECK);
			check3.setSelection(true);
			Label l6 = new Label(c, SWT.NONE);
			l6.setText("Skip movies that have been updated before");
			Label l1 = new Label(c, SWT.WRAP);
			l1.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas lectus. Phasellus eget justo ac dui egestas dictum. Curabitur lacinia mi a magna. Nulla elementum, purus at tempus pellentesque, ante quam tempor nunc, at egestas velit justo ut urna.");
			GridData gd3 = new GridData(labelWidth, SWT.DEFAULT);
			gd3.grabExcessVerticalSpace = true;
			gd3.horizontalSpan = 2;
			gd3.verticalAlignment = SWT.TOP;
			l1.setLayoutData(gd3);
			
			setControl(c);
		}
	}
	
	private class WizardPage3 extends WizardPage {
		public WizardPage3() {
			super("Downloading...");
			setTitle(getName());
			setPageComplete(true);
		}
		public void createControl(Composite parent) {
			Composite c =  new Composite(parent, SWT.NULL);
			GridLayout gl = new GridLayout();
			c.setLayout(gl);
			Label l1 = new Label(c, SWT.NONE);
			l1.setText("lol!");
			
			setControl(c);
			System.out.println(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		}
	}
}
