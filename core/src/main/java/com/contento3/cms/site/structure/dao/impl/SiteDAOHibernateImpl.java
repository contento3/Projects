package com.contento3.cms.site.structure.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SiteDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<Site,Integer> 
								  implements SiteDAO {

	private static final String CACHE_REGION = "com.contento3.cms.site.structure.model.Site";
	
	public SiteDAOHibernateImpl(){
		super(Site.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Site> findByAccount(Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Site.class)
								.createCriteria("account")
								.setCacheable(true)
								.setCacheRegion(CACHE_REGION)
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
