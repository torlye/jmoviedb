package com.googlecode.jmoviedb.gui;

import ca.odell.glazedlists.swt.AbstractKTableFormat;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.renderers.DefaultCellRenderer;

public class MovieTableFormat extends AbstractKTableFormat {
	private int fontHeight = 17;
	
	public MovieTableFormat(int i) {
		super();
		this.fontHeight = i;
	}

	public KTableCellEditor getColumnEditor(Object arg0, int arg1) {
		return null;
	}

	public Object getColumnHeaderValue(int headerRow, int column) {
		return null;
	}

	public KTableCellRenderer getColumnRenderer(Object arg0, int arg1) {
		return new MovieTableCellRenderer(DefaultCellRenderer.STYLE_PUSH);
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int column) {
		return null;
	}

	public Object getColumnValue(Object baseObject, int column) {
		return baseObject;
	}
	
	public int getRowHeightMinimum() {
		return 10 + (fontHeight+10)*3;
	}
	
	public int getRowHeight(Object rowObject) {
		return getRowHeightMinimum();
	}
}
