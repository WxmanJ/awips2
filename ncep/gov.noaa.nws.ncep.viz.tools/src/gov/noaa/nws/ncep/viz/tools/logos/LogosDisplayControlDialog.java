package gov.noaa.nws.ncep.viz.tools.logos;

import gov.noaa.nws.ncep.viz.ui.display.NmapUiUtils;
import gov.noaa.nws.ncep.viz.localization.impl.LocalizationManager;
import gov.noaa.nws.ncep.viz.localization.impl.LocalizationResourcePathConstants;
import gov.noaa.nws.ncep.viz.tools.logos.LogoInfo.LogoEntry;
import gov.noaa.nws.ncep.viz.ui.display.NCMapEditor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import com.raytheon.uf.viz.core.exception.VizException;
import com.raytheon.uf.viz.core.rsc.LoadProperties;
import com.raytheon.uf.viz.core.drawables.ResourcePair;
import com.raytheon.uf.viz.core.rsc.ResourceList;


/**
 * This class controls Logos display in National Centers perspective.
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * June 2009  	105        M. Li    	Initial creation. 
 * Nov  2009               G. Hull      migrate to to11d6
 * </pre>
 * 
 * @author mli
 * @version 1.0
 * 
 */


public class LogosDisplayControlDialog extends Dialog {

	/**
     * Dialog shell.
     */
    private Shell shell;
	
    Display display; 
    /**
     * Return object.
     */
    private final Boolean returnValue = false;
	
    private static final int SCALE_WIDTH = 150;

    private static final int LABEL_WIDTH = 35;
    
    private static final int DEF_HEIGHT = 40;
    
    private static final int ICON_WIDTH = 60;
    
    private static final int MAX_SCALER_VALUE = 300;
    
    private static final int MIN_SCALER_VALUE = 50;
    
    protected NCMapEditor theEditor;
    
    protected LogosResource logosResource;
	
    private LogoInfo logoInfo;
    
    private static List<LogoEntry> logoList = null;
    
    private final int MAX_LOCATION = 5;
    
    private final int INIT_POSITION = 0;
    
    private final int INIT_SCALE = 100;
    
    private final String[] locStr = {"OFF", "LL", "UL", "UR", "LR"};
    
    private Button[][] locButtons;
    
    private Scale[] scale;
    
    private Label[] scaleLabel;
    
    private int[] logoPosition = null;
    
    private int[] logoScale = null;
    
    
    
	protected LogosDisplayControlDialog(Shell parentShell) {
		super(parentShell);
		
	}

	/**
     * Open method used to display the Time Series dialog.
     * 
     * @return Return object (can be null).
     */
    public Object open() {
        Shell parent = getParent();
        display = parent.getDisplay();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Logos Display Controls");
        
		
        // Create the main layout for the shell.
        GridLayout mainLayout = new GridLayout(1, true);
        mainLayout.marginHeight = 1;
        mainLayout.marginWidth = 1;
        shell.setLayout(mainLayout);
        Rectangle rect = parent.getBounds();
        Point pt = new Point((int)(0.3 * rect.width), (int)(0.3 * rect.height));
        shell.setLocation(pt.x, pt.y);


        // Initialize all of the controls and layouts
        readLogoTable();
        initializeComponents();

        shell.pack();
        shell.open();
        
        //parent.setCursor(arrowCursor);
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        return returnValue;
    }
    
    /**
     * Initialize the dialog components.
     */
    private void initializeComponents() {
    	
    	createDisplayControls();
    	addSeparator();
    	createCloseButton();
    	
    	initialize();
    }
    
    /**
     * Create the tab folder container.
     */
    private void createDisplayControls() {
    	Composite comp = new Composite(shell, SWT.NONE);
        GridLayout gl = new GridLayout(4, false);
        comp.setLayout(gl);

        // Headers
        new Label(comp, SWT.BORDER).setText("   LOGO     ");
    	new Label(comp, SWT.BORDER).setText("                   POSITION                    ");
    	new Label(comp, SWT.BORDER).setText("              SCALE                 ");
    	new Label(comp, SWT.NONE).setText("");
        
        // Display controls
        scale = new Scale[logoList.size()];
        locButtons = new Button[logoList.size()][MAX_LOCATION];
        scaleLabel = new Label[logoList.size()];
        for (int i = 0; i < logoList.size(); i++) {
        	
        	// Logo Image label
        	/*
        	 * comment out by M. Gao
        	 */
//        	String logofile = LocalizationManager.getInstance().getFilename("logosImageDir") + File.separator
//        		+ logoList.get(i).getImageFile();
        	String logofileName = LocalizationManager.getInstance().getLocalizationFileNameDirectly(LocalizationResourcePathConstants.LOGO_IMAGES_DIR,
        			logoList.get(i).getImageFile()); 
        	Label imageLabel = new Label(comp, SWT.NONE);
        	Image image = new Image(display, logofileName);
        	
        	double ratio = 1.0;
        	if ( image.getBounds().width > 0) {
        		ratio = (double) (1.0 * image.getBounds().height / image.getBounds().width);
        	}	
        	int width = ICON_WIDTH;
        	int height = (int) (width * ratio);
        	if ( height <= 0) height = width;
        	
        	Image newImage = new Image( display, image.getImageData()
        			.scaledTo(width, height) );
        	imageLabel.setImage(newImage);
        	
        	// Logo Location
        	Composite buttonComp = new Composite(comp, SWT.NONE);
        	GridLayout gd = new GridLayout(MAX_LOCATION, false);
            buttonComp.setLayout(gd);
            
        	locButtons[i] = new Button[MAX_LOCATION];
        	for (int j = 0; j < MAX_LOCATION; j++) {
        		locButtons[i][j] = new Button(buttonComp, SWT.RADIO);
        		locButtons[i][j].setText(locStr[j]);
        		int data = i * (j + 1);
        		locButtons[i][j].setData(data);
        		locButtons[i][j].addSelectionListener(new SelectionListener() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						int location = 0;
						for (int ii = 0; ii < MAX_LOCATION; ii++) {
							if (e.getSource().toString().contains(locStr[ii])) {
								location = ii;
								break;
							}
						}
						
						int whichLogo =   Integer.parseInt(e.widget.getData().toString()) / (location + 1);
						logoPosition[whichLogo] = location;

						update();
					}
        		});
        	}
        	
        	
        	// Scaler to control Logo size
        	
        	scale[i] = new Scale(comp, SWT.HORIZONTAL);
        	scale[i].setLayoutData(new GridData(SCALE_WIDTH, DEF_HEIGHT));
            scale[i].setMinimum(MIN_SCALER_VALUE);
            scale[i].setMaximum(MAX_SCALER_VALUE);
            scale[i].setIncrement(5);
            scale[i].setPageIncrement(5);
            scale[i].setData(i);
            scale[i].addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					int whichLogo =   Integer.parseInt(event.widget.getData().toString());
					logoScale[whichLogo] = scale[whichLogo].getSelection();
					scaleLabel[whichLogo].setText(logoScale[whichLogo]+"%");
					update();
					
				}
            	
            });
            
            
            scaleLabel[i] = new Label(comp, SWT.NONE);
            scaleLabel[i].setLayoutData(new GridData(LABEL_WIDTH, SWT.DEFAULT));
        
        	if (newImage != null) newImage.dispose();
        	
        }
    	
    }
    
    private void update() {
    	if (IsToPlotLogos()) {
    		if (logosResource == null) {
        		logosResource = getLogosResource(true);
        	}
    		
    		logosResource.setLogoAttrs(logoPosition, logoScale);
    		theEditor.refresh();
    	}
    }
    
    private void initialize() {
    	
    	theEditor = NmapUiUtils.getActiveNatlCntrsEditor();
		logosResource = null;
    	logosResource = getLogosResource(false);
    	
    	if (logoList.size() > 0) {

    		// Initialize Logo Positions and Scales
    		logoPosition = new int[logoList.size()];
    		logoScale = new int[logoList.size()];
    		if (logosResource != null) {
    			logoPosition = logosResource.getPositionArray();
    			logoScale = logosResource.getScaleArray();
    		}
    		else {
    			for (int i = 0; i < logoList.size(); i++) {
    				logoPosition[i] = INIT_POSITION;
    				logoScale[i] = INIT_SCALE;
    			}
    		}


    		// Set position buttons and scale values
    		for (int i = 0; i < logoList.size(); i++) {
    			for ( int j = 0; j < MAX_LOCATION; j++) {
    				if (j == logoPosition[i]) {
    					locButtons[i][j].setSelection(true);
    				} else {
    					locButtons[i][j].setSelection(false);
    				}
    			}
    			
				scale[i].setSelection(logoScale[i]);
				scaleLabel[i].setText(logoScale[i]+"%");
    		}
    	}
    }
    
    private void setDefault() {
    	
    	if (logoList.size() > 0) {

    		// Default setting for Logo Positions and Scales
    		logoPosition = new int[logoList.size()];
    		logoScale = new int[logoList.size()];
    		
    		for (int i = 0; i < logoList.size(); i++) {
    			logoScale[i]  = logoList.get(i).getInitialScale();
    			
    			int pos = INIT_POSITION;
    			String loc = logoList.get(i).getInitialLocation();
    			
    			for ( int j = 0; j < MAX_LOCATION; j++) {
    				if (locStr[j].equalsIgnoreCase(loc)) {
    					pos = j;
    					break;
    				}
    			}
    			logoPosition[i] = pos;
    		}


    		// Set position buttons and scale values
    		for (int i = 0; i < logoList.size(); i++) {
    			for ( int j = 0; j < MAX_LOCATION; j++) {
    				if (j == logoPosition[i]) {
    					locButtons[i][j].setSelection(true);
    				} else {
    					locButtons[i][j].setSelection(false);
    				}
    			}
    			
				scale[i].setSelection(logoScale[i]);
				scaleLabel[i].setText(logoScale[i]+"%");
    		}
    	}
    }


    private boolean IsToPlotLogos() {
    	int flag = 0;
    	for(int i = 0; i < logoList.size(); i++) {
    		flag += logoPosition[i];
    	}
    	
    	return (flag == 0) ? false : true;
    }
    
    private void readLogoTable() {
    	if (logoList == null) {
    		logoInfo = new LogoInfo(LocalizationManager.getInstance().getFilename("logoTable"));
    		try {
    			if (logoInfo.readTable()) {
    				logoList = new ArrayList<LogoEntry>();
    				logoList = logoInfo.getLogoList();
    			}
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    
    private void addSeparator() {
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        Label sepLbl = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        sepLbl.setLayoutData(gd);
    }
    
    /**
     * Create the default and close button.
     */
    private void createCloseButton() {
        Composite centeredComp = new Composite(shell, SWT.NONE);
        GridLayout gl = new GridLayout(3, true);
        centeredComp.setLayout(gl);
        GridData gd = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
        centeredComp.setLayoutData(gd);

        Button default_btn = new Button(centeredComp, SWT.NONE);
        default_btn.setText("Default");
        default_btn.setLayoutData(gd);
        default_btn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
            	setDefault();
            	update();
            }
        });
        
        Button turnOff_btn = new Button(centeredComp, SWT.NONE);
        turnOff_btn.setText("Clear");
        turnOff_btn.setLayoutData(gd);
        turnOff_btn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
            	removeLogosResource();
            }
        });
        
        Button closeBtn = new Button(centeredComp, SWT.NONE);
        closeBtn.setText("Close");
        closeBtn.setLayoutData(gd);
        closeBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
            	if (!IsToPlotLogos()) removeLogosResource();
                shell.dispose();
            }
        });
    }
    
    public boolean isOpen() {
        return shell != null && !shell.isDisposed();
    }
    
    private LogosResource getLogosResource(boolean toCreate) {
    	// See if an logos resource is there
    	if (theEditor == null)
    		theEditor = NmapUiUtils.getActiveNatlCntrsEditor();
    	
        ResourceList rscs = theEditor.getDescriptor().getResourceList();
        
        for (ResourcePair r : rscs) {
            if (r.getResource() instanceof LogosResource) {
            	logosResource = (LogosResource) r.getResource();
                break;
            }
        }

        // Create new resource for logos
        if (toCreate) {
        	//logosResource = new LogosResource();
            try {
            	LogosResourceData srd = new LogosResourceData();
            	logosResource = srd.construct(new LoadProperties(), theEditor.getDescriptor());
            	theEditor.getDescriptor().getResourceList().add(
            			logosResource);
                logosResource.init(theEditor.getActiveDisplayPane()
                        .getTarget());
                
            } catch (VizException e) {
                e.printStackTrace();
            }
            theEditor.refresh();
        }
        
        return logosResource;
    }
    
    private void removeLogosResource() {
    	if (logosResource != null) {
    		theEditor.getDescriptor().getResourceList().removeRsc(logosResource);
    		logosResource = null;
    		theEditor.refresh();
    	}
    	
    	logoPosition = null;
    	logoScale = null;
    	initialize();
    }

}
