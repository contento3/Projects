package com.olive.cms.page.template.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.template.dao.PageTemplateDao;
import com.olive.cms.page.template.model.PageTemplate;
import com.olive.cms.page.template.model.PageTemplatePK;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageTemplateDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageTemplate,PageTemplatePK>  implements PageTemplateDao {

	public PageTemplateDaoHibernateImpl() {
		super(PageTemplate.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageAndPageSectionType(final Integer pageId,final Integer pageSectionTypeId) {
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.page.pageId", pageId)).add(Restrictions
		.eq("primaryKey.sectionType.id", pageSectionTypeId));

		return criteria.list();
	}

}
