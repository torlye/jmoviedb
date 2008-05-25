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
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.enumerated.AudioChannels;
import com.googlecode.jmoviedb.enumerated.AudioCodec;
import com.googlecode.jmoviedb.enumerated.Language;
import com.googlecode.jmoviedb.model.AudioTrack;
import com.googlecode.jmoviedb.model.SubtitleTrack;

public class AudioSubtitleTable {

	private boolean audio;
	private Table table;
	private TableViewer tableViewer;
	//	private Button closeButton;

	private ArrayList<AudioTrack> audioModel;
	private ArrayList<SubtitleTrack> subModel;

	// column names
	private String[] columnNames;
	
	private Image tickImage;
	private Image addImage;
	private Image removeImage;
	private Image upImage;
	private Image downImage;

	public AudioSubtitleTable(Composite parent, boolean audio) {
		this.audio = audio;
		
		tickImage = ImageDescriptor.createFromURL(CONST.ICON_TICK12).createImage();
		addImage = ImageDescriptor.createFromURL(CONST.ICON_ADD).createImage();
		removeImage = ImageDescriptor.createFromURL(CONST.ICON_DELETE).createImage();
		upImage = ImageDescriptor.createFromURL(CONST.ICON_UP).createImage();
		downImage = ImageDescriptor.createFromURL(CONST.ICON_DOWN).createImage();
		
		if(audio)
			columnNames = new String[] {"Language","Commentary track","Format","Channels"};
		else
			columnNames = new String[] {"Language","Commentary track","Hearing impaired","Format"};
		
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		parent.setLayoutData(gridData);

		// Set numColumns to 3 for the buttons 
		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = 4;
		parent.setLayout(layout);

		// Create the table 
		createTable(parent);

		// Create and setup the TableViewer
		createTableViewer();
		tableViewer.setContentProvider(new AudioSubContentProvider());
		tableViewer.setLabelProvider(new AudioLabelProvider());

		// Add the buttons
		createButtons(parent);
	}
	
	public void setAudioModel(ArrayList<AudioTrack> arrayList) {
		this.audioModel = arrayList;
		tableViewer.setInput(arrayList);
	}
	
	public void setSubModel(ArrayList<SubtitleTrack> arrayList) {
		this.subModel = arrayList;
		tableViewer.setInput(arrayList);
	}
	
	public ArrayList<AudioTrack> getAudioModel() {
		return audioModel;
	}

	public ArrayList<SubtitleTrack> getSubModel() {
		return subModel;
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

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 4;
		table.setLayoutData(gridData);		

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column;
		
		for(int i=0; i<columnNames.length; i++) {
			column = new TableColumn(table, SWT.CENTER, i);		
			column.setText(columnNames[i]);
			column.setWidth(100);
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
		editors[0] = new ComboBoxCellEditor(table, Language.getStringArray(), SWT.READ_ONLY);
		editors[1] = new CheckboxCellEditor(table);
		editors[2] = new ComboBoxCellEditor(table, AudioCodec.getStringArray(), SWT.READ_ONLY);
		editors[3] = new ComboBoxCellEditor(table, AudioChannels.getStringArray(), SWT.READ_ONLY);

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

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		up.setLayoutData(gridData);
		up.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AudioTrack track = (AudioTrack)((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					int currentIndex = audioModel.indexOf(track);
					if(currentIndex > 0) {
						AudioTrack temp = audioModel.get(currentIndex);
						audioModel.set(currentIndex, audioModel.get(currentIndex-1));
						audioModel.set(currentIndex-1, temp);
						tableViewer.refresh();
					}
				}
			}
		});
		
		// Create and configure the "Down" button
		Button down = new Button(parent, SWT.PUSH | SWT.CENTER);
		down.setImage(downImage);

		gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		down.setLayoutData(gridData);
		down.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				audioModel.add(new AudioTrack(Language.english, AudioCodec.other, AudioChannels.stereo, false));
				AudioTrack track = (AudioTrack)((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					int currentIndex = audioModel.indexOf(track);
					if(currentIndex < audioModel.size()-1 && audioModel.size() > 1) {
						AudioTrack temp = audioModel.get(currentIndex);
						audioModel.set(currentIndex, audioModel.get(currentIndex+1));
						audioModel.set(currentIndex+1, temp);
						tableViewer.refresh();
					}
				}
			}
		});
		
		// Create and configure the "Add" button
		Button add = new Button(parent, SWT.PUSH | SWT.CENTER);
		add.setImage(addImage);
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_END);
		add.setLayoutData(gridData);
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				audioModel.add(new AudioTrack(Language.english, AudioCodec.other, AudioChannels.stereo, false));
				tableViewer.refresh();
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
		delete.setImage(removeImage);
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_END);
		delete.setLayoutData(gridData); 
		delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AudioTrack track = (AudioTrack)((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
				if (track != null) {
					audioModel.remove(track);
					tableViewer.refresh();
				}
			}
		});
	}
	
	private class AudioLabelProvider extends LabelProvider implements ITableLabelProvider {
		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			String result = "";
			switch (columnIndex) {
			case 0:  // COMPLETED_COLUMN
				result += ((AudioTrack)element).getLanguage().getName();
				break;
			case 1 :
				break;
			case 2 :
				result += ((AudioTrack)element).getAudio().getShortName();
				break;
			case 3 :
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
		public Image getColumnImage(Object element, int columnIndex) {
			if(columnIndex == 1)
				if(((AudioTrack)element).isCommentary())
					return tickImage;
			return null;
		}

	}

	private class AudioCellModifier implements ICellModifier {

		/**
		 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
		 */
		public boolean canModify(Object element, String property) {
			System.out.println("CellModifier canModify "+element+" "+property);
			return true;
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
				case 0: 
					result = track.getLanguage().ordinal();
					break;
				case 1:
					result = track.isCommentary();
					break;
				case 2:
					result = track.getAudio().ordinal();
					break;
				case 3: 
					result = track.getChannels().ordinal();
					break;
				default :
					result = "";
				}
			} else {
				SubtitleTrack track = (SubtitleTrack)element;

				switch(columnIndex) {
				case 0: 
					result = Arrays.asList(Language.values()).indexOf(track);
					break;
				case 1:
					result = track.isCommentary();
					break;
				case 2:
					result = track.isHearingImpaired();
					break;
				case 3: 
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

			AudioTrack track = (AudioTrack)(((TableItem)element).getData());

			switch (columnIndex) {
			case 0:
				track.setLanguage(Language.values()[(Integer)value]);
				break;
			case 1:
				track.setCommentary((Boolean)value);
				break;
			case 2:
				track.setAudio(AudioCodec.values()[(Integer)value]);
				break;
			case 3:
				track.setChannels(AudioChannels.values()[(Integer)value]);
				break;
			default:
			}
			tableViewer.refresh();
		}
	}
}
