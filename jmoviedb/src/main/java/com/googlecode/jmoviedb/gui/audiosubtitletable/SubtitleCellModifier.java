package com.googlecode.jmoviedb.gui.audiosubtitletable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.googlecode.jmoviedb.enumerated.Language;
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

        Object result = null;
        SubtitleTrack track = (SubtitleTrack)element;

        switch(columnIndex) {
        case 1: 
//					result = Arrays.asList(Language.values()).indexOf(track);
            result = track.getLanguage().ordinal();
            break;
        case 2:
            result = track.isCommentary();
            break;
        case 3:
            result = track.isHearingImpaired();
            break;
        case 4:
            result = track.isForced();
            break;
        case 5: 
            result = track.getFormat().getID();
            break;
        default :
            result = "";
        }
        return result;	
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
            track.setLanguage(Language.values()[(Integer)value]);
            break;
        case 2:
            track.setCommentary((Boolean)value);
            break;
        case 3:
            track.setHearingImpaired((Boolean)value);
            break;
        case 4:
            track.setForced((Boolean)value);
            break;
        case 5:
            track.setFormat(SubtitleFormat.values()[(Integer)value]);
            break;
        default:
        }

        tableViewer.refresh();
        tableViewer.getTable().getColumn(columnIndex).pack();
    }
}
