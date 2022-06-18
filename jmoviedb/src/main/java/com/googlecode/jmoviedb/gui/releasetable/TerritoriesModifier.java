package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TerritoriesModifier extends LabelProvider implements ITableLabelProvider, ICellModifier {

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canModify(Object element, String property) {
        return true;
    }

    @Override
    public Object getValue(Object element, String property) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modify(Object arg0, String arg1, Object arg2) {
        // TODO Auto-generated method stub

    }
}
