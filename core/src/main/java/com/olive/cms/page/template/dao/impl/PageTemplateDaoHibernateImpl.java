package com.olive.cms.page.template.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.section.model.PageSectionTypeEnum;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageAndPageSectionType(final Integer pageId,final PageSectionTypeEnum pageSectionType) {
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.page.pageId", pageId)).add(Restrictions
		.eq("primaryKey.sectionType.name", pageSectionType.toString()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageId(final Integer pageId) {
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.page.pageId", pageId));

		return criteria.list();
	}

}
