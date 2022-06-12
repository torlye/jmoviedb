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
        String result = "";
        switch (columnIndex) {
        case 0:
            result = (table.getModel().indexOf(element)+1)+"";
            break;
        case 1:  // COMPLETED_COLUMN
            result += ((AudioTrack)element).getLanguage().getName();
            break;
        case 2:
            result += ((AudioTrack)element).getTrackType();
            break;
        case 3:
            result += ((AudioTrack)element).getAudio().getShortName();
            break;
        case 4 :
            result += ((AudioTrack)element).getChannels().getDescription();
            break;
        default :
            break; 	
        }
        return result;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }
}
