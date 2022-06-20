package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.audiosubtitletable.ComboBoxTextCellEditor;
import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;

public class ReleaseTypeTable extends MovieDialogTable<String> {

    public ReleaseTypeTable(Composite parent)  {
        super(parent, new String[] { "Release type" });
    }

    @Override
    protected ICellModifier createCellModifier(TableViewer tableViewer) {
        return new ReleaseTypeModifier(tableViewer, columnNames);
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
    protected String getNewObject() {
        return "";
    }

    @Override
    protected IBaseLabelProvider createLabelProvider() {
        return new ReleaseTypeLabelProvider();
    }

    @Override
    protected CellEditor[] createCellEditors() {
        CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new ComboBoxTextCellEditor(table, MainWindow.getMainWindow().getDB().getAllLanguages(), SWT.DROP_DOWN);
		return editors;
    }
}
