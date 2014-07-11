package com.c3.email.marketing.subscription.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.c3.email.marketing.subscription.dao.SubscriptionDao;
import com.c3.email.marketing.subscription.model.Subscription;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SubscriptionDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Subscription,Integer> 
implements  SubscriptionDao{

	private static final String CACHE_REGION = "com.c3.email.marketing.subscription.model.Subscription";

	public SubscriptionDaoHibernateImpl() {
		super(Subscription.class);
	}

	@Override
	public Collection<Subscription> findByAccountId(Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Subscription.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions.eq("account.accountId", accountId));
		return criteria.list();
	}

	@Override
	public Collection<Subscription> findByAccountId(final Integer accountId,final Integer newsletter) {
		Validate.notNull(accountId,"accountId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Subscription.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions.eq("account.accountId", accountId));
		return criteria.list();
	}

}
