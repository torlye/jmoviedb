package com.googlecode.jmoviedb.gui.audiosubtitletable;
import com.googlecode.jmoviedb.model.*;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class SubtitleLabelProvider extends LabelProvider implements ITableLabelProvider {
    private SubtitleTable table;
    
    public SubtitleLabelProvider(SubtitleTable table) {
        this.table = table;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    @Override
    public String getColumnText(Object element, int columnIndex) {
        SubtitleTrack track = (SubtitleTrack)element;
        switch (columnIndex) {
        case 0:
            return (table.getModel().indexOf(element)+1)+"";
        case 1:
            return track.getLanguageString();
        case 2:
            return track.getTrackType();
        case 3:
            return track.getFormat().getShortName();
        case 4:
            return track.getNote();
        default:
            return ""; 	
        }
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }
}
