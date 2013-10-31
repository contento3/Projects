package com.contento3.web.site.listener;

import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.web.site.SiteContentAssignmentUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class SiteContentAssignerClickEvent implements ClickListener {

	private SiteContentAssignmentUIManager siteContentAssignmentUIManager;
	
	public SiteContentAssignerClickEvent(SiteContentAssignmentUIManager siteContentAssignmentUIManager) {
		this.siteContentAssignmentUIManager = siteContentAssignmentUIManager;
	}

	@Override
	public void click(ClickEvent event) {
		siteContentAssignmentUIManager.buttonClick(new com.vaadin.ui.Button.ClickEvent(new Button("")));
	}

}
