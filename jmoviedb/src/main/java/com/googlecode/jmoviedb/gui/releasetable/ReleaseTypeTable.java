package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.MainWindow;

public class ReleaseTypeTable extends StringBufferTable {

    public ReleaseTypeTable(Composite parent) {
        super(parent, "Release type");
    }

    @Override
    public String[] getItems() {
        return MainWindow.getMainWindow().getDB().getAllReleaseTypes();
    }
    
}
