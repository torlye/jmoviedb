package com.googlecode.jmoviedb.gui.moviedialog;

import java.sql.SQLException;
import java.util.stream.Collectors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MainWindow;
import com.googlecode.jmoviedb.gui.releasetable.CompaniesTable;
import com.googlecode.jmoviedb.gui.releasetable.IdentifiersTable;
import com.googlecode.jmoviedb.gui.releasetable.MediaTable;
import com.googlecode.jmoviedb.gui.releasetable.ReleaseTypeTable;
import com.googlecode.jmoviedb.gui.releasetable.StringBufferTable;
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
    StringBufferTable releaseTypeTable;
    StringBufferTable companiesTable;
    private AbstractMovie movie;
    Image tabIcon;

    public ReleaseTab(int iconSize) {
        tabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_RELEASETAB).createImage(), iconSize, iconSize);
    }

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab = new CTabItem(tabFolder, SWT.NULL);
		tab.setText("Release");
        tab.setImage(tabIcon);
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
        urlText = new Text(c, SWT.SINGLE|SWT.BORDER);
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
        releaseTypeTable = new ReleaseTypeTable(t);
        companiesTable = new CompaniesTable(t);

        tab.setControl(c);
    }

    @Override
    public void configureListeners() {
        urlText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent event) {
                String text = ((Text)event.widget).getText();
                boolean isvalid = !Utils.isNullOrEmpty(text);
                setEnabled(isvalid);
                if (isvalid) {
                    try {
                        Release release = MainWindow.getMainWindow().getDB().getDatabase().getRelease(text);
                        if (release == null && movie.isJsonNote())
                            release = new Release(movie);

                        setReleaseModel(release != null ? release : new Release());
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
        territoriesTable.setEnabled(enabled);
        identifiersTable.setEnabled(enabled);
        mediaTable.setEnabled(enabled);
        releaseTypeTable.setEnabled(enabled);
        companiesTable.setEnabled(enabled);
    }

    @Override
    public void setModel(AbstractMovie m) {
        movie = m;
        urlText.setText(m.getUrl2String());
        if (m.getUrl2() != null) {
            try {
                Release release = MainWindow.getMainWindow().getDB().getDatabase().getRelease(m.getUrl2StringOrNull());
                if (release == null) {
                    release = new Release(m);
                }
                setReleaseModel(release);
            }
            catch (SQLException e) {}
        }
    }

    private void setReleaseModel(Release release) {
        this.release = release;
        titleText.setText(release.getReleaseTitle());
        yearText.setText(release.getReleaseYear() == null ? "" : (release.getReleaseYear() + ""));
        territoriesTable.setModel(release.getTerritories());
        identifiersTable.setModel(release.getIdentifiers());
        mediaTable.setModel(release.getMedia());
        releaseTypeTable.setModel(release.getReleaseTypes().stream().map(s -> new StringBuffer(s)).collect(Collectors.toList()));
        companiesTable.setModel(release.getCompanies().stream().map(s -> new StringBuffer(s)).collect(Collectors.toList()));
    }

    @Override
    public void save(AbstractMovie movie) {
        String text = urlText.getText();
        movie.setUrl2(text);
        if (!Utils.isNullOrEmpty(text))
            saveRelease();
    }

    private void saveRelease() {
        if (release == null)
            release = new Release();
        release.setReleaseTitle(titleText.getText());
        release.setReleaseYear(yearText.getText());
        release.setTerritories(territoriesTable.getModel());
        release.setIdentifiers(identifiersTable.getModel());
        release.setMedia(mediaTable.getModel());
        release.setReleaseTypes(releaseTypeTable.getModel().stream().map(b -> b.toString()).collect(Collectors.toList()));
        release.setCompanies(companiesTable.getModel().stream().map(b -> b.toString()).collect(Collectors.toList()));
        try {
            MainWindow.getMainWindow().getDB().addUpdateRelease(urlText.getText(), release);
            MainWindow.getMainWindow().getDB().reloadReleases();
        }
        catch (SQLException e) {}
    }

    @Override
    public void dispose() {
        tabIcon.dispose();
    }
}
