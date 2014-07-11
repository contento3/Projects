package com.contento3.web.email.marketing;


import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

public class NewsletterDeleteClickListener extends EntityDeleteClickListener<NewsletterDto>  { 


	private static final long serialVersionUID = 1L;

	/**
	 * NewsletterDeleteClickListener
	 * @param newsletterDto
	 * @param newsletterService
	 * @param deleteLink
	 * @param table
	 */
	public NewsletterDeleteClickListener(final NewsletterDto newsletterDto,final NewsletterService newsletterService,
			final Button deleteLink,final Table table) {
		super(newsletterDto,newsletterService,deleteLink,table);
	}
}

