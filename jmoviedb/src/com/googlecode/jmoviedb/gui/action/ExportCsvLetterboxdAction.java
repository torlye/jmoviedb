package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import com.googlecode.jmoviedb.gui.MainWindow;

public class ExportCsvLetterboxdAction extends Action {
	public ExportCsvLetterboxdAction() {
		setText("Export CSV watched list for Letterboxd");
	}
	
	public void run() {
		String filePath = selectFile();
				
		if(filePath != null) {
			try {
				//Create and run a dialog with a progress monitor
				CSVexportLetterboxd exportWorker = new CSVexportLetterboxd(filePath);
				new ProgressMonitorDialog(MainWindow.getMainWindow().getShell()).run(false, false, exportWorker);
				
				// Display message box to show how the operation went
				MessageBox messageBox = new MessageBox(MainWindow.getMainWindow().getShell(), SWT.ICON_INFORMATION | SWT.OK);
				messageBox.setMessage(exportWorker.getNumberOfReadRecords() + " records read successfully, " + exportWorker.getNumberOfSkippedRecords() + " were skipped");
				messageBox.open();
				
				exportWorker = null;
				
			} catch (InvocationTargetException e) {
				MainWindow.getMainWindow().handleException(e);
			} catch (InterruptedException e) {
				//The cancel button was pressed.
			}
		}
	}
	
	private String selectFile() {
		FileDialog dialog = new FileDialog(MainWindow.getMainWindow().getShell(), SWT.SAVE);
		dialog.setText("Export CSV file");
        String[] fileExtensions = {"*.csv"};
        dialog.setFilterExtensions(fileExtensions);
        String path = dialog.open();
        return path;
	}
}
