package com.contento3.cms.page.layout.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.layout.dao.PageLayoutDao;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageLayoutDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageLayout,Integer> 
										implements PageLayoutDao {

	public PageLayoutDaoHibernateImpl() {
		super(PageLayout.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection <PageLayout> findPageLayoutByAccount(final Integer accountId){
		Validate.notNull(accountId ,"accountId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageLayout.class)
		.add(Restrictions
		.eq("accountId", accountId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection <PageLayout> findPageLayoutByAccountAndLayoutType(final Integer accountId,final Integer pageLayoutTypeId){
		Validate.notNull(accountId ,"accountId cannot be null");
		Validate.notNull(pageLayoutTypeId,"pageLayoutTypeId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageLayout.class)
		.add(Restrictions
		.eq("accountId", accountId))
		.add(Restrictions
		.eq("layoutType.id", pageLayoutTypeId));
		return criteria.list();
	}

}