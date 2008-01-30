package com.googlecode.jmoviedb.gui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;

import com.googlecode.jmoviedb.gui.ImdbWizard;
import com.googlecode.jmoviedb.gui.MainWindow;

public class MassUpdateAction extends Action {
	
	private WizardDialog wd;
	
	public MassUpdateAction() {
		setText("Update all movies");
		setToolTipText("Update all movies with information from IMDb");
		setImageDescriptor(null);
	}
	
	public void run() {
		ImdbWizard wizard = new ImdbWizard();
		wd = new WizardDialog(MainWindow.getMainWindow().getShell(), wizard);
		wd.addPageChangedListener(new IPageChangedListener() {
			public void pageChanged(PageChangedEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(((IWizardPage)(evt.getSelectedPage())).getName());
			}
		});
		wd.open();
		
		wizard.dispose();
		wd = null;
	}

}
