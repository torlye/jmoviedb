package com.googlecode.jmoviedb.gui.audiosubtitletable;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

public class ComboBoxTextCellEditor extends ComboBoxCellEditor {
    public ComboBoxTextCellEditor(Composite parent, String[] items, int style) {
        super(parent, items, style);
    }

    @Override
    protected Object doGetValue() {
        return ((CCombo)this.getControl()).getText();
    }

    @Override
    protected void doSetValue(Object value) {
        ((CCombo)this.getControl()).setText((String)value);
    }
}
