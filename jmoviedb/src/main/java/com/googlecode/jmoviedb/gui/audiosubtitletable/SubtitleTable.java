package com.googlecode.jmoviedb.gui.audiosubtitletable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.jmoviedb.enumerated.*;
import com.googlecode.jmoviedb.model.SubtitleTrack;

public class SubtitleTable extends AudioSubtitleTable<SubtitleTrack> {

    public SubtitleTable(Composite parent,  Combo formatCombo) {
        super(parent, formatCombo, 
			new String[] {"#", "Language","Commentary track","Hearing impaired","Forced","Format"});
		tableViewer.setLabelProvider(new SubtitleLabelProvider(this));
    }

	@Override
	protected ICellModifier createCellModifier(TableViewer tableViewer) {
		return new SubtitleCellModifier(tableViewer, columnNames);
	}

	@Override
	protected CellEditor[] createCellEditors() {
		CellEditor[] editors = super.createCellEditors();
		editors[3] = new CheckboxCellEditor(table);
		editors[4] = new CheckboxCellEditor(table);
		editors[5] = new ComboBoxCellEditor(table, SubtitleFormat.getStringArray(), SWT.READ_ONLY);
		return editors;
	}

	@Override
	protected String getAddButtonTooltip() {
		return "Add subtitle";
	}
    
	@Override
	protected String getDeleteButtonTooltip() {
		return "Remove subtitle";
	}

    @Override
    protected SubtitleTrack getNewTrack()
    {
        FormatType format = FormatType.values()[formatCombo.getSelectionIndex()];
		
		Language lang = Language.english;
		SubtitleFormat subformat = SubtitleFormat.other;
		
		if(format == FormatType.dvd) {
			subformat = SubtitleFormat.vobsub;
		} else if(format == FormatType.bluray||format == FormatType.hddvd||format == FormatType.avchd||format == FormatType.bluray3d||format == FormatType.uhdbluray) {
			subformat = SubtitleFormat.medianative;
		} else if(format == FormatType.vcd) {
			subformat = SubtitleFormat.burnt_in;
		} else if(format == FormatType.svcd) {
			subformat = SubtitleFormat.cvd;
		} else if(format == FormatType.laserdisc||format == FormatType.vhs) {
			subformat = SubtitleFormat.burnt_in;
		} else if(format == FormatType.umd) {
			subformat = SubtitleFormat.medianative;
		}
		
        return new SubtitleTrack(lang, subformat, false, false, false);
    }
}
