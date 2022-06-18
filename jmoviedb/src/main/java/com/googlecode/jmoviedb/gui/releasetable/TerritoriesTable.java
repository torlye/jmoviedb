package com.googlecode.jmoviedb.gui.releasetable;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;
import com.googlecode.jmoviedb.model.Tuple;

public class TerritoriesTable extends MovieDialogTable<Tuple<String, String>> {
    private TerritoriesModifier cellModifier;

    public TerritoriesTable(Composite parent)  {
        super(parent, new String[] { "Territory", "Rating" });
        cellModifier = new TerritoriesModifier();
    }

    @Override
    protected ICellModifier createCellModifier(TableViewer tableViewer) {
        return cellModifier;
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
        return cellModifier;
    }
}
