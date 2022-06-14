package com.googlecode.jmoviedb.gui.audiosubtitletable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.googlecode.jmoviedb.enumerated.SubtitleFormat;
import com.googlecode.jmoviedb.model.SubtitleTrack;

class SubtitleCellModifier implements ICellModifier {
    private TableViewer tableViewer;
    private String[] columnNames;

    public SubtitleCellModifier(TableViewer tableViewer, String[] columnNames) {
        this.tableViewer = tableViewer;
        this.columnNames = columnNames;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
     */
    public boolean canModify(Object element, String property) {
        // System.out.println("CellModifier canModify "+element+" "+property);
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        
        return columnIndex != 0;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
     */
    public Object getValue(Object element, String property) {
        // Find the index of the column
        int columnIndex = Arrays.asList(columnNames).indexOf(property);
        SubtitleTrack track = (SubtitleTrack)element;

        switch(columnIndex) {
        case 1: 
            return track.getLanguageString();
        case 2:
            return track.getTrackType();
        case 3:
            return track.getFormat().getID();
        case 4:
            return track.getNote() == null ? "" : track.getNote();
        default:
            return "";
        }
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public void modify(Object element, String property, Object value) {	
        // Find the index of the column 
        int columnIndex	= Arrays.asList(columnNames).indexOf(property);

        SubtitleTrack track = (SubtitleTrack)(((TableItem)element).getData());

        switch (columnIndex) {
        case 1:
            track.setLanguageString((String)value);;
            break;
        case 2:
            track.setTrackType((String)value);
            break;
        case 3:
            track.setFormat(SubtitleFormat.values()[(Integer)value]);
            break;
        case 4:
            track.setNote((String)value);
            break;
        default:
        }

        tableViewer.refresh();
        tableViewer.getTable().getColumn(columnIndex).pack();
    }
}
