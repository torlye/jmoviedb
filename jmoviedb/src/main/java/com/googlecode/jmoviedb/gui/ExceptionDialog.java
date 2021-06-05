package com.googlecode.jmoviedb.gui;

import com.googlecode.jmoviedb.CONST;

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
public class ExceptionDialog extends MessageDialog {	
	private Throwable exception;

	public ExceptionDialog(Shell parentShell, String dialogTitle, String dialogMessage, Throwable t) {
		super(parentShell, dialogTitle, null, dialogMessage, MessageDialog.ERROR, new String[]{"OK"}, 0);
		exception = t;
	}
	
	protected Control createCustomArea(Composite parent) {
		Text text = new Text(parent, SWT.MULTI|SWT.READ_ONLY|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);

		String message = "JMoviedb "
				+CONST.MAJOR_VERSION+"."+CONST.MINOR_VERSION+"."+CONST.RELEASE_VERSION+" "+CONST.DEVELOPMENT_STAGE+"\n"+ 
				"Operating system: "+System.getProperty("os.name")+" "+System.getProperty("os.version")+" "+System.getProperty("os.arch")+"\n"+
				"Java VM: "+System.getProperty("java.vendor")+" "+System.getProperty("java.version")+"\n\n"
				+exception.getClass().toString()+"\n"+exception.getMessage()+"\n\n";
		StackTraceElement[] stackTrace = exception.getStackTrace();
		for(StackTraceElement el : stackTrace) {
			message += el.toString() + "\n";
		}
		
		text.setText(message);
		return text;
	}
}