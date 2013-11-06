package com.contento3.cms.page.template.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class TemplateDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Template,Integer> 
 implements  TemplateDao {

	private static final String CACHE_REGION = "com.contento3.cms.page.template.model.Template";

	public TemplateDaoHibernateImpl() {
		super(Template.class);
	}

	@Override
	public Collection<Template> findTemplateByDirectoryName(final String name){
		Validate.notNull(name,"name cannot be null");
		
		final Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.createCriteria("directory")
		.add(Restrictions
		.eq("directoryName", name));

		return criteria.list();
	}
	
	@Override
	public Collection<Template> findTemplateByDirectoryId(final Integer id){
		Validate.notNull(id,"name cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.createCriteria("directory")
		.add(Restrictions
		.eq("id", id));

		return criteria.list();
	}


	@Override
	public Collection<Template> findTemplateByPathAndAccount(final String templateName,final String parentDirectory,
			final String templateType,final Integer accountId){
		Validate.notNull(templateName,"templateName cannot be null");
		Validate.notNull(parentDirectory,"parentDirectory cannot be null");
		Validate.notNull(templateType,"templateType cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		
		final Criteria criteria = this.getSession()
		.createCriteria(Template.class,"template")
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("template.templateName", templateName));
		
		Criteria directoryCriteria = criteria.createCriteria("directory").add(Restrictions
				.eq("directoryName", parentDirectory));
		
		Criteria templateTypeCriteria = criteria
		.createCriteria("templateType").add(Restrictions
		.eq("contentType", templateType));
		
		Criteria accountCriteria = criteria
		.createCriteria("account").add(Restrictions
				.eq("accountId", accountId));
			
		return criteria.list();
	}

	@Override
	public Template findSystemTemplateForAccount(final String templateCategory,
			final Integer accountId,final Boolean isGlobal) {
		Validate.notNull(templateCategory,"templateCategory cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(isGlobal,"isGlobal cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION).add(Restrictions
		.eq("isGlobal", isGlobal));
		
		criteria.createAlias("templateCategory","templateCategory")
		.add(Restrictions
		.eq("templateCategory.name", templateCategory));

		criteria.createAlias("account","account")
		.add(Restrictions
		.eq("account.accountId", accountId));
		
		Template template = null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			template = (Template)criteria.list().get(0);
		}	
		
		return template;
	}

	@Override
	public Template findGlobalSystemTemplate(final String templateCategory)	 {
		Validate.notNull(templateCategory,"templateCategory cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("isGlobal", true));
		
		criteria.createAlias("templateCategory","templateCategory")
		.add(Restrictions
		.eq("templateCategory.name", templateCategory));

		Template template = null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			template = (Template)criteria.list().get(0);
		}	
		
		return template;
	}

}
