package com.googlecode.jmoviedb.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ExceptionDialog extends MessageDialog {
	
	Throwable exception;

	public ExceptionDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage, int dialogImageType, String[] dialogButtonLabels, int defaultIndex, Throwable t) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels, defaultIndex);
		exception = t;
	}
	
	protected Control createCustomArea(Composite parent) {
		Text text = new Text(parent, SWT.MULTI|SWT.READ_ONLY|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);

		String message = exception.getClass().toString() + "\n\n" + exception.getMessage() + "\n\n";
		StackTraceElement[] stackTrace = exception.getStackTrace();
		for(StackTraceElement el : stackTrace) {
			message += el.toString() + "\n";
		}
		
		text.setText(message);
		return text;
	}

}
