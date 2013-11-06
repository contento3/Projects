package com.contento3.web.site.listener;

import com.contento3.web.site.PageUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.TabSheet;

public class CreatePageEventListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	private PageUIManager pageUIManager;
	private TabSheet uiTabSheet;
	private Integer siteId;
	
	public CreatePageEventListener(PageUIManager UIManager, TabSheet uiSheet, Integer siteId) {
		
		this.pageUIManager = UIManager;
		this.uiTabSheet = uiSheet;
		this.siteId = siteId;
				
	}

	@Override
	public void click(ClickEvent event) {
		this.pageUIManager.renderNewPage(this.siteId, this.uiTabSheet , null);
	}
	
//	public void buttonClick(ClickEvent event) {
//		this.pageUIManager.renderNewPage(this.siteId, this.uiTabSheet , null);
//	}

}
