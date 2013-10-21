package com.contento3.web.content.image.listener;

import com.contento3.web.content.image.ImageLibraryPopup;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;

public class AddLibraryButtonListener implements ClickListener {

	private SpringContextHelper helper;

	public AddLibraryButtonListener(SpringContextHelper helper) {
		// TODO Auto-generated constructor stub
		this.helper = helper;
	}

	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		ImageLibraryPopup temp = new ImageLibraryPopup(helper);
		temp.buttonClick(new Button.ClickEvent(new Button("Add Library")));
	}

}
