package com.contento3.cms.page.template.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

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

	@Override
	public Collection<Template> findTemplateByPathAndAccount(final String templateName,final String parentDirectory,
			final String templateType,final Integer accountId){
		Criteria criteria = this.getSession()
		.createCriteria(Template.class,"template").add(Restrictions
		.eq("template.templateName", templateName));
		
		
		Criteria directoryCriteria = criteria.createCriteria("directory").add(Restrictions
				.eq("directoryName", parentDirectory));
		
		Criteria templateTypeCriteria = criteria
		.createCriteria("templateType").add(Restrictions
		.eq("contentType", templateType));
		
		Criteria accountCriteria = criteria
		.createCriteria("account").add(Restrictions
				.eq("accountId", accountId));
				
		
//				Restrictions
//		.eq("template.directory.directoryName", parentDirectory)).add(Restrictions
//		.eq("template.templateType.contentType", templateType)).add(Restrictions
//		.eq("template.account.accountId", accountId));

		return criteria.list();
	}


}
