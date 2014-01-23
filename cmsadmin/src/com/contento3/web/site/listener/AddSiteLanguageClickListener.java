package com.contento3.web.site.listener;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.web.site.SiteConfigUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;

public class AddSiteLanguageClickListener implements ClickListener {

	private SiteDto siteDto;
	private ComboBox pageLayoutCombo;
	private ComboBox languageCombo;
	private SiteConfigUIManager siteConfigUIManager;

	public AddSiteLanguageClickListener(SiteDto siteDto,
			ComboBox pageLayoutCombo, ComboBox languageCombo, SiteConfigUIManager siteConfigUIManager) {
		
		this.siteDto = siteDto;
		this.pageLayoutCombo = pageLayoutCombo;
		this.languageCombo = languageCombo;
		this.siteConfigUIManager = siteConfigUIManager;
	}

	@Override
	public void click(ClickEvent event) { 
		siteConfigUIManager.saveSite(siteDto,  pageLayoutCombo,languageCombo);
		Notification.show(String.format("SiteConfig for %s saved successfullly",siteDto.getSiteName()));
	}

}
