package com.contento3.web.common.helper;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ScreenHeader {

	public ScreenHeader(final AbstractOrderedLayout layout,final String heading){
		buildHeader(layout,heading);
	}
	
	/**
	 * Create the screen header
	 * @param layout
	 * @param heading
	 */
	private void buildHeader(final AbstractOrderedLayout layout,final String heading){
		final Label screenHeading = new Label(heading); 
		screenHeading.setStyleName("screenHeading");
		layout.addComponent(screenHeading);
		layout.addComponent(new HorizontalRuler());
	}
}