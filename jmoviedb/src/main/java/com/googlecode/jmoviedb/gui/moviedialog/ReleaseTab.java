package com.googlecode.jmoviedb.gui.moviedialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.releasetable.IdentifiersTable;
import com.googlecode.jmoviedb.gui.releasetable.MediaTable;
import com.googlecode.jmoviedb.gui.releasetable.TerritoriesTable;
import com.googlecode.jmoviedb.model.Release;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ReleaseTab implements IMovieDialogTab {
    private Text urlText;
    private Text titleText;
    private Text yearText;
    private Release release;
    TerritoriesTable territoriesTable;
    IdentifiersTable identifiersTable;
    MediaTable mediaTable;
    List releaseTypeList;

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab = new CTabItem(tabFolder, SWT.NULL);
		tab.setText("Release");
		Composite c = new Composite(tabFolder, SWT.NULL);

        int layoutColumns = 4;
        GridLayout layout = new GridLayout(layoutColumns, false);
        layout.marginHeight = MovieDialog.MARGIN_HEIGHT;
		layout.marginWidth = MovieDialog.MARGIN_WIDTH;
		layout.verticalSpacing = MovieDialog.VERTICAL_SPACING;
		layout.horizontalSpacing = MovieDialog.HORIZONTAL_SPACING;
		c.setLayout(layout);

        int defaultHorizontalSpan = layoutColumns - 1;

        Label urlLabel = new Label(c, SWT.CENTER);
        urlLabel.setText("URL/ID");
        urlText = new Text(c, SWT.READ_ONLY|SWT.SINGLE|SWT.BORDER);
        urlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, defaultHorizontalSpan, 1));

        Label releaseLabel = new Label(c, SWT.CENTER);
        releaseLabel.setText("Title/Year");
        titleText = new Text(c, SWT.SINGLE|SWT.BORDER);
        titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, defaultHorizontalSpan - 1, 1));
        yearText = new Text(c, SWT.SINGLE|SWT.BORDER);

        Composite t = new Composite(c, SWT.NULL);
		GridLayout layoutT = new GridLayout(2, false);
		t.setLayout(layoutT);
        t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, layoutColumns, 1));
        territoriesTable = new TerritoriesTable(t);
        identifiersTable = new IdentifiersTable(t);
        mediaTable = new MediaTable(t);
        releaseTypeList = new List(t, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

        tab.setControl(c);
    }

    @Override
    public void configureListeners() {
    }

    @Override
    public void setModel(AbstractMovie m) {
        urlText.setText(m.getUrl2String());
        if (m.getUrl2() != null) {
            try {
                release = MainWindow.getMainWindow().getDB().getDatabase().getRelease(m);

                if (release != null) {
                    titleText.setText(release.getReleaseTitle());
                    yearText.setText(release.getReleaseYear() + "");
                    territoriesTable.setModel(release.getTerritories());
                    identifiersTable.setModel(release.getIdentifiers());
                    mediaTable.setModel(release.getMedia());
                    setListModel(release.getReleaseTypes());
                }
                else {
                    titleText.setText(m.getReleaseTitle());
                    yearText.setText(m.getReleaseYear() + "");
                    territoriesTable.setModel(Release.parseTerritories(m.getTerritories(), m.getClassifications()));
                    identifiersTable.setModel(Release.parseIdentifiers(m.getIdentifiers()));
                    mediaTable.setModel(Release.parseMedia(m.getMedia()));
                    setListModel(Release.parseReleaseTypes(m.getReleaseType()));
                }
            }
            catch (SQLException e) {}
        }
    }

    private void setListModel(ArrayList<String> selectedItems) {
        releaseTypeList.removeAll();
        releaseTypeList.setItems(MainWindow.getMainWindow().getDB().getAllReleaseTypes());
        for (String item : selectedItems) {
            releaseTypeList.add(item);
        }
        releaseTypeList.setSelection(selectedItems.toArray(new String[0]));
    }

    @Override
    public void save(AbstractMovie movie) {
        if (release == null)
            release = new Release();
        movie.setUrl2(urlText.getText());
        release.setReleaseTitle(titleText.getText());
        release.setReleaseYear(yearText.getText());
        release.setTerritories(territoriesTable.getModel());
        release.setIdentifiers(identifiersTable.getModel());
        release.setMedia(mediaTable.getModel());
        release.setReleaseTypes(new ArrayList<String>(Arrays.asList(releaseTypeList.getSelection())));
        try {
            MainWindow.getMainWindow().getDB().getDatabase().addUpdateRelease(movie, release);
        }
        catch (SQLException e) {}
    }

    @Override
    public void dispose() {
    }
}
