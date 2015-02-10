package com.contento3.cms.page.template.dao.impl;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

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
	public Collection<TemplateDirectory> findRootDirectories(final boolean isGlobal,final Integer accountId){
		Validate.notNull(isGlobal,"isGlobal cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.isNull("parent"))
		.add(Restrictions
		.eq("isGlobal", isGlobal))
		.add(Restrictions
		.eq("account.accountId", accountId));

		return criteria.list();
	}

	@Override
	public TemplateDirectory findByName(final String name,final boolean isGlobal,final Integer accountId){
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(isGlobal,"isGlobal cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("directoryName", name))
		.add(Restrictions
		.eq("isGlobal", isGlobal))
		.add(Restrictions
		.eq("account.accountId", accountId));

		final List <TemplateDirectory> directories = criteria.list();
		
		TemplateDirectory templateDirectory = null;
		if (!CollectionUtils.isEmpty(directories)){
			templateDirectory = (TemplateDirectory) criteria.list().get(0);
		}
		
		return templateDirectory;
	}

	@Override
	public Collection<TemplateDirectory> findChildDirectories(final Integer parentId,final Integer accountId){
		Validate.notNull(parentId,"parentId cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("parent.id", parentId))
		.add(Restrictions
		.eq("account.accountId", accountId));

		return criteria.list();
	}

	@Override
	public Collection<TemplateDirectory> findChildDirectories(Integer parentId) {
		Validate.notNull(parentId,"parentId cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("parent.id", parentId));

		return criteria.list();
	}

	@Override
	public TemplateDirectory findChildDirectory(Integer parentId,String directoryToFind, Integer accountId) {
		Validate.notNull(parentId,"parentId cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(directoryToFind,"directoryToFind cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(TemplateDirectory.class)
		.setCacheable(true)
		.setCacheRegion(CACHE_REGION)
		.add(Restrictions
		.eq("parent.id", parentId)).add(Restrictions
		.eq("directoryName", directoryToFind))
		.add(Restrictions
		.eq("account.accountId", accountId));

		final List <TemplateDirectory> templateDirectoryList = criteria.list();
		TemplateDirectory templateDirectory = null;
		if (!CollectionUtils.isEmpty(templateDirectoryList)){
			templateDirectory = templateDirectoryList.get(0);
		}
		
		return templateDirectory;
	}

}
