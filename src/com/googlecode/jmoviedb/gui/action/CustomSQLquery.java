package com.googlecode.jmoviedb.gui.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.SQLWizard;

public class CustomSQLquery implements IRunnableWithProgress {

	private String query;
	private SQLWizard wizard;
	private String[] columnNames;
	private String[][] results;
	
	public CustomSQLquery(SQLWizard wizard) {
		this.wizard = wizard;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Running SQL Query", IProgressMonitor.UNKNOWN);
		try {
			monitor.subTask(" - Querying database...");
			Object sqlResult = MainWindow.getMainWindow().getDB().getDatabase().runCustomQuery(query);
			monitor.subTask(" - Compiling results...");
			
			if(sqlResult instanceof ResultSet) {
				ResultSet rs = (ResultSet) sqlResult;
				
				ResultSetMetaData metaData = rs.getMetaData();
				int[] columnTypes = new int[metaData.getColumnCount()];
				columnNames = new String[metaData.getColumnCount()];
				for (int i = 0; i < columnTypes.length; i++) {
					columnTypes[i] = metaData.getColumnType(i+1);
					columnNames[i] = metaData.getColumnName(i+1);
				}
				ArrayList<String[]> resultsList = new ArrayList<String[]>();
				while(rs.next()) {
					String[] row = new String[columnTypes.length];
					for (int i = 0; i < columnTypes.length; i++) {
						switch (columnTypes[i]) {
						case Types.INTEGER:
							row[i] = rs.getInt(i+1)+"";
							break;
						case Types.SMALLINT:
							row[i] = rs.getInt(i+1)+"";
							break;
						case Types.CHAR:
							row[i] = rs.getString(i+1);
							break;
						case Types.VARCHAR:
							row[i] = rs.getString(i+1);
							break;
						case Types.BLOB:
							row[i] = "[BLOB]";
							break;
						default:
							row[i] = "[Unknown type "+columnTypes[i]+"]";
							break;
						}					
					}
					resultsList.add(row);				
				}
				results = resultsList.toArray(new String[resultsList.size()][]);
				resultsList = null;
				rs.getStatement().close();
				
			} else if(sqlResult instanceof Integer) {
				columnNames = new String[]{"SQL update"};
				results = new String[][]{{(Integer)sqlResult+" rows updated"}};
			} else {
				throw new SQLException("Unknown SQL result");
			}
			
			if(CONST.DEBUG_MODE) System.out.println("Got results: "+results.length+" rows");
			monitor.subTask("Hang on!");
			monitor.done();
			
			/*
			 * When running a simple SQL query that returns a large ResultSet, the following 
			 * part of the job is actually the most time consuming one. Unfortunately, it seems
			 * that if it is run concurrently with the progress monitor animation, it suffers a huge
			 * performance penalty. This is probably because they both must run in the GUI thread,
			 * and the job is constantly being interrupted by the progress animation. Is there a better
			 * way of doing this?
			 */
			MainWindow.getMainWindow().getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					wizard.createColumns(columnNames);
					wizard.setInput(results);
				}
			});
		} catch (SQLException e) {
			monitor.done();
			throw new InvocationTargetException(e);
		}
	}
}
