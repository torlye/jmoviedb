package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.SQLWizard;

public class CustomSQLaction extends Action {
	
	private WizardDialog wd;
	private SQLWizard wizard;
	private CustomSQLquery runnable;

	public CustomSQLaction() {
		setText("Custom SQL Query...");
		setToolTipText("Run a custom SQL Query");
		setImageDescriptor(ImageDescriptor.createFromURL(CONST.ICON_DATABASE_EDIT));
	}

	public void run() {
		wizard = new SQLWizard();
		runnable = new CustomSQLquery(wizard);
		wd = new SQLWizardDialog(MainWindow.getMainWindow().getShell(), wizard);
		wd.open();
	}
	
	private class SQLWizardDialog extends WizardDialog {
		private Composite pageContainer;
		
		public SQLWizardDialog(Shell parentShell, IWizard newWizard) {
			super(parentShell, newWizard);
			setShellStyle(SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL|getDefaultOrientation()|SWT.RESIZE);
		}
		
		public Composite getPageContainer() {
			return pageContainer;
		}
		
		@Override
		protected void nextPressed() {
			try {
				runnable.setQuery(wizard.getQuery());
				wd.run(true, false, runnable);
				wizard.setCanFinish(true);
				super.nextPressed();
			} catch (InvocationTargetException e) {
				if(e.getCause() instanceof SQLException)
					MessageDialog.openError(this.getShell(), "SQL Error", "SQL Error\n" +
							e.getCause().getMessage());
				else
					MainWindow.getMainWindow().handleException(e.getCause());
			} catch (InterruptedException e) {/*Should never happen*/}
		}
		
		@Override
		protected void backPressed() {
			wizard.setCanFinish(false);
			super.backPressed();
		}
	}
}
