package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.model.ActorInfo;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ActorsTab implements IMovieDialogTab {
	private Table actorTable;
	private Text directorText;
	private Text writerText;
	private TableColumn actorNameColumn;
	private TableColumn asColumn;
	private TableColumn characterNameColumn;
	private TableColumn idColumn;
	private Image actorTabIcon;

    public ActorsTab(int iconSize) {
        actorTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_ACTORSTAB).createImage(), iconSize, iconSize);
    }

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab3 = new CTabItem(tabFolder, SWT.NULL);
		tab3.setText("Actors, directors and writers");
		tab3.setImage(actorTabIcon);
		
		Composite c3 = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(2, false);
		c3.setLayout(compositeLayout);
		
		Label directorLabel = new Label(c3, SWT.CENTER);
		directorLabel.setText("Directed by:");
		directorText = new Text(c3, SWT.SINGLE|SWT.BORDER);
		directorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label writerLabel = new Label(c3, SWT.CENTER);
		writerLabel.setText("Written by:");
		writerText = new Text(c3, SWT.SINGLE|SWT.BORDER);
		writerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		actorTable = new Table (c3, SWT.BORDER | SWT.MULTI | SWT.SINGLE);
		actorTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		actorTable.setHeaderVisible(true);
		actorTable.setLinesVisible(true);
/*		actorTable.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(MovieDialog.this.getShell(), "Unimplemented feature!",
				"The completed version will do something cool when selecting an actor name, like " +
				"opening the actor's IMDb page or maybe displaying other movies with the same actor.");
			}
		});
*/		actorNameColumn = new TableColumn(actorTable, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		asColumn = new TableColumn(actorTable, SWT.NONE);
		characterNameColumn = new TableColumn(actorTable, SWT.NONE);
		idColumn = new TableColumn(actorTable, SWT.NONE);
		actorNameColumn.setText("Actor");
		asColumn.setText("");
		characterNameColumn.setText("Character");
		idColumn.setText("ID");
		
		//TODO find out why auto column width is a little too small
//		actorNameColumn.setResizable(false);
//		asColumn.setResizable(false);
//		characterNameColumn.setResizable(false);
		
		directorText.setEditable(false);
		writerText.setEditable(false);
		tab3.setControl(c3);
    }

    @Override
    public void configureListeners() {
    }

    @Override
    public void setModel(AbstractMovie m) {
		directorText.setText(m.getDirectorsAsString());
		writerText.setText(m.getWritersAsString());
		
		actorTable.removeAll();
		for(ActorInfo a : m.getActors()) {
			TableItem i = new TableItem(actorTable, SWT.NONE);
			i.setText(0, a.getPerson().getName());
			i.setText(1, "as");
			i.setText(2, a.getCharacter());
			i.setText(3, ""+a.getPerson().getID());
		}

		actorNameColumn.pack();
		asColumn.pack();
		characterNameColumn.pack();
		idColumn.pack();
    }

    @Override
    public void save(AbstractMovie movie) {
    }

    @Override
    public void dispose() {
		actorTabIcon.dispose();
    }
}
