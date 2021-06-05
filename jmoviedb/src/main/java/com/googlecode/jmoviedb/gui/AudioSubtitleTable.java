package com.googlecode.jmoviedb.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.AudioChannels;
import com.googlecode.jmoviedb.enumerated.AudioCodec;
import com.googlecode.jmoviedb.enumerated.FormatType;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.enumerated.SubtitleFormat;
import com.googlecode.jmoviedb.model.AudioOrSubtitleTrack;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.SubtitleTrack;

public class AudioSubtitleTable<T extends AudioOrSubtitleTrack> {

	private boolean audio;
	private Table table;
	private TableViewer tableViewer;
	//	private Button closeButton;

	private ArrayList<T> model;

	// column names
	private String[] columnNames;
	
	private Image tickImage;
	private Image addImage;
	private Image removeImage;
	private Image upImage;
	private Image downImage;
	private Combo formatCombo;

	public AudioSubtitleTable(Composite parent, boolean audio, Combo formatCombo) {
		this.audio = audio;
		this.formatCombo = formatCombo;
		
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		tickImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_TICK12).getImageData(), iconSize, iconSize);
		addImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_ADD).getImageData(), iconSize, iconSize);
		removeImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_DELETE).getImageData(), iconSize, iconSize);
		upImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_UP).getImageData(), iconSize, iconSize);
		downImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_DOWN).getImageData(), iconSize, iconSize);
		
		if(audio)
			columnNames = new String[] {"#", "Language","Commentary track","Audio descriptive","Format","Channels"};
		else
			columnNames = new String[] {"#", "Language","Commentary track","Hearing impaired","Forced","Format"};
		
		// Create the table 
		createTable(parent);

		// Create and setup the TableViewer
		createTableViewer();
		tableViewer.setContentProvider(new AudioSubContentProvider());
		tableViewer.setLabelProvider(new AudioLabelProvider());

		// Add the buttons
		createButtons(parent);
	}
	
	public void setModel(ArrayList<T> arrayList) {
		this.model = arrayList;
		tableViewer.setInput(arrayList);
		for (TableColumn col : table.getColumns()) {
			col.pack();
		}
	}
	
	public ArrayList<T> getModel() {
		return model;
	}

	/**
	 * Disposes of Image resources
	 */
	public void dispose() {
		tickImage.dispose();
		addImage.dispose();
		removeImage.dispose();
		upImage.dispose();
		downImage.dispose();
	}

	
	/**
	 * Create the Table
	 */
	private void createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
		SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		table = new Table(parent, style);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 5;
		gridData.minimumHeight = 100;
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column;
		
		for(int i=0; i<columnNames.length; i++) {
			column = new TableColumn(table, SWT.LEFT, i);		
			column.setText(columnNames[i]);
			column.setWidth(Math.round(100 * MainWindow.DPI_SCALE));
		}

	}

	/**
	 * Create the TableViewer 
	 */
	private void createTableViewer() {

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		tableViewer.setColumnProperties(columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];
		editors[0] = new TextCellEditor(table);
		editors[1] = new ComboBoxCellEditor(table, Language.getStringArray(), SWT.READ_ONLY);
		editors[2] = new CheckboxCellEditor(table);
		if(audio) {
			editors[3] = new CheckboxCellEditor(table);
			editors[4] = new ComboBoxCellEditor(table, AudioCodec.getStringArray(), SWT.READ_ONLY);
			editors[5] = new ComboBoxCellEditor(table, AudioChannels.getStringArray(), SWT.READ_ONLY);
		} else {
			editors[3] = new CheckboxCellEditor(table);
			editors[4] = new CheckboxCellEditor(table);
			editors[5] = new ComboBoxCellEditor(table, SubtitleFormat.getStringArray(), SWT.READ_ONLY);
		}

		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		
		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(new AudioCellModifier());
	}

	/**
	 * Private internal contentprovider 
	 */
	private class AudioSubContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

		public void dispose() {}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object parent) {
			if (audio)
				return ((ArrayList<AudioTrack>)parent).toArray();				
			else
				return ((ArrayList<SubtitleTrack>)parent).toArray();
		}
	}

	/**
	 * Add buttons
	 * @param parent the parent composite
	 */
	private void createButtons(Composite parent) {
		
		// Create and configure the "Up" button
		Button up = new Button(parent, SWT.PUSH | SWT.CENTER);
		up.setImage(upImage);
		up.setToolTipText("Move up");

		GridData gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		up.setLayoutData(gridData);
		up.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Object track = ((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					int currentIndex = model.indexOf(track);
					if(currentIndex > 0) {
						T temp = model.get(currentIndex);
						model.set(currentIndex, model.get(currentIndex-1));
						model.set(currentIndex-1, temp);
						tableViewer.refresh();
					}
				}
			}
		});
		
		// Create and configure the "Down" button
		Button down = new Button(parent, SWT.PUSH | SWT.CENTER);
		down.setImage(downImage);
		down.setToolTipText("Move down");

		gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		down.setLayoutData(gridData);
		down.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Object track = ((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					int currentIndex = model.indexOf(track);
					if(currentIndex < model.size()-1 && model.size() > 1) {
						T temp = model.get(currentIndex);
						model.set(currentIndex, model.get(currentIndex+1));
						model.set(currentIndex+1, temp);
						tableViewer.refresh();
					}
				}
			}
		});
		
		// Create and configure the "Add" button
		Button add = new Button(parent, SWT.PUSH | SWT.CENTER);
		add.setImage(addImage);
		if(audio)
			add.setToolTipText("Add audio track");
		else
			add.setToolTipText("Add subtitle");
		
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		add.setLayoutData(gridData);
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				model.add((T)getNewTrack());
				tableViewer.refresh();
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
		delete.setImage(removeImage);
		if(audio)
			delete.setToolTipText("Remove audio track");
		else
			delete.setToolTipText("Remove subtitle");
		
		
		gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		delete.setLayoutData(gridData); 
		delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Object track = ((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					model.remove(track);
					tableViewer.refresh();
				}
			}
		});
	}
	
	private AudioOrSubtitleTrack getNewTrack() {
		FormatType format = FormatType.values()[formatCombo.getSelectionIndex()];
		
		Language lang = Language.english;
		AudioCodec audioformat = AudioCodec.other;
		AudioChannels channels = AudioChannels.stereo;
		SubtitleFormat subformat = SubtitleFormat.other;
		
		if(format == FormatType.dvd) {
			audioformat = AudioCodec.ac3;
			channels = AudioChannels.none;
			subformat = SubtitleFormat.vobsub;
		} else if(format == FormatType.bluray||format == FormatType.hddvd||format == FormatType.avchd||format == FormatType.bluray3d||format == FormatType.uhdbluray) {
			audioformat = AudioCodec.other;
			channels = AudioChannels.none;
			subformat = SubtitleFormat.medianative;
		} else if(format == FormatType.vcd) {
			audioformat = AudioCodec.mp2;
			channels = AudioChannels.stereo;
			subformat = SubtitleFormat.burnt_in;
		} else if(format == FormatType.svcd) {
			audioformat = AudioCodec.mp2;
			channels = AudioChannels.stereo;
			subformat = SubtitleFormat.cvd;
		} else if(format == FormatType.laserdisc||format == FormatType.vhs) {
			audioformat = AudioCodec.analog;
			channels = AudioChannels.stereo;
			subformat = SubtitleFormat.burnt_in;
		} else if(format == FormatType.umd) {
			audioformat = AudioCodec.atrac3plus;
			channels = AudioChannels.stereo;
			subformat = SubtitleFormat.medianative;
		}
		if(audio) {
			return new AudioTrack(lang, audioformat, channels, false, false);
		} else {
			return new SubtitleTrack(lang, subformat, false, false, false);
		}
	}
	
	private class AudioLabelProvider extends LabelProvider implements ITableLabelProvider {
		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			String result = "";
			switch (columnIndex) {
			case 0:
				result = (AudioSubtitleTable.this.getModel().indexOf(element)+1)+"";
				break;
			case 1:  // COMPLETED_COLUMN
				if(audio)
					result += ((AudioTrack)element).getLanguage().getName();
				else
					result += ((SubtitleTrack)element).getLanguage().getName();
				break;
			case 2:
			case 3:
				break;
			case 4:
				if(audio)
					result += ((AudioTrack)element).getAudio().getShortName();
				break;
			case 5 :
				if(audio)
					result += ((AudioTrack)element).getChannels().getDescription();
				else
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
		public Image getColumnImage(Object element, int columnIndex) {
			if(audio) {
				if(columnIndex == 2)
					if(((AudioTrack)element).isCommentary())
						return tickImage;
				if(columnIndex == 3)
					if(((AudioTrack)element).isAudioDescriptive())
						return tickImage;
			} else {
				if(columnIndex == 2)
					if(((SubtitleTrack)element).isCommentary())
						return tickImage;
				if(columnIndex == 3)
					if(((SubtitleTrack)element).isHearingImpaired())
						return tickImage;
				if(columnIndex == 4)
					if(((SubtitleTrack)element).isForced())
						return tickImage;
			}
			return null;
		}

	}

	private class AudioCellModifier implements ICellModifier {

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
			if(audio) {
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
			} else {
				SubtitleTrack track = (SubtitleTrack)element;

				switch(columnIndex) {
				case 1: 
//					result = Arrays.asList(Language.values()).indexOf(track);
					result = track.getLanguage().ordinal();
					break;
				case 2:
					result = track.isCommentary();
					break;
				case 3:
					result = track.isHearingImpaired();
					break;
				case 4:
					result = track.isForced();
					break;
				case 5: 
					result = track.getFormat().getID();
					break;
				default :
					result = "";
				}
			}
			return result;	
		}

		/**
		 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
		 */
		public void modify(Object element, String property, Object value) {	
			// Find the index of the column 
			int columnIndex	= Arrays.asList(columnNames).indexOf(property);

			if(audio) {
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
			} else {
				SubtitleTrack track = (SubtitleTrack)(((TableItem)element).getData());

				switch (columnIndex) {
				case 1:
					track.setLanguage(Language.values()[(Integer)value]);
					break;
				case 2:
					track.setCommentary((Boolean)value);
					break;
				case 3:
					track.setHearingImpaired((Boolean)value);
					break;
				case 4:
					track.setForced((Boolean)value);
					break;
				case 5:
					track.setFormat(SubtitleFormat.values()[(Integer)value]);
					break;
				default:
				}

			}
			tableViewer.refresh();
		}
	}
}
