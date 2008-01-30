package com.googlecode.jmoviedb.gui;

import java.awt.Checkbox;

import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ImdbWizard extends Wizard{
	
	public ImdbWizard() {
		super();
		setWindowTitle("IMDb import wizard");
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		addPage(new WizardPage1());
		addPage(new WizardPage2());
		addPage(new WizardPage3());
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
	
	private class WizardPage1 extends WizardPage {
		public WizardPage1() {
			super("IMDB download introduction");
			setTitle(getName());
			setPageComplete(true);
		}
		public void createControl(Composite parent) {
			Composite c =  new Composite(parent, SWT.NULL);
			GridLayout gl = new GridLayout();
			c.setLayout(gl);
			Label l1 = new Label(c, SWT.NONE);
			l1.setText("Some text here");
			
			setControl(c);
		}
	}
	
	private class WizardPage2 extends WizardPage {
		public WizardPage2() {
			super("Preferences");
			setTitle(getName());
			setPageComplete(true);
		}
		public void createControl(Composite parent) {
			Composite c =  new Composite(parent, SWT.NULL);
			GridLayout gl = new GridLayout();
			gl.numColumns = 2;
			c.setLayout(gl);
			
			GridData gd1 = new GridData();
			gd1.grabExcessHorizontalSpace = true;
			gd1.grabExcessVerticalSpace = true;
			gd1.horizontalSpan=2;
			Label l1 = new Label(c, SWT.NONE);
			l1.setText("Can I Has Cheezburger?");
			l1.setLayoutData(gd1);
			
			Button check1 = new Button(c, SWT.CHECK);
			Label l2 = new Label(c, SWT.NONE);
			l2.setText("Check1");
			Label l4 = new Label(c, SWT.NONE);
			l4.setText("Check1 details");
			l4.setLayoutData(gd1);
			
			
			Button check2 = new Button(c, SWT.CHECK);
			Label l3 = new Label(c, SWT.NONE);
			l3.setText("Check2");
			Label l5 = new Label(c, SWT.NONE);
			l5.setText("Check1 details");
			l5.setLayoutData(gd1);
			
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
		}
	}
}
