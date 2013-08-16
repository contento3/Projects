package com.contento3.web.common.helper;

import com.contento3.dam.image.dto.ImageDto;
import com.contento3.web.content.image.ImageLoader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class ImageViewer extends CustomComponent implements Window.CloseListener,Button.ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 *  Reference to main window
	 */
	private Window mainWindow; 
	
	/**
	 * The window to be opened
	 */
	private Window popupWindow; 
	
	/**
	 * Button for opening the window
	 */
	private Button openbutton; 
	
	/**
	 * Vertical layout of pop-up
	 */
	private VerticalLayout popupMainLayout;
	
	/**
	 * Window close property
	 */
	boolean isModalWindowClosable = true;
	
	/**
	 *Image to display
	 */
	private final ImageDto image;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param imageDto
	 */
	public ImageViewer(final Window mainWindow, final ImageDto imageDto) {
		this.mainWindow = mainWindow;
		this.image = imageDto;
		
		// The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Group", this);
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}
	
	 /** 
	   * Handle the click event.
	   */
	public void openButtonClick(Button.ClickEvent event) {
		popupWindow = new Window();
		popupWindow.setPositionX(200);
		popupWindow.setPositionY(100);
		popupWindow.setHeight(66,Unit.PERCENTAGE);
		popupWindow.setWidth(36,Unit.PERCENTAGE);
     
		/* Add the window inside the main window. */
		UI.getCurrent().addWindow(popupWindow);

		/* Listen for close events for the window. */
		popupWindow.addCloseListener(this);
		popupWindow.setModal(true);
		popupWindow.setCaption(image.getName());
		popupMainLayout = new VerticalLayout();
		popupWindow.setContent(popupMainLayout);
		popupWindow.setResizable(false);
		/* Allow opening only one window at a time. */
		openbutton.setEnabled(false);
		renderImage();
	}
	
	private void renderImage(){
		//Panel panel = new Panel();
		//panel.setHeight(380, UNITS_PIXELS);
		//panel.setWidth(400, UNITS_PIXELS);
		//panel.addComponent(loadImage(image));
		//this.popupMainLayout.addComponent(panel);
		this.popupMainLayout.addComponent(loadImage(image));
	}
	
	  /**
     * Loads the image using the Image loader class based on width and height provided.
     * @param imageDto
     * @return
     */
	public Embedded loadImage(final ImageDto imageDto) {
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(imageDto.getImage(), 425, 425);
		return embedded;
	}

	
	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {
		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			UI.getCurrent().removeWindow(popupWindow);

			/* Return to initial state. */
			openbutton.setEnabled(true);
		}
	}

	/**
	 * Handle window close event
	 */
	@Override
	public void windowClose(CloseEvent e) {
		/* Return to initial state. */
		openbutton.setEnabled(true);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		this.openButtonClick(event);		
	}

}
