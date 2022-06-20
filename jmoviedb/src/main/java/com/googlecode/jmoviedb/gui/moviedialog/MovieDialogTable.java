package com.googlecode.jmoviedb.gui.moviedialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
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

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;

public abstract class MovieDialogTable<T> {

	protected Table table;
	protected TableViewer tableViewer;
	//	private Button closeButton;

	private ArrayList<T> model;

	// column names
	protected String[] columnNames;

	private Image addImage;
	private Image removeImage;
	private Image upImage;
	private Image downImage;

	public MovieDialogTable(Composite parent, String[] columnNames) {
		int iconSize = Math.round(16*MainWindow.DPI_SCALE);
		addImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_ADD).getImageData(100), iconSize, iconSize);
		removeImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_DELETE).getImageData(100), iconSize, iconSize);
		upImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_UP).getImageData(100), iconSize, iconSize);
		downImage = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_DOWN).getImageData(100), iconSize, iconSize);

		Composite c = new Composite(parent, SWT.NULL);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 1;
		gridData.minimumHeight = 100;
		c.setLayoutData(gridData);

		GridLayout layout = new GridLayout(5, false);
		layout.marginWidth = 4;
		c.setLayout(layout);

		this.columnNames = columnNames;

		// Create the table
		createTable(c);

		// Create and setup the TableViewer
		createTableViewer();
		tableViewer.setContentProvider(new AudioSubContentProvider());

		// Add the buttons
		createButtons(c);
	}

	public void setModel(List<T> list) {
		setModel(new ArrayList<T>(list));
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
		CellEditor[] editors = createCellEditors();

		// Assign the cell editors to the viewer
		tableViewer.setCellEditors(editors);

		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(createCellModifier(tableViewer));
        tableViewer.setLabelProvider(createLabelProvider());
	}

	protected abstract ICellModifier createCellModifier(TableViewer tableViewer);

    protected abstract IBaseLabelProvider createLabelProvider();

	protected abstract CellEditor[] createCellEditors();

	/**
	 * Private internal contentprovider
	 */
	private class AudioSubContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

		public void dispose() {}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object parent) {
			return ((ArrayList<T>)parent).toArray();
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
		add.setToolTipText(getAddButtonTooltip());

		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		add.setLayoutData(gridData);
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				model.add(getNewObject());
				tableViewer.refresh();
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
		delete.setImage(removeImage);
		delete.setToolTipText(getDeleteButtonTooltip());

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

	protected abstract String getAddButtonTooltip();

	protected abstract String getDeleteButtonTooltip();

    protected abstract T getNewObject();
}
