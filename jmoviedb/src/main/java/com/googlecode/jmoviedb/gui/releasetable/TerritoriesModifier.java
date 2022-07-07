package com.googlecode.jmoviedb.gui.releasetable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.googlecode.jmoviedb.model.Tuple;

public class TerritoriesModifier implements ICellModifier {
    private TableViewer tableViewer;
    private String[] columnNames;

    public TerritoriesModifier(TableViewer tableViewer, String[] columnNames) {
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
        return TerritoriesLabelProvider.getColumnTextForIndex(element, columnIndex);
    }

    @Override
    public void modify(Object element, String property, Object value) {
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        Tuple<String, String> obj = (Tuple<String, String>)(((TableItem)element).getData());
        switch (columnIndex) {
            case 0:
                obj.setValue1((String)value);
                break;
            case 1:
                obj.setValue2((String)value);
                break;
        }
        tableViewer.refresh();
        tableViewer.getTable().getColumn(columnIndex).pack();
    }
}
