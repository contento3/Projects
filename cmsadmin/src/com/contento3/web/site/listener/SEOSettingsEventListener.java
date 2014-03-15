package com.contento3.web.site.listener;

import com.contento3.web.site.seo.SEOUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.TabSheet;

public class SEOSettingsEventListener implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * TabSheet component
	 */
	private TabSheet uiTabSheet;
	
	/**
	 * UI Manger for SEO Settings
	 */
	private SEOUIManager seoUiManager;
	
	/**
	 * Id of site
	 */
	final Integer siteId;
	
	public SEOSettingsEventListener(final SEOUIManager uiManager, final TabSheet tabSheet,final  
			Integer siteId) {
		this.uiTabSheet = tabSheet;
		this.seoUiManager = uiManager;
		this.siteId = siteId;
	}
	
	/**
	 * Button click handler
	 */
	@Override
	public void click(ClickEvent event) {
		
		seoUiManager.renderSEOSettingsManager(uiTabSheet, siteId);
	}

}
