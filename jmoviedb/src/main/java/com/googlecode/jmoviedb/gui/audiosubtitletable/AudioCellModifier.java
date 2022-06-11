package com.googlecode.jmoviedb.gui.audiosubtitletable;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.googlecode.jmoviedb.enumerated.AudioChannels;
import com.googlecode.jmoviedb.enumerated.AudioCodec;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.model.AudioTrack;

class AudioCellModifier implements ICellModifier {
    private TableViewer tableViewer;
    private String[] columnNames;

    public AudioCellModifier(TableViewer tableViewer, String[] columnNames) {
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
        
        AudioTrack track = (AudioTrack)element;

        switch(columnIndex) {
        case 1: 
            result = track.getLanguage().ordinal();
            break;
        case 2:
            result = track.isCommentary();
            break;
        case 3:
            result = track.isAudioDescriptive();
            break;
        case 4:
            result = track.getAudio().ordinal();
            break;
        case 5: 
            result = track.getChannels().ordinal();
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

        AudioTrack track = (AudioTrack)(((TableItem)element).getData());

        switch (columnIndex) {
        case 1:
            track.setLanguage(Language.values()[(Integer)value]);
            break;
        case 2:
            track.setCommentary((Boolean)value);
            break;
        case 3:
            track.setAudioDescriptive((Boolean)value);
            break;
        case 4:
            track.setAudio(AudioCodec.values()[(Integer)value]);
            break;
        case 5:
            track.setChannels(AudioChannels.values()[(Integer)value]);
            break;
        default:
        }
        
        tableViewer.refresh();
    }
}
