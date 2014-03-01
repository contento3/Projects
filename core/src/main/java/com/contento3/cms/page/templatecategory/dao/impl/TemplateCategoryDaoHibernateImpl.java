package com.contento3.cms.page.templatecategory.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dao.CategoryDao;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.templatecategory.dao.TemplateCategoryDao;
import com.contento3.cms.page.templatecategory.model.TemplateCategory;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class TemplateCategoryDaoHibernateImpl extends
		GenericDaoSpringHibernateTemplate<TemplateCategory, Integer> implements
		TemplateCategoryDao {

	public TemplateCategoryDaoHibernateImpl() {
		super(TemplateCategory.class);
	}

	

}
