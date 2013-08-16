package com.contento3.cms.content.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.content.dao.AssociatedContentScopeDao;
import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class AssociatedContentScopeDaoHibernateImpl extends
		GenericDaoSpringHibernateTemplate<AssociatedContentScope, Integer> implements
		AssociatedContentScopeDao {

	public static final String CACHE_REGION = "com.contento3.cms.content.model.AssociatedContentScope";

	public AssociatedContentScopeDaoHibernateImpl() {
		super(AssociatedContentScope.class);
	}
	
	@Override
	public Collection<AssociatedContentScope> findAssociatedContentScopeByType(AssociatedContentScopeTypeEnum scopeType){
		Validate.notNull(scopeType,"scopeType cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(AssociatedContentScope.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("type", scopeType.getScopeType()));
		
		return criteria.list();
	}

	
}
