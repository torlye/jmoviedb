package com.googlecode.jmoviedb.gui.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.gui.ImdbWizard;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.net.ImdbWorker;

public class MassUpdateAction extends Action {
	
	private ImdbWizardDialog wd;
	private boolean hasRun;
	private ImdbWizard wizard;
	
	/**
	 * Default constructor
	 */
	public MassUpdateAction() {
		setText("Update all movies");
		setToolTipText("Update all movies with information from IMDb");
		setImageDescriptor(null);
	}
	
	public void run() {
		wizard = new ImdbWizard();
		hasRun = false;
		wd = new ImdbWizardDialog(MainWindow.getMainWindow().getShell(), wizard);
		wd.addPageChangedListener(new IPageChangedListener() {
			public void pageChanged(PageChangedEvent evt) {
				try {
					if(wd.getCurrentPage().getTitle().equals("Downloading...") && hasRun==false) {
						ImdbWorker w = new ImdbWorker();
						wd.run(true, true, w.new ImdbMultiDownloader(wd.getShell(), wizard.getKeepTitles(), wizard.getSkipSearching(), wizard.getSkipUpdatedMovies()));
						hasRun = true;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						MainWindow.getMainWindow().getDB().updateModel();
					} catch (Exception e) {
						MainWindow.getMainWindow().handleException(e);
					}					
				}
			}
		});
		wd.open();
		wd = null;
	}
	
	
	public class ImdbWizardDialog extends WizardDialog {
		private Composite pageContainer;
		
		public ImdbWizardDialog(Shell parentShell, IWizard newWizard) {
			super(parentShell, newWizard);
			setShellStyle(SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL|getDefaultOrientation());
		}
				
		public Composite getPageContainer() {
			return pageContainer;
		}
	}
	

	private class Foo implements IRunnableWithProgress {
		public void run(IProgressMonitor arg0)
				throws InvocationTargetException, InterruptedException {
			arg0.beginTask("Running the amazing infinite loop", IProgressMonitor.UNKNOWN);
			while(!arg0.isCanceled()) {}
		}
	}
}
