package com.contento3.web.site.listener;

import com.contento3.web.site.SiteConfigUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.TabSheet;

public class SiteConfigurationEventListener implements ClickListener {

	private static final long serialVersionUID = 1L;
	private SiteConfigUIManager siteConfigUIManager;
	private TabSheet uiTabSheet;
	private Integer siteId;

	public SiteConfigurationEventListener(SiteConfigUIManager UIManager, TabSheet uiSheet, Integer siteId) {
		this.siteConfigUIManager = UIManager;
		this.uiTabSheet = uiSheet;
		this.siteId = siteId;
				
	}

	
	public void click(ClickEvent event) {
		this.siteConfigUIManager.renderSiteConfig(this.siteId, this.uiTabSheet, null);
	}
}
