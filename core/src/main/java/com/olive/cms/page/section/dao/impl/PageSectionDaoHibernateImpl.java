package com.olive.cms.page.section.dao.impl;

import com.olive.cms.page.section.dao.PageSectionDao;
import com.olive.cms.page.section.model.PageSection;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageSectionDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageSection,Integer> 
									 	 implements PageSectionDao{

	public PageSectionDaoHibernateImpl() {
		super(PageSection.class);
	}

}
