package com.googlecode.jmoviedb;

import java.io.IOException;

// import com.googlecode.jmoviedb.Settings;
import com.googlecode.jmoviedb.gui.MainWindow;

public class Main {
	// private static Settings settings;

	/**
	 * The main method that launches the application.
	 * @param args - not currently used
	 */
	public static void main(String[] args) {
		for(String s : args)
			System.out.println("CmdLineArg: "+s);
		
		//Disable creation of the derby.log file
		System.setProperty("derby.stream.error.method", 
				"MainWindow.derbyNullLogger");
		
		// Settings settings = Settings.getSettings();
		
		MainWindow m = new MainWindow(args);

		/*MessageDialog.openInformation(m.getShell(), "Warning", 
				"This is a very early test version of JMoviedb. " +
				"It has several errors and missing features. " +
				"Use it for testing only!");*/
		m.run();
		
		
		/**
		 * More magic
		 */
		new org.eclipse.jface.wizard.Wizard() {
			public boolean performFinish() {
				new org.eclipse.jface.dialogs.TitleAreaDialog(null);
				new org.eclipse.jface.preference.PreferenceDialog(null, null);
				return false;
			}
		};
	}
	
	public static java.io.OutputStream derbyNullLogger(){
	     return new java.io.OutputStream() {
	         public void write(int b) throws IOException {
	             // Ignore all log messages
	         }
	     };
	}
}
