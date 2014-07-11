package com.c3.email.marketing.subscription.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.c3.email.marketing.subscription.dto.SubscriberDto;
import com.c3.email.marketing.subscription.model.Subscriber;
import com.c3.email.marketing.subscription.service.SubscriberAssembler;

public class SubscriberAssemblerImpl implements SubscriberAssembler {

	@Override
	public Subscriber dtoToDomain(final SubscriberDto dto,final Subscriber domain) {
		domain.setEmail(dto.getEmail());
		domain.setFirstName(dto.getFirstName());
		domain.setLastName(dto.getLastName());
		domain.setSiteId(dto.getSiteId());
		domain.setSiteUserId(dto.getSiteUserId());
		domain.setSubscriberId(dto.getSubscriberId());
		return domain;
	}

	@Override
	public SubscriberDto domainToDto(final Subscriber domain,final SubscriberDto dto) {
		dto.setEmail(domain.getEmail());
		dto.setFirstName(domain.getFirstName());
		dto.setLastName(domain.getLastName());
		dto.setSiteId(domain.getSiteId());
		dto.setSiteUserId(domain.getSiteUserId());
		dto.setSubscriberId(domain.getSubscriberId());
		return dto;
	}

	@Override
	public Collection<SubscriberDto> domainsToDtos(final Collection<Subscriber> domains) {
        final Collection<SubscriberDto> dtos = new ArrayList<SubscriberDto>();
        final Iterator<Subscriber> iterator = domains.iterator();
        while (iterator.hasNext()){
                dtos.add(domainToDto(iterator.next(),new SubscriberDto()));
        }
        return dtos;
	}

	@Override
	public Collection<Subscriber> dtosToDomains(final Collection<SubscriberDto> dtos) {
        final Collection<Subscriber> domains = new ArrayList<Subscriber>();
        final Iterator<SubscriberDto> iterator = dtos.iterator();
        while (iterator.hasNext()){
                domains.add(dtoToDomain(iterator.next(),new Subscriber()));
        }
        return domains;
	}

}
