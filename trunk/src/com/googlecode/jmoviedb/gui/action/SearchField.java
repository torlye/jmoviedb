package com.googlecode.jmoviedb.gui.action;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swt.TextWidgetMatcherEditor;

public class SearchField extends ControlContribution implements SelectionListener, FocusListener {
	
	private Text searchfield;
	private TextWidgetMatcherEditor matcherEditor;
	private String defaultText;
	private Color normalColor;
	private Color disabledColor;

	public SearchField() {
		super("searchfield");
	}

	@Override
	protected Control createControl(Composite parent) {
		searchfield = new Text(parent, SWT.SINGLE|SWT.BORDER);
		searchfield.addSelectionListener(this);
		searchfield.addFocusListener(this);
		defaultText = "Search";
		matcherEditor = new TextWidgetMatcherEditor(searchfield, new MovieTextFilterator(), true);
		normalColor = searchfield.getForeground();
		disabledColor = new Color(Display.getCurrent(), 170, 170, 170);
		
		disableSearch();
		return searchfield;
	}
	
	@Override
	protected int computeWidth(Control control) {
		String temp = searchfield.getText(); 
		searchfield.setText("adventures");
		int w = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
		searchfield.setText(temp);
		return w;
	}
	
	private void disableSearch() {
		matcherEditor.setLive(false);
		searchfield.setForeground(disabledColor);
		searchfield.setText(defaultText);
	}
	
	private void enableSearch() {
		searchfield.setForeground(normalColor);
		searchfield.setText("");
		matcherEditor.setLive(true);
	}
	
	/**
	 * Returns the TextWidgetMatcherEditor
	 * @return a TextWidgetMatcherEditor
	 */
	@SuppressWarnings("unchecked")
	public MatcherEditor<AbstractMovie> getMatcherEditor() {
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
	public void widgetSelected(SelectionEvent arg0) {}

	public void focusGained(FocusEvent e) {
		if(searchfield.getText().equals(defaultText)) {
			enableSearch();
		}
	}

	public void focusLost(FocusEvent e) {
		if(searchfield.getText().equals("")) {
			disableSearch();
		}
	}
}