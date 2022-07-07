package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.googlecode.jmoviedb.model.Tuple;

public class TerritoriesLabelProvider extends LabelProvider implements ITableLabelProvider {
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        return getColumnTextForIndex(element, columnIndex);
    }

    public static String getColumnTextForIndex(Object element, int columnIndex) {
        Tuple<String, String> obj = (Tuple<String, String>)element;
        switch (columnIndex) {
            case 0:
                return obj.getValue1();
            case 1:
                return obj.getValue2();
        }
        return "";
    }
}
