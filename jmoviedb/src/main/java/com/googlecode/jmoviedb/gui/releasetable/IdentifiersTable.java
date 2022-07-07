package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.audiosubtitletable.ComboBoxTextCellEditor;
import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;
import com.googlecode.jmoviedb.model.Tuple;

public class IdentifiersTable extends MovieDialogTable<Tuple<String, String>> {

    public IdentifiersTable(Composite parent)  {
        super(parent, new String[] { "Type", "Value" });
    }

    @Override
    protected ICellModifier createCellModifier(TableViewer tableViewer) {
        return new IdentifiersModifier(tableViewer, columnNames);
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
    protected Tuple<String, String> getNewObject() {
        return new Tuple<String, String>("", "");
    }

    @Override
    protected IBaseLabelProvider createLabelProvider() {
        return new IdentifiersLabelProvider();
    }

    @Override
    protected CellEditor[] createCellEditors() {
        CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new ComboBoxTextCellEditor(table, MainWindow.getMainWindow().getDB().getAllIdentifierTypes(), SWT.DROP_DOWN);
		editors[1] = new TextCellEditor(table);
		return editors;
    }
}
