package com.contento3.cms.page.section.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.section.dao.PageSectionTypeDao;
import com.contento3.cms.page.section.model.PageSectionType;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageSectionTypeHibernateImpl extends GenericDaoSpringHibernateTemplate<PageSectionType,Integer> 
 										  implements PageSectionTypeDao{

	public PageSectionTypeHibernateImpl() {
		super(PageSectionType.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageSectionType findByName(String name){
		PageSectionType pageSectionType;
		
		Criteria criteria = this.getSession()
		.createCriteria(PageSectionType.class)
		.add(Restrictions
		.eq("name", name));
		
		List<PageSectionType> pageSectionTypeList =  (ArrayList<PageSectionType>) criteria.list();
		pageSectionType = pageSectionTypeList.get(0);
		
		return pageSectionType;
	}
	
}
