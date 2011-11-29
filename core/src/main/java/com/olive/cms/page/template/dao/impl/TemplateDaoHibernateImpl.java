package com.olive.cms.page.template.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.template.dao.TemplateDao;
import com.olive.cms.page.template.model.Template;
import com.olive.cms.page.template.model.TemplateDirectory;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class TemplateDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Template,Integer> 
 implements  TemplateDao {

	public TemplateDaoHibernateImpl() {
		super(Template.class);
	}

	@Override
	public Collection<Template> findTemplateByDirectoryName(final String name){
		Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.createCriteria("directory")
		.add(Restrictions
		.eq("directoryName", name));

		return criteria.list();
	}

}
