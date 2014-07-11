package com.c3.email.marketing.subscription.service;

import java.util.Collection;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.service.SimpleService;

public interface SubscriptionService extends SimpleService <SubscriptionDto> {
	
	Collection<SubscriptionDto> findByAccountId(Integer accountId) throws EntityNotFoundException;
	
	SubscriptionDto findById(Integer subscription);
	
	void update(SubscriptionDto subscription);

}
