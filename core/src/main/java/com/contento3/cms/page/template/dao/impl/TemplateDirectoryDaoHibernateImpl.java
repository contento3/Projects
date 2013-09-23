package com.contento3.cms.page.template.dao.impl;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class TemplateDirectoryDaoHibernateImpl 
		extends GenericDaoSpringHibernateTemplate<TemplateDirectory, Integer> 
		implements TemplateDirectoryDao {
	
	private static final String CACHE_REGION = "com.contento3.cms.page.template.model.TemplateDirectory";

	
	public TemplateDirectoryDaoHibernateImpl (){
		super(TemplateDirectory.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateDirectory> findRootDirectories(final boolean isGlobal){
		Validate.notNull(isGlobal,"isGlobal cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.isNull("parent"))
		.add(Restrictions
		.eq("isGlobal", isGlobal));

		return criteria.list();
	}

	@Override
	public TemplateDirectory findByName(final String name,final boolean isGlobal){
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(isGlobal,"isGlobal cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("directoryName", name))
		.add(Restrictions
		.eq("isGlobal", isGlobal));

		TemplateDirectory templateDirectory = (TemplateDirectory) criteria.list().get(0);
		return templateDirectory;
	}

	@Override
	public Collection<TemplateDirectory> findChildDirectories(final Integer parentId){
		Validate.notNull(parentId,"parentId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("parent.id", parentId));

		return criteria.list();
	}

}
