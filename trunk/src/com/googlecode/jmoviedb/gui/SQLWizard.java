package com.googlecode.jmoviedb.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class SQLWizard extends Wizard {
	// Minimum dialog width (in dialog units) (value from class TitleAreaDialog)
	private static final int MIN_DIALOG_WIDTH = 350;
	
	public static final String PAGE1 = "Run custom SQL query";
	public static final String PAGE2 = "SQL results";
	
	private int labelWidth;
	private Text sqlText;
	private TableViewer tableViewer;
	private Table table;
	private boolean canFinish;
	
	public SQLWizard() {
		super();
		setWindowTitle("Custom SQL Query");
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		Composite c = new Composite(getShell(), SWT.NONE);
		GC gc = new GC(c);
		gc.setFont(c.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		c.dispose();
		
		labelWidth = Dialog.convertHorizontalDLUsToPixels(fontMetrics, MIN_DIALOG_WIDTH);
		System.out.println("labelWidth "+labelWidth);

		addPage(new WizardPage(PAGE1) {
			{ setTitle(getName()); setPageComplete(true); }
			public void createControl(Composite parent) {
				Composite c =  new Composite(parent, SWT.BORDER);
				c.setLayout(new GridLayout(1, false));
				Label infoLabel = new Label(c, SWT.WRAP);
				infoLabel.setText("Run a custom SQL query against the currently opened database. " +
						"WARNING: This may crash the application or destroy your data! Only use it " +
						"if you really know what you are doing.");
				infoLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
				sqlText = new Text(c, SWT.BORDER|SWT.MULTI|SWT.WRAP|SWT.V_SCROLL);
				sqlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				setControl(c);
			}
		});
		addPage(new WizardPage(PAGE2) {
			{ setTitle(getName()); setPageComplete(true); }
			public void createControl(Composite parent) {
				Composite c =  new Composite(parent, SWT.BORDER);
				c.setLayout(new GridLayout(1, false));
				table = new Table(c, SWT.V_SCROLL|SWT.H_SCROLL|SWT.SINGLE|SWT.FULL_SELECTION|SWT.HIDE_SELECTION);
				table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				table.setLinesVisible(true);
				table.setHeaderVisible(true);
				
				tableViewer = new TableViewer(table);
				tableViewer.setUseHashlookup(true);
				
				tableViewer.setContentProvider(new ArrayContentProvider());
				tableViewer.setLabelProvider(new ITableLabelProvider(){
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						return ((String[])element)[columnIndex];
					}
					public void addListener(ILabelProviderListener listener) {}
					public void dispose() {}

					public boolean isLabelProperty(Object element, String property) {
						System.out.println("isLabelProperty "+element+" "+property);
						return false;
					}
					public void removeListener(ILabelProviderListener listener) {}
				});
				
				setControl(c);
			}
		});
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	public String getQuery() {
		return sqlText.getText();
	}
	
	@Override
	public boolean canFinish() {
		return canFinish;
	}
	
	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}
	
	public void createColumns(String[] headers) {
		for(TableColumn col : table.getColumns())
			col.dispose();
		TableColumn column;
		for(int i=0; i<headers.length; i++) {
			column = new TableColumn(table, SWT.LEFT, i);
			column.setText(headers[i]);
			column.setWidth(40);
		}
		
		tableViewer.setColumnProperties(headers);
	}
	
	public void setInput(String[][] input) {
		tableViewer.setInput(input);
		for(TableColumn col : table.getColumns()) {
			col.pack();
		}
	}
}
