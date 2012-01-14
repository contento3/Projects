package com.olive.cms.page.layout.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.layout.dao.PageLayoutDao;
import com.olive.cms.page.layout.model.PageLayout;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageLayoutDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageLayout,Integer> 
										implements PageLayoutDao {

	public PageLayoutDaoHibernateImpl() {
		super(PageLayout.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection <PageLayout> findPageLayoutByAccount(final Integer accountId){
		Criteria criteria = this.getSession()
		.createCriteria(PageLayout.class)
		.add(Restrictions
		.eq("accountId", accountId));
		return criteria.list();
	}

}
