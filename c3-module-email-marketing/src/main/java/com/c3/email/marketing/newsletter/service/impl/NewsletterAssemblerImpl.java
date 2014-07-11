package com.c3.email.marketing.newsletter.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.CollectionUtils;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.model.Newsletter;
import com.c3.email.marketing.newsletter.service.NewsletterAssembler;
import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.model.Subscription;
import com.c3.email.marketing.subscription.service.SubscriptionAssembler;
import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.service.TemplateAssembler;

public class NewsletterAssemblerImpl implements NewsletterAssembler {

	private final TemplateAssembler templateAssembler;
	
	private final AccountAssembler accountAssembler;
	
	private final SubscriptionAssembler subscriptionAssembler;
	
	public NewsletterAssemblerImpl (final TemplateAssembler templateAssembler,final AccountAssembler accountAssembler,final SubscriptionAssembler subscriptionAssembler){
		this.templateAssembler = templateAssembler;
		this.accountAssembler = accountAssembler;
		this.subscriptionAssembler = subscriptionAssembler;
	}
	
	@Override
	public Newsletter dtoToDomain(final NewsletterDto dto,final Newsletter domain) {
		domain.setNewsletterId(dto.getNewsletterId());
		domain.setNewsletterName(dto.getNewsletterName());
		domain.setDescription(dto.getDescription());
		domain.setStatus(dto.getStatus());
		domain.setSubject(dto.getSubject());
		domain.setTemplate(templateAssembler.dtoToDomain(dto.getTemplate(), new Template()));
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
	
		final Collection<SubscriptionDto> subscriptionList = dto.getSubscriptionList();
		if (!CollectionUtils.isEmpty(subscriptionList)){
			domain.setSubscriptionList(subscriptionAssembler.dtosToDomains(subscriptionList));
		}
		
		return domain;
	}

	@Override
	public NewsletterDto domainToDto(final Newsletter domain, final NewsletterDto dto) {
		dto.setNewsletterId(domain.getNewsletterId());
		dto.setNewsletterName(domain.getNewsletterName());
		dto.setDescription(domain.getDescription());
		dto.setStatus(domain.getStatus());
		dto.setSubject(domain.getSubject());
		dto.setTemplate(templateAssembler.domainToDto(domain.getTemplate(), new TemplateDto()));
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		
		final Collection<Subscription> subscriptionList = domain.getSubscriptionList();
		if (!CollectionUtils.isEmpty(domain.getSubscriptionList())){
			dto.setSubscriptionList(subscriptionAssembler.domainsToDtos(subscriptionList));
		}

		return dto;
		
	}

	@Override
	public Collection<NewsletterDto> domainsToDtos(final Collection<Newsletter> domains) {
        final Collection<NewsletterDto> dtos = new ArrayList<NewsletterDto>();
        final Iterator<Newsletter> iterator = domains.iterator();
        while (iterator.hasNext()){
                dtos.add(domainToDto(iterator.next(),new NewsletterDto()));
        }
        return dtos;
	}

	@Override
	public Collection<Newsletter> dtosToDomains(Collection<NewsletterDto> dtos) {
		return null;
	}

}
