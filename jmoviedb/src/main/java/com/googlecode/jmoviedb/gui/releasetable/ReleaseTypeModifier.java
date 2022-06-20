package com.googlecode.jmoviedb.gui.releasetable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class ReleaseTypeModifier implements ICellModifier {
    private TableViewer tableViewer;
    private String[] columnNames;

    public ReleaseTypeModifier(TableViewer tableViewer, String[] columnNames) {
        this.tableViewer = tableViewer;
        this.columnNames = columnNames;
    }

    @Override
    public boolean canModify(Object element, String property) {
        return true;
    }

    @Override
    public Object getValue(Object element, String property) {
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        return ReleaseTypeLabelProvider.getColumnTextForIndex(element, columnIndex);
    }

    @Override
    public void modify(Object element, String property, Object value) {
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        String obj = (String)(((TableItem)element).getData());
        switch (columnIndex) {
            case 0:
                obj = (String)value; // TODO
                break;
        }
        tableViewer.refresh();
        tableViewer.getTable().getColumn(columnIndex).pack();
    }
}
