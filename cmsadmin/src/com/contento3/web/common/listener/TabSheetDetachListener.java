package com.contento3.web.common.listener;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SitesDashBoard;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachEvent;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

public class TabSheetDetachListener implements DetachListener, ComponentDetachListener {

	@Override
	public void detach(DetachEvent event) {
		event.getSource();
		System.out.print("hello");
		final SpringContextHelper helper = new SpringContextHelper(UI.getCurrent());

		SitesDashBoard sitesDashBoard = new SitesDashBoard((TabSheet)event.getSource(),helper);
		sitesDashBoard.render(null);

	}

	@Override
	public void componentDetachedFromContainer(ComponentDetachEvent event) {
		Component component = event.getDetachedComponent();
		System.out.print("hello component");
		final SpringContextHelper helper = new SpringContextHelper(UI.getCurrent());

		SitesDashBoard sitesDashBoard = new SitesDashBoard((TabSheet)event.getComponent(),helper);
		sitesDashBoard.render(null);
	}

}
