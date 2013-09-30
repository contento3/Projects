package com.contento3.web.site.listener;

import com.contento3.web.site.SiteContentAssignmentUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;

public class ContentAssignerEventListener implements ClickListener {

	private static final long serialVersionUID = 1L;
	private SiteContentAssignmentUIManager siteContentUIManager;
	private Integer siteId;

	public ContentAssignerEventListener(SiteContentAssignmentUIManager siteContentUIManager_, Integer siteId_) {
		this.siteContentUIManager = siteContentUIManager_;
		this.siteId = siteId_;
	}

	@Override
	public void click(ClickEvent event) {
		this.siteContentUIManager.render(this.siteId);
	}

}
