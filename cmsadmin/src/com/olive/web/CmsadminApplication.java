package com.olive.web;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class CmsadminApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Cmsadmin Application");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
