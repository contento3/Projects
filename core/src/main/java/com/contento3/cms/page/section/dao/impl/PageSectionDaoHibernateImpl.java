package com.contento3.cms.page.section.dao.impl;

import com.contento3.cms.page.section.dao.PageSectionDao;
import com.contento3.cms.page.section.model.PageSection;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageSectionDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageSection,Integer> 
									 	 implements PageSectionDao{

	public PageSectionDaoHibernateImpl() {
		super(PageSection.class);
	}

}
