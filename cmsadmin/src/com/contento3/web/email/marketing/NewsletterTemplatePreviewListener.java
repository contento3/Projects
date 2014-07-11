package com.contento3.web.email.marketing;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.web.UIManagerContext;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewsletterTemplatePreviewListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	final NewsletterDto newsletterDto;
	
	final NewsletterService newsletterService;
	
	final UIManagerContext context; 
	
	public NewsletterTemplatePreviewListener (final UIManagerContext context,final NewsletterDto newsletter){
		this.newsletterDto = newsletter;
		this.context = context;
		this.newsletterService = (NewsletterService)context.getHelper().getBean("newsletterService");
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
	final String templateHtml = newsletterService.preview(newsletterDto);
	

	}

}
