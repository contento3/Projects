package com.contento3.web.email.marketing;

import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.web.UIManagerContext;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;

public class NewsletterSendListener implements ClickListener {

	private static final long serialVersionUID = -2614658384560645243L;

	private UIManagerContext context;
	
	private NewsletterService newsletterService;
	
	public NewsletterSendListener (final UIManagerContext context){
	 this.context = context;	
	 this.newsletterService = (NewsletterService)context.getHelper().getBean("newsletterService");
	}
	
	@Override
	public void click(final ClickEvent event) {
		newsletterService.send(newsletterService.findById(context.getIdToEdit()));
	}

}
