package com.googlecode.jmoviedb.gui.action;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import ca.odell.glazedlists.swt.TextWidgetMatcherEditor;

public class SearchField extends ControlContribution implements SelectionListener {
	
	private Text searchfield;
	private TextWidgetMatcherEditor matcherEditor;

	public SearchField() {
		super("searchfield");
	}

	@Override
	protected Control createControl(Composite parent) {
		searchfield = new Text(parent, SWT.SINGLE|SWT.BORDER);
		searchfield.addSelectionListener(this);
		matcherEditor = new TextWidgetMatcherEditor(searchfield, new MovieTextFilterator(), true);
		return searchfield;
	}
	
	/**
	 * Returns the TextWidgetMatcherEditor
	 * @return a TextWidgetMatcherEditor
	 */
	public TextWidgetMatcherEditor getMatcherEditor() {
		return matcherEditor;
	}
	
	/**
	 * Clears the search field.
	 */
	public void clear() {
		searchfield.setText("");
	}

	/**
	 * Called whenever enter is pressed in the search field.
	 * This will clear the search field.
	 */
	public void widgetDefaultSelected(SelectionEvent arg0) {
		clear();
	}

	/**
	 * Not used
	 */
	public void widgetSelected(SelectionEvent arg0) {
		System.out.println("Searchfield selected "+arg0);
	}

}