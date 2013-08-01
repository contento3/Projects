package com.contento3.cms.site.structure.domain.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.site.structure.domain.dao.SiteDomainDao;
import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SiteDomainDaoHibernateImpl  extends
	GenericDaoSpringHibernateTemplate<SiteDomain, Integer> implements SiteDomainDao {


	public SiteDomainDaoHibernateImpl() {
		super(SiteDomain.class);
	}

	@Override
	public SiteDomain findSiteDomainByName(String domainName) {
	
		Criteria criteria = this.getSession()
				.createCriteria(SiteDomain.class)
				.add(Restrictions.eq("domainName", domainName));
	
		SiteDomain siteDomain= null;
		if(!CollectionUtils.isEmpty(criteria.list())){
			siteDomain= (SiteDomain) criteria.list().get(0);
		}
		return siteDomain;
	}
	


}
