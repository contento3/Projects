package com.c3.email.marketing.newsletter.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.c3.email.marketing.newsletter.dao.NewsletterDao;
import com.c3.email.marketing.newsletter.model.Newsletter;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class NewsletterDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Newsletter,Integer> 
	 implements NewsletterDao {

	private static final String CACHE_REGION = "com.c3.email.marketing.newsletter.model.Newsletter";

	public NewsletterDaoHibernateImpl() {
		super(Newsletter.class);
	}

	@Override
	public Collection<Newsletter> findByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Newsletter.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions.eq("account.accountId", accountId));
		return criteria.list();
	}

}
