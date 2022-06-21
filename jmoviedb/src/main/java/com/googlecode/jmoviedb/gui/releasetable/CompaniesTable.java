package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.MainWindow;

public class CompaniesTable extends StringBufferTable {
    public CompaniesTable(Composite parent) {
        super(parent, "Company");
    }

    @Override
    public String[] getItems() {
        return MainWindow.getMainWindow().getDB().getAllCompanies();
    }
}
