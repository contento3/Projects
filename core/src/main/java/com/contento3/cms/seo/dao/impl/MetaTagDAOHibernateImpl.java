package com.contento3.cms.seo.dao.impl;

import com.contento3.cms.seo.dao.MetaTagDAO;
import com.contento3.cms.seo.model.MetaTag;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class MetaTagDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<MetaTag, Integer> implements MetaTagDAO {

	public MetaTagDAOHibernateImpl() {
		super(MetaTag.class);
	}


}
