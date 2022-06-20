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

public class MediaTable extends MovieDialogTable<Tuple<String, Integer>> {

    public MediaTable(Composite parent)  {
        super(parent, new String[] { "Type", "Count" });
    }

    @Override
    protected ICellModifier createCellModifier(TableViewer tableViewer) {
        return new MediaModifier(tableViewer, columnNames);
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
    protected Tuple<String, Integer> getNewObject() {
        return new Tuple<String, Integer>("", 0);
    }

    @Override
    protected IBaseLabelProvider createLabelProvider() {
        return new MediaLabelProvider();
    }

    @Override
    protected CellEditor[] createCellEditors() {
        CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new ComboBoxTextCellEditor(table, MainWindow.getMainWindow().getDB().getAllLanguages(), SWT.DROP_DOWN);
		editors[1] = new TextCellEditor(table);
		return editors;
    }
}
