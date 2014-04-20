package com.contento3.web.content.image;

import com.contento3.dam.image.dto.ImageDto;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ImageViewPopup extends Window {

	private static final long serialVersionUID = 1L;
	
	private final static int IMAGE_WIDTH = 150;
	private final static int IMAGE_HEIGHT = 150;
	private final static String MSG_IMAGE_FAILED = "Image not loaded.";
	
	/**
	 * Singleton instance
	 */
	private static ImageViewPopup instance;
	
	/**
	 * Mainlayout for popup window
	 */
	private VerticalLayout mainLayout;
	
	/**
	 * Return singletion instance
	 * @return
	 */
	public static ImageViewPopup getInstance() {
		
		if(instance == null)
			instance = new ImageViewPopup();
		
		return instance;
	}
	
	/**
	 * Show pop-up window
	 * @param dto
	 */
	public void show(ImageDto dto) {
		
		setCaption(dto.getName());
		mainLayout = new VerticalLayout();
		addcomponent(dto);
		mainLayout.setMargin(true);
        setContent(mainLayout);
        setModal(true);
        setClosable(true);
        setResizable(false);
//        setPositionX(500);
//        setPositionY(500);
        UI.getCurrent().addWindow(this);
	}
	

	/**
	 * Add image to pop-up window
	 * @param dto
	 */
	private void addcomponent(ImageDto dto) {
		
		try {
			Embedded embedded = loadImage(dto, IMAGE_WIDTH, IMAGE_HEIGHT);
			mainLayout.addComponent(embedded);
		} catch(Exception e) {
			mainLayout.addComponent(new Label(MSG_IMAGE_FAILED));
		}
	}
	
	/**
	 * Return image
	 * @param imageDto
	 * @param height
	 * @param width
	 * @return
	 */
	public Embedded loadImage(final ImageDto imageDto,final Integer height,final Integer width) {
		
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(imageDto.getImage(), height, width);
		return embedded;
	}

	
}
