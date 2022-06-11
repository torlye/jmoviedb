package com.googlecode.jmoviedb.gui.audiosubtitletable;
import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.*;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AudioLabelProvider extends LabelProvider implements ITableLabelProvider {
    private AudioTable table;
	private Image tickImage;
    
    public AudioLabelProvider(AudioTable table) {
        this.table = table;
        int iconSize = Math.round(16*MainWindow.DPI_SCALE);
        tickImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_TICK12).getImageData(100), iconSize, iconSize);
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
        case 3:
            break;
        case 4:
            result += ((AudioTrack)element).getAudio().getShortName();
            break;
        case 5 :
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
        if(columnIndex == 2)
            if(((AudioTrack)element).isCommentary())
                return tickImage;
        if(columnIndex == 3)
            if(((AudioTrack)element).isAudioDescriptive())
                return tickImage;
        return null;
    }

    @Override
	public void dispose() {
		tickImage.dispose();
    }
}
