package com.olive.cms.site.structure.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.olive.cms.site.structure.dao.SiteDAO;
import com.olive.cms.site.structure.model.Site;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SiteDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<Site,Integer> 
								  implements SiteDAO {

	public SiteDAOHibernateImpl(){
		super(Site.class);
	}
	
	@Override
	public Collection<Site> findByAccount(Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Site.class)
								.createCriteria("account")
								.add(Restrictions
								.eq("accountId", accountId));
		return criteria.list();
	}

	@Override
	public Site findByDomain(String domain){
		Criteria criteria = this.getSession()
								.createCriteria(Site.class)
								.add(Restrictions
								.eq("url", domain));
		List<Site> sites = criteria.list();
		Site site = null;
		
		if (!CollectionUtils.isEmpty(sites)){
			site = sites.get(0);
		}
		
		return site;
	}

}
