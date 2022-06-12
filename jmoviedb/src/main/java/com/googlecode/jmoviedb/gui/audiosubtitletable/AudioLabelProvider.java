package com.googlecode.jmoviedb.gui.audiosubtitletable;
import com.googlecode.jmoviedb.model.*;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AudioLabelProvider extends LabelProvider implements ITableLabelProvider {
    private AudioTable table;
    
    public AudioLabelProvider(AudioTable table) {
        this.table = table;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    @Override
    public String getColumnText(Object element, int columnIndex) {
        AudioTrack track = (AudioTrack)element;
        switch (columnIndex) {
        case 0:
            return (table.getModel().indexOf(element)+1)+"";
        case 1:
            return track.getLanguageString();
        case 2:
            return track.getTrackType();
        case 3:
            return track.getAudio().getShortName();
        case 4:
            return track.getChannels().getDescription();
        case 5:
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
