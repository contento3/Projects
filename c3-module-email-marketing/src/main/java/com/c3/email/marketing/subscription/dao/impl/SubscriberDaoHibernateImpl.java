package com.c3.email.marketing.subscription.dao.impl;

import com.c3.email.marketing.subscription.dao.SubscriberDao;
import com.c3.email.marketing.subscription.model.Subscriber;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SubscriberDaoHibernateImpl extends 
			GenericDaoSpringHibernateTemplate<Subscriber,Integer> implements  SubscriberDao {

	public SubscriberDaoHibernateImpl() {
		super(Subscriber.class);
	}
	
}
