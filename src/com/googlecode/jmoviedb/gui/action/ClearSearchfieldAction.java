package com.googlecode.jmoviedb.gui.action;

import org.eclipse.jface.action.Action;

public class ClearSearchfieldAction extends Action {
	
	private SearchField searchField;
	
	public ClearSearchfieldAction(SearchField searchField) {
		setText("Clear");
		setToolTipText("Clear searchfield");
		this.searchField = searchField;
	}
	
	public void run() {
		searchField.clear();
	}

}
