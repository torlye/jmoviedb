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
import com.googlecode.jmoviedb.gui.action.search.TitleTextFilterator;

import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swt.TextWidgetMatcherEditor;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.action.search.DirectorTextFilterator;
import com.googlecode.jmoviedb.gui.action.search.GenreTextFilterator;
import com.googlecode.jmoviedb.gui.action.search.LanguagesTextFilterator;
import com.googlecode.jmoviedb.gui.action.search.WriterTextFilterator;
import com.googlecode.jmoviedb.gui.action.search.YearTextFilterator;

public class SearchField extends ControlContribution implements SelectionListener, FocusListener {

	private Text searchfield;
	private TextWidgetMatcherEditor matcherEditor;
	private String defaultText;
	private Color normalColor;
	private Color disabledColor;
	private MainWindow mainWindow;

	public SearchField() {
		super("searchfield");
	}

	@Override
	protected Control createControl(Composite parent) {
		searchfield = new Text(parent, SWT.SINGLE | SWT.BORDER);
		searchfield.addSelectionListener(this);
		searchfield.addFocusListener(this);
		defaultText = "Search";
		matcherEditor = new TextWidgetMatcherEditor(searchfield, new TitleTextFilterator(), true);
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
	public void widgetSelected(SelectionEvent arg0) {

	}

	public void focusGained(FocusEvent e) {
		if (searchfield.getText().equals(defaultText)) {
			enableSearch();
		}
	}

	public void focusLost(FocusEvent e) {
		if (searchfield.getText().equals("")) {
			disableSearch();
		}
	}

	//Sets the matcherEditor according to the searchparameter chosen
	public void setSearchParameter(String searchParameter) {
		//Remove old matchereditor listeners
		matcherEditor.dispose();

		//Todo replace with class-loading or likewise
		if (searchParameter.equals("Year")) {
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new YearTextFilterator(), true);
		} else if (searchParameter.equals("Genre")) {
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new GenreTextFilterator(), true);
		} else if (searchParameter.equals("Director")) {
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new DirectorTextFilterator(), true);
		} else if (searchParameter.equals("Writer")) {
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new WriterTextFilterator(), true);
		} else if (searchParameter.equals("Language")) {
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new LanguagesTextFilterator(), true);
		} else {
			//Default to title
			matcherEditor = new TextWidgetMatcherEditor(searchfield, new TitleTextFilterator(), true);
		}

		//Notify main window that lists should be updated
		mainWindow.updateSearchFilter();
	}

	public void setEventReceiver(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
}
