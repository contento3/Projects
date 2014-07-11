package com.c3.email.marketing.newsletter.service;

import java.util.Collection;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.contento3.common.service.SimpleService;

/**
 * Class that manages the newsletter related information
 * @author hamakhaa
 *
 */
public interface NewsletterService extends SimpleService<NewsletterDto> {

	/**
	 * Gets the newsletters for an account
	 * @param accountId
	 * @return
	 */
	Collection <NewsletterDto> findByAccountId(Integer accountId);
	
	/**
	 * Gets the newsletter by an id
	 * @param newsletterId
	 * @return
	 */
	NewsletterDto findById(Integer newsletterId);
	
	/**
	 * Updates
	 * @param newsletter
	 */
	void update(NewsletterDto newsletter);
	
	/**
	 * Sends the newsletter to the subscriber based 
	 * on subscriptions assigned to the newsletter
	 * @param newsletter
	 */
	void send(NewsletterDto newsletter);
	
	/**
	 * Returns the preview html that is used to preview the newsletter
	 * @param newsletter
	 * @return String html markup for this newsletter
	 */
	String preview(NewsletterDto newsletter);
}
