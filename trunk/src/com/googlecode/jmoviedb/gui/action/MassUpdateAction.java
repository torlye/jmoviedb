package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.gui.ImdbWizard;
import com.googlecode.jmoviedb.gui.MainWindow;

public class MassUpdateAction extends Action {
	
	private ImdbWizardDialog wd;
	private boolean hasRun;
	private static final int MIN_DIALOG_WIDTH = 350;
	
	public MassUpdateAction() {
		setText("Update all movies");
		setToolTipText("Update all movies with information from IMDb");
		setImageDescriptor(null);
	}
	
	ImdbWizard wizard = new ImdbWizard();
	public void run() {
		hasRun = false;
		wd = new ImdbWizardDialog(MainWindow.getMainWindow().getShell(), wizard);
//		wd.configureShell()
//		wd.setPageSize(450, SWT.DEFAULT);
//				wd.setPageSize(500, SWT.DEFAULT);
		wd.addPageChangedListener(new IPageChangedListener() {
			public void pageChanged(PageChangedEvent evt) {
				for(IWizardPage page : wizard.getPages())
//					page.getControl().setSize(page.getControl().computeSize(500, SWT.DEFAULT));
				
//				wd.setShellWidth(500);
//				wd.getShell().setSize(500, wd.getShell().getSize().y);
				try {
					if(wd.getCurrentPage().getTitle().equals("Downloading...") && hasRun==false) {
						wd.run(true, true, new Foo());
						hasRun = true;
					}
//					System.out.println("shell bounds "+wizard.getShell().getBounds());
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
//		wd.initializeDialogUnits(wd.get);
//		System.out.println("page size blir "+wd.convertHorizontalDLUsToPixels(MIN_DIALOG_WIDTH));
//		wd.setPageSize(400, SWT.DEFAULT);
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
