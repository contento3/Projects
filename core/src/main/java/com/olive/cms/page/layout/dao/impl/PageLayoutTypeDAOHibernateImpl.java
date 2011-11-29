package com.olive.cms.page.layout.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.layout.dao.PageLayoutTypeDAO;
import com.olive.cms.page.layout.model.PageLayout;
import com.olive.cms.page.layout.model.PageLayoutType;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageLayoutTypeDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<PageLayoutType,Integer> 
								  implements PageLayoutTypeDAO {

	public PageLayoutTypeDAOHibernateImpl(){
		super(PageLayoutType.class);
	}

	@SuppressWarnings("unchecked")
	public PageLayoutType findByName(final String name){
		PageLayoutType layoutType;
		Criteria criteria = this.getSession()
		.createCriteria(PageLayoutType.class)
		.add(Restrictions
		.eq("name", name));

		List<PageLayoutType> pageLayoutTypeList =  (ArrayList<PageLayoutType>) criteria.list();
		layoutType = pageLayoutTypeList.get(0);
		
		return layoutType;
	}
}
