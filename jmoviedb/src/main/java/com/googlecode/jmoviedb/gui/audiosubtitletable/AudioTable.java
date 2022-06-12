package com.googlecode.jmoviedb.gui.audiosubtitletable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.enumerated.*;
import com.googlecode.jmoviedb.model.AudioTrack;

public class AudioTable extends AudioSubtitleTable<AudioTrack> {

    public AudioTable(Composite parent, Combo formatCombo) {
        super(parent, formatCombo, 
        	new String[] {"#", "Language","Track type","Format","Channels","Note"});
		tableViewer.setLabelProvider(new AudioLabelProvider(this));
    }

	@Override
	protected ICellModifier createCellModifier(TableViewer tableViewer) {
		return new AudioCellModifier(tableViewer, columnNames);
	}

	@Override
	protected CellEditor[] createCellEditors() {
		CellEditor[] editors = super.createCellEditors();
		editors[2] = new ComboBoxTextCellEditor(table, AudioTrackType.getStringArray(), SWT.DROP_DOWN);
		editors[3] = new ComboBoxCellEditor(table, AudioCodec.getStringArray(), SWT.READ_ONLY);
		editors[4] = new ComboBoxCellEditor(table, AudioChannels.getStringArray(), SWT.READ_ONLY);
		editors[5] = new TextCellEditor(table);
		return editors;
	}

	@Override
	protected String getAddButtonTooltip() {
		return "Add audio track";
	}

	@Override
	protected String getDeleteButtonTooltip() {
		return "Remove audio track";
	}
    
    @Override
    protected AudioTrack getNewTrack()
    {
        FormatType format = FormatType.values()[formatCombo.getSelectionIndex()];
		
		Language lang = Language.english;
		AudioCodec audioformat = AudioCodec.other;
		AudioChannels channels = AudioChannels.stereo;
		
		if(format == FormatType.dvd) {
			audioformat = AudioCodec.ac3;
			channels = AudioChannels.none;
		} else if(format == FormatType.bluray||format == FormatType.hddvd||format == FormatType.avchd||format == FormatType.bluray3d||format == FormatType.uhdbluray) {
			audioformat = AudioCodec.other;
			channels = AudioChannels.none;
		} else if(format == FormatType.vcd) {
			audioformat = AudioCodec.mp2;
			channels = AudioChannels.stereo;
		} else if(format == FormatType.svcd) {
			audioformat = AudioCodec.mp2;
			channels = AudioChannels.stereo;
		} else if(format == FormatType.laserdisc||format == FormatType.vhs) {
			audioformat = AudioCodec.analog;
			channels = AudioChannels.stereo;
		} else if(format == FormatType.umd) {
			audioformat = AudioCodec.atrac3plus;
			channels = AudioChannels.stereo;
		}
        return new AudioTrack(lang, audioformat, channels, false, false, "", lang.getName(), "");
    }
}
