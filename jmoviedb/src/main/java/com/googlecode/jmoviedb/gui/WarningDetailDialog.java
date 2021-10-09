package com.googlecode.jmoviedb.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Displays a MessageDialog containing information and a stack trace.
 * @author Tor
 *
 */
public class WarningDetailDialog extends MessageDialog {	
	String longMessage;

	public WarningDetailDialog(Shell parentShell, String dialogTitle, String dialogMessage, String longMessage) {
		super(parentShell, dialogTitle, null, dialogMessage, MessageDialog.WARNING, new String[] {"OK"}, 0);
		this.longMessage = longMessage;
	}
	
	protected Control createCustomArea(Composite parent) {
		Text text = new Text(parent, SWT.MULTI|SWT.READ_ONLY|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		
		text.setText(longMessage);
		return text;
	}
}