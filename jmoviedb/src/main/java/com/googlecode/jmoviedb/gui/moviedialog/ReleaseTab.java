package com.googlecode.jmoviedb.gui.moviedialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.Utils;
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
    Button saveButton;
    Button loadButton;

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
        urlText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                String text = ((Text)event.widget).getText();
                boolean isvalid = Utils.isNullOrEmpty(text);
                setEnabled(isvalid);
                if (isvalid) {
                    Release release;
                    try {
                        release = MainWindow.getMainWindow().getDB().getDatabase().getRelease(text);
                        if (release == null) {
                            setReleaseModel(release);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setEnabled(boolean enabled) {
        titleText.setEnabled(enabled);
        yearText.setEnabled(enabled);
        releaseTypeList.setEnabled(enabled);
    }

    @Override
    public void setModel(AbstractMovie m) {
        urlText.setText(m.getUrl2String());
        if (m.getUrl2() != null) {
            try {
                Release release = MainWindow.getMainWindow().getDB().getDatabase().getRelease(m.getUrl2StringOrNull());
                if (release == null) {
                    release = new Release();
                    release.setReleaseTitle(m.getReleaseTitle());
                    release.setReleaseYear(m.getReleaseYear());
                    release.setTerritoriesJson(m.getTerritories(), m.getClassifications());
                    release.setIdentifiersJson(m.getIdentifiers());
                    release.setMediaJson(m.getMedia());
                    release.setReleaseTypesJson(m.getReleaseType());
                }
                setReleaseModel(release);
            }
            catch (SQLException e) {}
        }
    }

    private void setReleaseModel(Release release) {
        this.release = release;
        titleText.setText(release.getReleaseTitle());
        yearText.setText(release.getReleaseYear() + "");
        territoriesTable.setModel(release.getTerritories());
        identifiersTable.setModel(release.getIdentifiers());
        mediaTable.setModel(release.getMedia());
        setListModel(release.getReleaseTypes());
    }

    private void setListModel(ArrayList<String> selectedItems) {
        releaseTypeList.removeAll();

        String[] releaseTypeOptions = Stream.concat(
            Arrays.stream(MainWindow.getMainWindow().getDB().getAllReleaseTypes()),
            selectedItems.stream()
        ).distinct().toArray(String[]::new);

        releaseTypeList.setItems(releaseTypeOptions);
        releaseTypeList.setSelection(selectedItems.toArray(new String[0]));
    }

    @Override
    public void save(AbstractMovie movie) {
        if (release == null)
            release = new Release();
        movie.setUrl2(urlText.getText());
        saveRelease(movie);
    }

    private void saveRelease(AbstractMovie movie) {
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
