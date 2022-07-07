package com.googlecode.jmoviedb.gui.releasetable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class StringBufferModifier implements ICellModifier {
    private TableViewer tableViewer;
    private String[] columnNames;

    public StringBufferModifier(TableViewer tableViewer, String[] columnNames) {
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
        return StringBufferLabelProvider.getColumnTextForIndex(element, columnIndex);
    }

    @Override
    public void modify(Object element, String property, Object value) {
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        StringBuffer obj = (StringBuffer)(((TableItem)element).getData());
        switch (columnIndex) {
            case 0:
                obj.replace(0, obj.length(), (String)value);
                break;
        }
        tableViewer.refresh();
        tableViewer.getTable().getColumn(columnIndex).pack();
    }
}
