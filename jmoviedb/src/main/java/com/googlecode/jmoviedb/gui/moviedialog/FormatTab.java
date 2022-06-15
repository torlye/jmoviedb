package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.Utils;
import com.googlecode.jmoviedb.enumerated.*;
import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public class FormatTab implements IMovieDialogTab {
    private Combo colour;
	private Button legalCheck;
	private Combo discCombo;
	private Text locationText;
//	private Button myEncodeCheck;
	private Text sceneNameText;
	private Combo aspectCombo;
//	private Text url1Text;
	private Text url2Text;
	
	private Combo formatCombo;
	private Combo videoCodecCombo;
	private Combo containerCombo;
	private Combo resolutionCombo;
	private Combo tvSystemCombo;
	private Button r0;
	private Button r1;
	private Button r2;
	private Button r3;
	private Button r4;
	private Button r5;
	private Button r6;
	private Button r7;
	private Button r8;
    private Image formatTabIcon;
	private Label regionLabel;
	private SelectionListener formatComboListener;
	private SelectionListener legalCheckListener;
//	private SelectionListener myEncodeCheckListener;
	private SelectionListener r0CheckListener;
	
    public FormatTab(int iconSize) {
        formatTabIcon = Utils.resizePreserveAspect(ImageDescriptor.createFromURL(CONST.ICON_MOVIEDIALOG_FORMATTAB).createImage(), iconSize, iconSize);
    }

    @Override
    public void createTabArea(CTabFolder tabFolder) {
        CTabItem tab4 = new CTabItem(tabFolder, SWT.NULL);
		tab4.setText("Format and video");
		tab4.setImage(formatTabIcon);
		
		Composite c = new Composite(tabFolder, SWT.NULL);
		GridLayout compositeLayout = new GridLayout(10, false);
		compositeLayout.makeColumnsEqualWidth = false;
		c.setLayout(compositeLayout);
		
		int comboHorizontalAlignment = SWT.BEGINNING;
		boolean comboGrabExcessHorizontalSpace = true;
		int comboHorizontalSpan = 9;
		
		Label formatLabel = new Label(c, SWT.CENTER);
		formatLabel.setText("Format:");
		formatCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		formatCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		formatCombo.setItems(FormatType.getStringArray());
		formatCombo.select(0);
		formatCombo.setVisibleItemCount(FormatType.getStringArray().length); //make all items visible
		
		Label containerLabel = new Label(c, SWT.CENTER);
		containerLabel.setText("Container format:");
		containerCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		containerCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		containerCombo.setItems(ContainerFormat.getStringArray());
		containerCombo.select(0);
		containerCombo.setVisibleItemCount(ContainerFormat.getStringArray().length); //make all items visible
		
		Label videoCodecLabel = new Label(c, SWT.CENTER);
		videoCodecLabel.setText("Video format:");
		videoCodecCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		videoCodecCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		videoCodecCombo.setItems(VideoCodec.getStringArray());
		videoCodecCombo.select(0);
		videoCodecCombo.setVisibleItemCount(VideoCodec.getStringArray().length); //make all items visible
		
		Label resolutionLabel = new Label(c, SWT.CENTER);
		resolutionLabel.setText("Video resolution:");
		resolutionCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		resolutionCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		resolutionCombo.setItems(Resolution.getStringArray());
		resolutionCombo.select(0);
		resolutionCombo.setVisibleItemCount(Resolution.getStringArray().length); //make all items visible
		
		Label aspectLabel = new Label(c, SWT.CENTER);
		aspectLabel.setText("Aspect ratio");
		aspectCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		aspectCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		aspectCombo.setItems(AspectRatio.getStringArray());
		aspectCombo.select(0);
		aspectCombo.setVisibleItemCount(AspectRatio.getStringArray().length); //make all items visible
		
		Label tvSystemLabel = new Label(c, SWT.CENTER);
		tvSystemLabel.setText("TV system:");
		tvSystemCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		tvSystemCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, false, false, comboHorizontalSpan-4, 1));
		tvSystemCombo.setItems(TVsystem.getStringArray());
		tvSystemCombo.select(0);
		tvSystemCombo.setVisibleItemCount(TVsystem.getStringArray().length); //make all items visible
		
		Label colorLabel = new Label(c, SWT.CENTER);
		colorLabel.setText("Colour");
		colour = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		colour.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, false, false, 3, 1));
		colour.setItems(ColorFormat.getAllFormatsStringArray());
		
		regionLabel = new Label(c, SWT.CENTER);
		regionLabel.setText("Region:");
		r0 = new Button(c, SWT.CHECK);
		r0.setText("Region free");
		r1 = new Button(c, SWT.CHECK);
		r1.setText("R1");
		r2 = new Button(c, SWT.CHECK);
		r2.setText("R2");
		r3 = new Button(c, SWT.CHECK);
		r3.setText("R3");
		r4 = new Button(c, SWT.CHECK);
		r4.setText("R4");
		r5 = new Button(c, SWT.CHECK);
		r5.setText("R5");
		r6 = new Button(c, SWT.CHECK);
		r6.setText("R6");
		r7 = new Button(c, SWT.CHECK);
		r7.setText("R7");
		r8 = new Button(c, SWT.CHECK);
		r8.setText("R8");
		
		Label discLabel = new Label(c, SWT.CENTER);
		discLabel.setText("Storage medium:");
		discCombo = new Combo(c, SWT.DROP_DOWN|SWT.READ_ONLY);
		discCombo.setLayoutData(new GridData(comboHorizontalAlignment, SWT.CENTER, comboGrabExcessHorizontalSpace, false, comboHorizontalSpan, 1));
		discCombo.setItems(DiscType.getStringArray());
		discCombo.select(0);
		discCombo.setVisibleItemCount(DiscType.getStringArray().length); //make all items visible
		
		Label locationLabel = new Label(c, SWT.CENTER);
		locationLabel.setText("Storage location:");
		locationText = new Text(c, SWT.SINGLE|SWT.BORDER);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		legalCheck = new Button(c, SWT.CHECK);
		legalCheck.setText("Original");
		legalCheck.setSelection(true);
		legalCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, comboHorizontalSpan+1, 1));
		
//		myEncodeCheck = new Button(c, SWT.CHECK);
//		myEncodeCheck.setText("Encoded by me");
//		myEncodeCheck.setSelection(true);
//		myEncodeCheck.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, comboHorizontalSpan+1, 1));
		
		Label sceneNameLabel = new Label(c, SWT.CENTER);
		sceneNameLabel.setText("Scene release name:");
		sceneNameText = new Text(c, SWT.SINGLE|SWT.BORDER);
		sceneNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
//		Label urlLabel1 = new Label(c, SWT.CENTER);
//		urlLabel1.setText("Discogs URL");
//		url1Text = new Text(c, SWT.SINGLE|SWT.BORDER);
//		url1Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		Label urlLabel2 = new Label(c, SWT.CENTER);
		urlLabel2.setText("blu-ray.com/lddb.com");
		url2Text = new Text(c, SWT.SINGLE|SWT.BORDER);
		url2Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, comboHorizontalSpan, 1));
		
		tab4.setControl(c);
    }

    @Override
    public void configureListeners() {
        formatComboListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				//Reset
				videoCodecCombo.setEnabled(true);
				containerCombo.setEnabled(true);
				resolutionCombo.setEnabled(true);
				discCombo.setEnabled(true);
				regionLabel.setVisible(false);
				r0.setVisible(false);
				r1.setVisible(false);
				r2.setVisible(false);
				r3.setVisible(false);
				r4.setVisible(false);
				r5.setVisible(false);
				r6.setVisible(false);
				r7.setVisible(false);
				r8.setVisible(false);
				
				FormatType selectedValue = FormatType.values()[formatCombo.getSelectionIndex()];
				
				int selectedColor = colour.getSelectionIndex();
				colour.setItems(
					selectedValue == FormatType.other || selectedValue == FormatType.file || selectedValue == FormatType.uhdbluray 
					? ColorFormat.getAllFormatsStringArray() : ColorFormat.getSDRFormatsStringArray());
				if (selectedColor < colour.getItemCount())
					colour.select(selectedColor);
				else
					colour.select(0);
				
				if(selectedValue == FormatType.dvd) {
					videoCodecCombo.select(VideoCodec.mpeg2.ordinal());
					containerCombo.select(ContainerFormat.vob.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.dvd.ordinal());
					
					videoCodecCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r4.setVisible(true);
					r5.setVisible(true);
					r6.setVisible(true);
					r7.setVisible(true);
					r8.setVisible(true);
					r1.setText("R1");
					r2.setText("R2");
					r3.setText("R3");
					
				} else if(selectedValue == FormatType.bluray || selectedValue == FormatType.bluray3d) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					discCombo.select(DiscType.bd.ordinal());
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r1.setText("A");
					r2.setText("B");
					r3.setText("C");
				} else if(selectedValue == FormatType.uhdbluray) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					discCombo.select(DiscType.uhdbd.ordinal());
					
				} else if(selectedValue == FormatType.hddvd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					discCombo.select(DiscType.hddvd.ordinal());
					containerCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.avchd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					containerCombo.setEnabled(false);
					videoCodecCombo.select(VideoCodec.h264.ordinal());
					videoCodecCombo.setEnabled(false);
					if (discCombo.getSelectionIndex() == 0)
						discCombo.select(DiscType.dvdminusr.ordinal());
				} else if(selectedValue == FormatType.vcd) {
					containerCombo.select(ContainerFormat.mpeg.ordinal());
					videoCodecCombo.select(VideoCodec.mpeg1.ordinal());
					resolutionCombo.select(Resolution.cif.ordinal());
					discCombo.select(DiscType.cd.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
				}  else if(selectedValue == FormatType.svcd) {
					containerCombo.select(ContainerFormat.mpeg.ordinal());
					videoCodecCombo.select(VideoCodec.mpeg2.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.cd.ordinal());
					
					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.laserdisc) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.ld.ordinal());
					
					containerCombo.setEnabled(false);
					videoCodecCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.vhs) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.vhs.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.betamax) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.betamax.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
				} else if(selectedValue == FormatType.video8) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.analog.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.video8.ordinal());

					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);

				} else if(selectedValue == FormatType.umd) {
					containerCombo.select(ContainerFormat.medianative.ordinal());
					videoCodecCombo.select(VideoCodec.h264.ordinal());
					resolutionCombo.select(Resolution.sd.ordinal());
					discCombo.select(DiscType.umd.ordinal());
					
					containerCombo.setEnabled(false);
					containerCombo.setEnabled(false);
					resolutionCombo.setEnabled(false);
					discCombo.setEnabled(false);
					
					regionLabel.setVisible(true);
					r0.setVisible(true);
					r1.setVisible(true);
					r2.setVisible(true);
					r3.setVisible(true);
					r4.setVisible(true);
					r5.setVisible(true);
					r6.setVisible(true);
				}
			}
		};
		formatCombo.addSelectionListener(formatComboListener);
		
		legalCheckListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if(legalCheck.getSelection() == true) {
					sceneNameText.setEnabled(false);
//					myEncodeCheck.setSelection(false);
//					myEncodeCheck.setEnabled(false);
				} else {
					sceneNameText.setEnabled(true);
//					myEncodeCheck.setEnabled(true);
				}
			}
		};
		legalCheck.addSelectionListener(legalCheckListener);
		
//		myEncodeCheckListener = new SelectionListener() {
//			public void widgetDefaultSelected(SelectionEvent e) {}
//			public void widgetSelected(SelectionEvent e) {
//				if(myEncodeCheck.getSelection() == true) {
//					sceneNameText.setText("");
//					sceneNameText.setEnabled(false);
//					legalCheck.setSelection(false);
//					legalCheck.setEnabled(false);
//				} else {
//					sceneNameText.setEnabled(true);
//					legalCheck.setEnabled(true);
//				}
//			}
//		};
//		myEncodeCheck.addSelectionListener(myEncodeCheckListener);
		
		r0CheckListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				boolean value = !r0.getSelection();
				r1.setEnabled(value);
				r2.setEnabled(value);
				r3.setEnabled(value);
				r4.setEnabled(value);
				r5.setEnabled(value);
				r6.setEnabled(value);
				r7.setEnabled(value);
				r8.setEnabled(value);
				if(value == false) {
					r1.setSelection(false);
					r2.setSelection(false);
					r3.setSelection(false);
					r4.setSelection(false);
					r5.setSelection(false);
					r6.setSelection(false);
					r7.setSelection(false);
					r8.setSelection(false);
				}
			}
		};
		r0.addSelectionListener(r0CheckListener);
    }

    @Override
    public void setModel(AbstractMovie m) {
        formatCombo.select(m.getFormat().ordinal());
		containerCombo.select(m.getContainer().ordinal());
		videoCodecCombo.select(m.getVideo().ordinal());
		resolutionCombo.select(m.getResolution().ordinal());
		tvSystemCombo.select(m.getTvSystem().ordinal());
		r0.setSelection(m.getDvdRegion()[0]);
		r1.setSelection(m.getDvdRegion()[1]);
		r2.setSelection(m.getDvdRegion()[2]);
		r3.setSelection(m.getDvdRegion()[3]);
		r4.setSelection(m.getDvdRegion()[4]);
		r5.setSelection(m.getDvdRegion()[5]);
		r6.setSelection(m.getDvdRegion()[6]);
		r7.setSelection(m.getDvdRegion()[7]);
		r8.setSelection(m.getDvdRegion()[8]);
		legalCheck.setSelection(m.isLegal());
		discCombo.select(m.getDisc().ordinal());
		aspectCombo.select(m.getAspectRatio().ordinal());
		locationText.setText(m.getLocation());
//		myEncodeCheck.setSelection(m.isMyEncode());
		sceneNameText.setText(m.getSceneReleaseName());
//		url1Text.setText(m.getUrl1String());
		url2Text.setText(m.getUrl2String());
        
		formatComboListener.widgetSelected(null);
		legalCheckListener.widgetSelected(null);
//		myEncodeCheckListener.widgetSelected(null);
		r0CheckListener.widgetSelected(null);

		colour.select(m.getColor().ordinal());
    }

    @Override
    public void save(AbstractMovie movie) {
        movie.setFormat(FormatType.values()[formatCombo.getSelectionIndex()]);
		movie.setContainer(ContainerFormat.values()[containerCombo.getSelectionIndex()]);
		movie.setVideo(VideoCodec.values()[videoCodecCombo.getSelectionIndex()]);
		movie.setResolution(Resolution.values()[resolutionCombo.getSelectionIndex()]);
		movie.setTvSystem(TVsystem.values()[tvSystemCombo.getSelectionIndex()]);
		movie.setDvdRegion(new boolean[]
				{	r0.getSelection(), 
					r1.getSelection(),
					r2.getSelection(),
					r3.getSelection(),
					r4.getSelection(),
					r5.getSelection(),
					r6.getSelection(),
					r7.getSelection(),
					r8.getSelection(),
				});
		movie.setColor(ColorFormat.values()[colour.getSelectionIndex()]);
		movie.setLegal(legalCheck.getSelection());
		movie.setDisc(DiscType.values()[discCombo.getSelectionIndex()]);
		movie.setAspectRatio(AspectRatio.values()[aspectCombo.getSelectionIndex()]);
		movie.setLocation(locationText.getText());
		movie.setSceneReleaseName(sceneNameText.getText());
//		movie.setUrl1(url1Text.getText());
		movie.setUrl2(url2Text.getText());
//		movie.setMyEncode(myEncodeCheck.getSelection());
    }

    @Override
    public void dispose() {
        formatTabIcon.dispose();   
    }
    
    public Combo getFormatCombo() {
        return formatCombo;
    }
}
