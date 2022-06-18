package com.googlecode.jmoviedb.gui.moviedialog;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.releasetable.TerritoriesTable;
import com.googlecode.jmoviedb.model.Release;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ReleaseTab implements IMovieDialogTab {
    private Text urlText;
    private Text titleText;
    private Text yearText;
    private Release release;
    TerritoriesTable territoriesTable;

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab = new CTabItem(tabFolder, SWT.NULL);
		tab.setText("Release");
		Composite c = new Composite(tabFolder, SWT.NULL);

        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = MovieDialog.MARGIN_HEIGHT;
		layout.marginWidth = MovieDialog.MARGIN_WIDTH;
		layout.verticalSpacing = MovieDialog.VERTICAL_SPACING;
		layout.horizontalSpacing = MovieDialog.HORIZONTAL_SPACING;
		c.setLayout(layout);

        int defaultHorizontalSpan = 2;

        Label urlLabel = new Label(c, SWT.CENTER);
        urlLabel.setText("URL/ID");
        urlText = new Text(c, SWT.READ_ONLY|SWT.SINGLE|SWT.BORDER);
        urlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, defaultHorizontalSpan, 1));
        urlText.setEnabled(false);

        Label releaseLabel = new Label(c, SWT.CENTER);
        releaseLabel.setText("Title/Year");
        titleText = new Text(c, SWT.SINGLE|SWT.BORDER);
        titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, defaultHorizontalSpan - 1, 1));
        yearText = new Text(c, SWT.SINGLE|SWT.BORDER);

        territoriesTable = new TerritoriesTable(c);

        tab.setControl(c);
    }

    @Override
    public void configureListeners() {
        // TODO Auto-generated method stub

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
                }
                else {
                    titleText.setText(m.getReleaseTitle());
                    yearText.setText(m.getReleaseYear() + "");
                    territoriesTable.setModel(Release.parseTerritories(m.getTerritories(), m.getClassifications()));
                }
            }
            catch (SQLException e) {}
        }
    }

    @Override
    public void save(AbstractMovie movie) {
        if (release == null)
            release = new Release();
        release.setReleaseTitle(titleText.getText());
        release.setReleaseYear(yearText.getText());
        release.setTerritories(territoriesTable.getModel());
        try {
            MainWindow.getMainWindow().getDB().getDatabase().addUpdateRelease(movie, release);
        }
        catch (SQLException e) {}
    }

    @Override
    public void dispose() {
    }
}
