package com.c3.email.marketing.newsletter.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.dto.Dto;

public class NewsletterDto extends Dto {

	private Integer newsletterId;
	
	private String newsletterName;
	
	private String status;
	
	private String description;
	
	private TemplateDto template;
	
	private String subject;

	private Collection <SubscriptionDto> subscriptionList = new ArrayList<SubscriptionDto>();

	private AccountDto account;
	
	@Override
	public Integer getId(){
		return this.newsletterId;
	}

	@Override
	public String getName(){
		return this.newsletterName;
	}

	public Integer getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(final Integer newsletterId) {
		this.newsletterId = newsletterId;
	}

	public String getNewsletterName() {
		return newsletterName;
	}

	public void setNewsletterName(final String newsletterName) {
		this.newsletterName = newsletterName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public TemplateDto getTemplate() {
		return template;
	}

	public void setTemplate(final TemplateDto template) {
		this.template = template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public Collection <SubscriptionDto> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(final Collection <SubscriptionDto> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(final AccountDto account) {
		this.account = account;
	}

}
