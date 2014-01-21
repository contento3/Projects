package com.contento3.dam.content.library.dao.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.content.library.dao.ContentStorageLibraryMappingDao;
import com.contento3.dam.content.library.model.ContentStorageLibraryMapping;

public class ContentStorageLibraryMappingDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<ContentStorageLibraryMapping, Integer> implements ContentStorageLibraryMappingDao {

	public ContentStorageLibraryMappingDaoHibernateImpl() {
		super(ContentStorageLibraryMapping.class);
	}

	@Override
	public ContentStorageLibraryMapping findByLibraryAndContentType(
		final String libraryName, final String contentType, final Integer accountId) {

		Validate.notNull(libraryName ,"libraryName cannot be null");
		Validate.notNull(accountId ,"accountId cannot be null");
		Validate.notNull(contentType ,"contentType cannot be null");
		
		final Criteria criteria = this.getSession()
								.createCriteria(ContentStorageLibraryMapping.class)
								.add(Restrictions.eq("contentType", contentType));

		criteria.createAlias("library.account","account")
		.add(Restrictions
		.eq("account.accountId", accountId));

		criteria.createAlias("library","library")
		.add(Restrictions
		.eq("library.name", libraryName));

		ContentStorageLibraryMapping mapping = null; 
		final List <ContentStorageLibraryMapping> mappingList = criteria.list();	
		if (!CollectionUtils.isEmpty(mappingList)){
			mapping = mappingList.get(0);
		}
		
		return mapping;
	}
	
	@Override
	public ContentStorageLibraryMapping findByLibraryId(
		final Integer libraryId, final Integer accountId) {

		Validate.notNull(libraryId ,"libraryId cannot be null");
		Validate.notNull(accountId ,"accountId cannot be null");
			
		final Criteria criteria = this.getSession()
								.createCriteria(ContentStorageLibraryMapping.class);
		
		criteria.createAlias("library.account","account")
		.add(Restrictions
		.eq("account.accountId", accountId));

		criteria.createAlias("library","library")
		.add(Restrictions
		.eq("library.id", libraryId));

		ContentStorageLibraryMapping mapping = null; 
		final List <ContentStorageLibraryMapping> mappingList = criteria.list();	
		if (!CollectionUtils.isEmpty(mappingList)){
			mapping = mappingList.get(0);
	}

		return mapping;
	}

}
