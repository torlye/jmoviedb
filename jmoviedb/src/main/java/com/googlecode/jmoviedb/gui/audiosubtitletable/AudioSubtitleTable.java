package com.googlecode.jmoviedb.gui.audiosubtitletable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.moviedialog.MovieDialogTable;
import com.googlecode.jmoviedb.model.AudioOrSubtitleTrack;

public abstract class AudioSubtitleTable<T extends AudioOrSubtitleTrack> extends MovieDialogTable<T> {
	protected Combo formatCombo;

	public AudioSubtitleTable(Composite parent, Combo formatCombo, String[] columnNames) {
		super(parent, columnNames);
		this.formatCombo = formatCombo;
	}

	@Override
	protected CellEditor[] createCellEditors() {
		CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new TextCellEditor(table);
		editors[1] = new ComboBoxTextCellEditor(table, MainWindow.getMainWindow().getDB().getAllLanguages(), SWT.DROP_DOWN);
		return editors;
	}
}
