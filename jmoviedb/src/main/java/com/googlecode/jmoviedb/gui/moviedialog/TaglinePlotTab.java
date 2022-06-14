package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.JSONObject;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.gui.MovieDialog;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class TaglinePlotTab implements IMovieDialogTab, ModifyListener {
	private Image taglineTabIcon;
    private Text taglineText;
	private Text plotText;
	private Button jsonCheck;
	private Text notesText;

    public TaglinePlotTab(int iconSize) {
        taglineTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_TAGLINEPLOTTAB).createImage(), iconSize, iconSize);
    }

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab2 = new CTabItem(tabFolder, SWT.NULL);
		tab2.setText("Tagline and plot");
		tab2.setImage(taglineTabIcon);
		
		Composite c2 = new Composite(tabFolder, SWT.NULL);
		GridLayout gridLayout2 = new GridLayout(2, false);
		gridLayout2.marginHeight = MovieDialog.MARGIN_HEIGHT;
		gridLayout2.marginWidth = MovieDialog.MARGIN_WIDTH;
		gridLayout2.verticalSpacing = MovieDialog.VERTICAL_SPACING;
		gridLayout2.horizontalSpacing = MovieDialog.HORIZONTAL_SPACING;
		c2.setLayout(gridLayout2);
		
		Label taglineLabel = new Label(c2, SWT.LEFT);
		taglineLabel.setText("Tagline:");
		GridData gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		gd.widthHint = 70;
		taglineLabel.setLayoutData(gd);
		taglineText = new Text(c2, SWT.SINGLE|SWT.BORDER);
		taglineText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label plotLabel = new Label(c2, SWT.LEFT);
		plotLabel.setText("Plot outline:");
		gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		gd.widthHint = 70;
		plotLabel.setLayoutData(gd);
		plotText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		plotText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label notesLabel = new Label(c2, SWT.LEFT);
		notesLabel.setText("Notes:");
		gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		gd.widthHint = 70;
		notesLabel.setLayoutData(gd);
		notesText = new Text(c2, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		notesText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		jsonCheck = new Button(c2, SWT.CHECK);
		jsonCheck.setText("Valid JSON");
		jsonCheck.setEnabled(false);
		
		tab2.setControl(c2);
    }
    
    @Override
    public void configureListeners() {
        notesText.addModifyListener(this);
    }

    @Override
    public void setModel(AbstractMovie m) {
		taglineText.setText(m.getTagline());
		plotText.setText(m.getPlotOutline());
		notesText.setText(m.getNotes());
    }

    @Override
    public void save(AbstractMovie movie) {
		movie.setTagline(taglineText.getText());
		movie.setPlotOutline(plotText.getText());
		movie.setNotes(notesText.getText());
    }

    @Override
    public void dispose() {
		taglineTabIcon.dispose();
    }

    @Override
    public void modifyText(ModifyEvent event) {
		try {
			new JSONObject(((Text)event.widget).getText());
			jsonCheck.setSelection(true);
		}
		catch (Exception e) {
			jsonCheck.setSelection(false);
		}
    }    
}
