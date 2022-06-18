package com.googlecode.jmoviedb.gui.audiosubtitletable;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;
import com.googlecode.jmoviedb.model.AudioOrSubtitleTrack;

public abstract class AudioSubtitleTable<T extends AudioOrSubtitleTrack> extends MovieDialogTable<T> {
	protected Combo formatCombo;

	public AudioSubtitleTable(Composite parent, Combo formatCombo, String[] columnNames) {
		super(parent, columnNames);
		this.formatCombo = formatCombo;
	}
}
