package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ReleaseTypeLabelProvider extends LabelProvider implements ITableLabelProvider {
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        return getColumnTextForIndex(element, columnIndex);
    }

    public static String getColumnTextForIndex(Object element, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (String)element;
        }
        return "";
    }
}
