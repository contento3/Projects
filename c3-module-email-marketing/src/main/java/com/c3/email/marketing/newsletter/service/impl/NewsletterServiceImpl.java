package com.c3.email.marketing.newsletter.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.c3.email.marketing.newsletter.dao.NewsletterDao;
import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.model.Newsletter;
import com.c3.email.marketing.newsletter.service.NewsletterAssembler;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.c3.email.marketing.subscription.dto.SubscriberDto;
import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.module.email.EmailService;
import com.contento3.module.email.model.EmailInfo;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class NewsletterServiceImpl implements NewsletterService {

	private final NewsletterDao newsletterDao;
	
	private final NewsletterAssembler assembler;

	private EmailService emailService;
	
	private TemplateEngine templateEngine;
	
	public NewsletterServiceImpl(final NewsletterDao newsletterDao,final NewsletterAssembler assembler){
		this.newsletterDao = newsletterDao;
		this.assembler = assembler;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<NewsletterDto> findByAccountId(final Integer accountId) {
		final Collection <Newsletter> newsletters = newsletterDao.findByAccountId(accountId);
		return assembler.domainsToDtos(newsletters);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public NewsletterDto findById(final Integer newsletterId) {
		return assembler.domainToDto(newsletterDao.findById(newsletterId),new NewsletterDto());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Object create(final NewsletterDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		final Newsletter newsletter = new Newsletter();
		assembler.dtoToDomain(dto, newsletter);
		return newsletterDao.persist(newsletter);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(final NewsletterDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		final Newsletter newsletter = new Newsletter();
		assembler.dtoToDomain(dtoToDelete, newsletter);
		newsletterDao.delete(newsletter);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final NewsletterDto newsletterDto) {
		final Newsletter newsletter = new Newsletter();
		assembler.dtoToDomain(newsletterDto, newsletter);
		newsletterDao.update(newsletter);
	}

	@Override
	public void send(final NewsletterDto newsletterDto) {
		final TemplateDto template = newsletterDto.getTemplate();
		final Collection<SubscriptionDto> subscriptions = newsletterDto.getSubscriptionList();
		
		final String templateText = templateEngine.process("/newsletterTemplate/"+template.getTemplateId(), new Context());
		for (SubscriptionDto subscriptionDto : subscriptions){
			final Collection <SubscriberDto> subscribers = subscriptionDto.getSubscribers();
			for (SubscriberDto subscriber : subscribers){
				emailService.send(buildEmailInfo(subscriber,templateText));		
			}
		}
	}
	
	public void setEmailService(final EmailService emailService){
		this.emailService = emailService;
	}
	
	
	private EmailInfo buildEmailInfo(final SubscriberDto subscriberDto,final String template){
		final EmailInfo emailInfo = new EmailInfo();
		emailInfo.setEmailText(template);
		emailInfo.setTo(subscriberDto.getEmail());
		emailInfo.setFrom("info@contento3.com");
		return emailInfo;
	}

	public void setTemplateEngine(final TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Override
	public String preview(NewsletterDto newsletter) {
		final TemplateDto template = newsletter.getTemplate();
		final String templateText = templateEngine.process("/newsletterTemplate/"+template.getTemplateId(), new Context());
		return templateText;
	}
	
}
