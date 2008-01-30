package com.googlecode.jmoviedb.gui;

import org.eclipse.swt.SWT;

import ca.odell.glazedlists.swt.AbstractKTableFormat;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class MovieTableFormat extends AbstractKTableFormat {

	public KTableCellEditor getColumnEditor(Object arg0, int arg1) {
		return null;
	}

	public Object getColumnHeaderValue(int headerRow, int column) {
		System.out.println("getColumnHeaderValue "+headerRow+" "+column);
		return null;
	}

	public KTableCellRenderer getColumnRenderer(Object arg0, int arg1) {
		return new MovieTableCellRenderer(SWT.NONE);
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int arg0) {
		return null;
	}

	public Object getColumnValue(Object arg0, int arg1) {
		return arg0;
	}
	
	public int getRowHeightMinimum() {
		return 90;
	}
	
	public int getRowHeight(Object rowObject) {
		return getRowHeightMinimum();
	}
}
