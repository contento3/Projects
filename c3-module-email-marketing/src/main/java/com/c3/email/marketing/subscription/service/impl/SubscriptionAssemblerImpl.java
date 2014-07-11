
package com.c3.email.marketing.subscription.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.CollectionUtils;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.model.Subscription;
import com.c3.email.marketing.subscription.service.SubscriberAssembler;
import com.c3.email.marketing.subscription.service.SubscriptionAssembler;
import com.contento3.account.service.AccountAssembler;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;

public class SubscriptionAssemblerImpl implements SubscriptionAssembler {

	private AccountAssembler accountAssembler;
	
	private SubscriberAssembler subscriberAssembler;
	
	private SaltedHibernateUserAssembler  userAssembler;
	
	public SubscriptionAssemblerImpl(final AccountAssembler accountAssembler,final SubscriberAssembler subscriberAssembler,final SaltedHibernateUserAssembler userAssembler){
		this.accountAssembler = accountAssembler;
		this.subscriberAssembler = subscriberAssembler;
		this.userAssembler = userAssembler;
	}
	 
	@Override
	public Subscription dtoToDomain(final SubscriptionDto dto,final Subscription domain) {
		domain.setCreatedOn(dto.getCreatedOn());
		domain.setSubscriptionId(dto.getSubscriptionId());
		domain.setDescription(dto.getDescription());
		domain.setSubscriptionName(dto.getSubscriptionName());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		
		if (!CollectionUtils.isEmpty(dto.getSubscribers())){
			domain.setSubscribers(subscriberAssembler.dtosToDomains(dto.getSubscribers()));
		}
		domain.setPlatformUser(userAssembler.dtoToDomain(dto.getPlatformUser()));
		return domain;
	}

	@Override
	public SubscriptionDto domainToDto(final Subscription domain, final SubscriptionDto dto) {
		dto.setCreatedOn(domain.getCreatedOn());
		dto.setSubscriptionId(domain.getSubscriptionId());
		dto.setDescription(domain.getDescription());
		dto.setSubscriptionName(domain.getSubscriptionName());
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		dto.setSubscribers(subscriberAssembler.domainsToDtos(domain.getSubscribers()));
		dto.setPlatformUser(userAssembler.domainToDto(domain.getPlatformUser()));
		return dto;
	}	
	
	@Override
	public Collection<SubscriptionDto> domainsToDtos(final Collection<Subscription> domains) {
        final Collection<SubscriptionDto> dtos = new ArrayList<SubscriptionDto>();
        Iterator<Subscription> iterator = domains.iterator();
        while (iterator.hasNext()){
                dtos.add(domainToDto(iterator.next(),new SubscriptionDto()));
        }
        return dtos;
	}

	@Override
	public Collection<Subscription> dtosToDomains(final Collection<SubscriptionDto> dtos) {
        final Collection<Subscription> domains = new ArrayList<Subscription>();
        Iterator<SubscriptionDto> iterator = dtos.iterator();
        while (iterator.hasNext()){
        	domains.add(dtoToDomain(iterator.next(),new Subscription()));
        }
        return domains;
	}

}
