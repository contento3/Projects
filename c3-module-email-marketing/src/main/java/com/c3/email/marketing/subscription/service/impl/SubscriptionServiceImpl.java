package com.c3.email.marketing.subscription.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.c3.email.marketing.subscription.dao.SubscriptionDao;
import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.model.Subscription;
import com.c3.email.marketing.subscription.service.SubscriptionAssembler;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionDao subscriptionDao;
	
	private SubscriptionAssembler subscriptionAssembler;
	
	public SubscriptionServiceImpl (final SubscriptionDao subscriptionDao,final SubscriptionAssembler subscriptionAssembler){
		this.subscriptionDao = subscriptionDao;
		this.subscriptionAssembler = subscriptionAssembler;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Object create(final SubscriptionDto dto) throws EntityAlreadyFoundException,EntityNotCreatedException {
		final Subscription subscription = subscriptionAssembler.dtoToDomain(dto,new Subscription());
		return subscriptionDao.persist(subscription);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(final SubscriptionDto dtoToDelete) throws EntityCannotBeDeletedException {
		final Integer subscriptionId = dtoToDelete.getSubscriptionId();
		subscriptionDao.delete(subscriptionAssembler.dtoToDomain(dtoToDelete,new Subscription()));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<SubscriptionDto> findByAccountId(final Integer accountId) {
		final Collection<Subscription> subscriptionList = subscriptionDao.findByAccountId(accountId);
		return subscriptionAssembler.domainsToDtos(subscriptionList);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SubscriptionDto findById(final Integer subscriptionId) {
		final Subscription subscription = subscriptionDao.findById(subscriptionId);
		return subscriptionAssembler.domainToDto(subscription,new SubscriptionDto());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final SubscriptionDto subscriptionDto) {
		final Subscription subscription = subscriptionDao.findById(subscriptionDto.getSubscriptionId());
		subscriptionAssembler.dtoToDomain(subscriptionDto,subscription);
		subscriptionDao.update(subscription);
	}

	
}
