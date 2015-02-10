package com.contento3.web.common.helper;

import com.vaadin.ui.Button.ClickEvent;

public class EntityListener {

	/**
	 *Used for Button and title caption
	 */
	private String caption;
	
	public final String getCaption() {
		return caption;
	}

	public final void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * used in extended class for saving selected items
	 */
	public void updateList(){
	}
	
	/**
	 * used in extended class to handle items button click listener
	 */
	public void entityButtonClickListener(ClickEvent event) {
		System.out.print("In "+EntityListener.class+"Entity button click listener");
	}
	
}
