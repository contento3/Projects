package com.contento3.web.common.helper;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ScreenHeader {

	public ScreenHeader(final VerticalLayout layout,final String heading){
		buildHeader(layout,heading);
	}
	
	/**
	 * Create the screen header
	 * @param layout
	 * @param heading
	 */
	private void buildHeader(final VerticalLayout layout,final String heading){
		final Label screenHeading = new Label(heading); 
		screenHeading.setStyleName("screenHeading");
		screenHeading.setSizeUndefined();
		layout.addComponent(screenHeading);
		layout.setSizeUndefined();
		//layout.addComponent(new HorizontalRuler());
	}
}