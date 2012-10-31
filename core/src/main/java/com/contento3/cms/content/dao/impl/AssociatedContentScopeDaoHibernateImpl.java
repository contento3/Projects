package com.contento3.cms.content.dao.impl;

import com.contento3.cms.content.dao.AssociatedContentScopeDao;
import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class AssociatedContentScopeDaoHibernateImpl extends
		GenericDaoSpringHibernateTemplate<AssociatedContentScope, Integer> implements
		AssociatedContentScopeDao {

	public AssociatedContentScopeDaoHibernateImpl() {
		super(AssociatedContentScope.class);
	}
	
}
