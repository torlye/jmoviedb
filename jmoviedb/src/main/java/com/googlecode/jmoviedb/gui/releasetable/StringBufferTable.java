package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.audiosubtitletable.ComboBoxTextCellEditor;
import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;

public abstract class StringBufferTable extends MovieDialogTable<StringBuffer> {

    public StringBufferTable(Composite parent, String name)  {
        super(parent, new String[] { name });
    }

    @Override
    protected ICellModifier createCellModifier(TableViewer tableViewer) {
        return new StringBufferModifier(tableViewer, columnNames);
    }

    @Override
    protected String getAddButtonTooltip() {
        return "Add";
    }

    @Override
    protected String getDeleteButtonTooltip() {
        return "Delete";
    }

    @Override
    protected StringBuffer getNewObject() {
        return new StringBuffer();
    }

    @Override
    protected IBaseLabelProvider createLabelProvider() {
        return new StringBufferLabelProvider();
    }

    @Override
    protected CellEditor[] createCellEditors() {
        CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new ComboBoxTextCellEditor(table, getItems(), SWT.DROP_DOWN);
		return editors;
    }

    public abstract String[] getItems();
}
