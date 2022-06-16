package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.JSONObject;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class ReleaseTab implements IMovieDialogTab {
    private Text urlText;
    private Text titleText;
    private Text yearText;

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
		
        tab.setEnabled(false);
        tab.setControl(c);
    }

    @Override
    public void configureListeners() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setModel(AbstractMovie m) {
        urlText.setText(m.getUrl2String());
        titleText.setText(m.getReleaseTitle());
        yearText.setText(m.getReleaseYear() + "");
    }

    @Override
    public void save(AbstractMovie movie) {
        movie.setReleaseTitle(titleText.getText());
        movie.setReleaseYear(yearText.getText());
    }

    @Override
    public void dispose() {
    }
    
}
