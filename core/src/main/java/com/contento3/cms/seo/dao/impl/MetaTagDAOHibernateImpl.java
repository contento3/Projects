package com.contento3.cms.seo.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.seo.dao.MetaTagDAO;
import com.contento3.cms.seo.model.MetaTag;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class MetaTagDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<MetaTag, Integer> implements MetaTagDAO {

	private final static String SITE_ID = "site.siteId";//META_TAG_ID
	
	public MetaTagDAOHibernateImpl() {
		super(MetaTag.class);
	}

	@Override
	public Collection<MetaTag> findBySiteId(Integer siteId) {
		
		Validate.notNull(siteId,"siteId cannot be null");

		final Criteria criteria = this.getSession()
								.createCriteria(MetaTag.class)
								.add(Restrictions
								.eq(SITE_ID, siteId));
		
		return criteria.list();
	}


}
