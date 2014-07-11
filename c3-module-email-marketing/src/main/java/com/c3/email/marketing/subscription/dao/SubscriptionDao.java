package com.c3.email.marketing.subscription.dao;

import java.util.Collection;

import com.c3.email.marketing.subscription.model.Subscription;
import com.contento3.common.dao.GenericDao;

public interface SubscriptionDao extends GenericDao<Subscription,Integer> {

	Collection<Subscription> findByAccountId(Integer accountId);

	Collection<Subscription> findByAccountId(Integer accountId,Integer newsletter);
}
