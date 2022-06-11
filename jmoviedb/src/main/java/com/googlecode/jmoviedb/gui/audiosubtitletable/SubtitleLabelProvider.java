package com.googlecode.jmoviedb.gui.audiosubtitletable;
import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.model.*;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class SubtitleLabelProvider extends LabelProvider implements ITableLabelProvider {
    private SubtitleTable table;
	private Image tickImage;
    
    public SubtitleLabelProvider(SubtitleTable table) {
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
            result += ((SubtitleTrack)element).getLanguage().getName();
            break;
        case 2:
        case 3:
            break;
        case 4:
            break;
        case 5 :
            result += ((SubtitleTrack)element).getFormat().getShortName();
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
            if(((SubtitleTrack)element).isCommentary())
                return tickImage;
        if(columnIndex == 3)
            if(((SubtitleTrack)element).isHearingImpaired())
                return tickImage;
        if(columnIndex == 4)
            if(((SubtitleTrack)element).isForced())
                return tickImage;
        return null;
    }

    @Override
	public void dispose() {
		tickImage.dispose();
    }
}
