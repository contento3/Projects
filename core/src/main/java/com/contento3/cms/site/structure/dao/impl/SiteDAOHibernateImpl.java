package com.contento3.cms.site.structure.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
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
	public Collection<Site> findByAccount(final Integer accountId, boolean isPublished){
		Validate.notNull(accountId,"accountId cannot be null");
		
	
		
		final Criteria criteria = this.getSession()
								.createCriteria(Site.class)
								.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
								.setCacheable(true)
								.setCacheRegion(CACHE_REGION)
								.add(Restrictions
								.eq("account.accountId", accountId));
		
		if(isPublished) {
			criteria.add(Restrictions.eq("status", 1));
		}
		
		return criteria.list();
	}

	@Override
	public Site findByDomain(final String domain, boolean isPublished){
		Validate.notNull(domain,"domain cannot be null");

		final Criteria criteria = this.getSession()
								.createCriteria(Site.class)
							    .createAlias("siteDomain", "domain")
		    					.add(Restrictions
								.eq("domain.domainName", domain));
		if(isPublished) {
			criteria.add(Restrictions.eq("status", 1));
		}
		
		final List<Site> sites = criteria.list();
		Site site = null;
		
		if (!CollectionUtils.isEmpty(sites)){
			site = sites.get(0);
		}
		
		return site;
	}

}