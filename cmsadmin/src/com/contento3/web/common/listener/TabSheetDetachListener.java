package com.contento3.web.common.listener;

import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachEvent;
import com.vaadin.ui.HasComponents.ComponentDetachListener;

public class TabSheetDetachListener implements DetachListener, ComponentDetachListener {

	@Override
	public void detach(DetachEvent event) {
		event.getSource();
		System.out.print("hello");
	}

	@Override
	public void componentDetachedFromContainer(ComponentDetachEvent event) {
		Component component = event.getDetachedComponent();
		component = null;
		System.out.print("hello component");
	}

}
