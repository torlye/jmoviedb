package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.audiosubtitletable.*;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.SubtitleTrack;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class AudioSubTab implements IMovieDialogTab {
	private Image audioTabIcon;
	private AudioSubtitleTable<AudioTrack> audioTable;
	private AudioSubtitleTable<SubtitleTrack> subtitleTable;
    private FormatTab formatTab;

    public AudioSubTab(int iconSize, FormatTab formatTab) {
        audioTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_AUDIOSUBTAB).createImage(), iconSize, iconSize);
        this.formatTab = formatTab;
    }

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab5 = new CTabItem(tabFolder, SWT.NULL);
		tab5.setText("Audio and subtitles");
		tab5.setImage(audioTabIcon);

		Composite c = new Composite(tabFolder, SWT.NULL);

//		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
//		parent.setLayoutData(gridData);

		// Set numColumns to 3 for the buttons
		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = 4;
		c.setLayout(layout);

		audioTable = new AudioTable(c, formatTab.getFormatCombo());
		subtitleTable = new SubtitleTable(c, formatTab.getFormatCombo());

		tab5.setControl(c);
    }

    @Override
    public void configureListeners() {
    }

    @Override
    public void setModel(AbstractMovie m) {
		audioTable.setModel(m.getAudioTracks());
		subtitleTable.setModel(m.getSubtitles());
    }

    @Override
    public void save(AbstractMovie movie) {
		movie.setAudioTracks(audioTable.getModel());
		movie.setSubtitles(subtitleTable.getModel());
    }

    @Override
    public void dispose() {
		audioTabIcon.dispose();
		audioTable.dispose();
		subtitleTable.dispose();
    }
}
