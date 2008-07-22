/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.jmoviedb.gui.action;

import java.util.ArrayList;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 *
 * @author mvejen
 */
public class SearchDropDownMenu extends ControlContribution implements SelectionListener {
  //Added drop down menu
  private Combo dropdownMenu;
  private String currentSelection = "Title";
  private SearchField searchField;
  private ArrayList<String> comboItemsList;
  
  public SearchDropDownMenu() {
    super("dropdownmenu");
    //Build item list of options to be shown in menu
    buildMenuItems();
  }

  @Override
  protected Control createControl(Composite parent) {
    //Drop down menu
    dropdownMenu = new Combo(parent, SWT.READ_ONLY);
    
    //Add menu fieds from items-list
    for (String item: comboItemsList) {
      dropdownMenu.add(item);
    }
    //Select first item as initial shown item
    dropdownMenu.select(0);
    
    //Pick up selection events here
    dropdownMenu.addSelectionListener(this);
    
    return dropdownMenu;
  }
  
  private void buildMenuItems() {
    comboItemsList = new ArrayList<String>();
    
    comboItemsList.add("Title"); //First item is initally shown in menu
    comboItemsList.add("Year");
    comboItemsList.add("Genre");
    comboItemsList.add("Director");
    comboItemsList.add("Writer");
    comboItemsList.add("Language"); //Also searches countries
  }

  /**
   * Not used
   */
  public void widgetDefaultSelected(SelectionEvent arg0) {
  }

  /**
   * Send event to searchField, regarding search parameter change
   */
  public void widgetSelected(SelectionEvent arg0) {
    this.currentSelection = dropdownMenu.getItem(dropdownMenu.getSelectionIndex());
    searchField.setSearchParameter(currentSelection);
  }

  public void setEventReceiver(SearchField receiver) {
    this.searchField = receiver;
  }
}
