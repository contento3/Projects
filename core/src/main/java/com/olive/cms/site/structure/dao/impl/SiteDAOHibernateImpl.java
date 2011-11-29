package com.olive.cms.site.structure.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.site.structure.dao.SiteDAO;
import com.olive.cms.site.structure.model.Site;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class SiteDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<Site,Integer> 
								  implements SiteDAO {

	public SiteDAOHibernateImpl(){
		super(Site.class);
	}
	
	public Collection<Site> findByAccount(Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Site.class)
								.createCriteria("account")
								.add(Restrictions
								.eq("accountId", accountId));
		return criteria.list();
	}
}
