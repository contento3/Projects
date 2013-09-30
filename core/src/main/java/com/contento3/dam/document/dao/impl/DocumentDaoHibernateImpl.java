package com.contento3.dam.document.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.model.Document;

/**
 * @author Syed Muhammad Ali
 * Service Implementation for objects of type Document
 */
public class DocumentDaoHibernateImpl
	extends GenericDaoSpringHibernateTemplate<Document, Integer>
	implements DocumentDao {
	
	/* Constructor */
	public DocumentDaoHibernateImpl() {
		super(Document.class);
	}

	/* Finds a document by using it's unique uid */
	@Override
	public Document findByUuid(final Integer accountId, final String uuid) {
		Validate.notNull(accountId,"accoundId cannot be null");
		Validate.notNull(uuid,"uuid cannot be null");
		
		Criteria criteria = this.getSession().createCriteria(Document.class);
		
		criteria.add(Restrictions.eq("documentUuid", uuid))
				.add(Restrictions.eq("account.accountId", accountId));
		
		Document document = null;
		
		if(!CollectionUtils.isEmpty(criteria.list()))
			document = (Document) criteria.list().get(0); 
		
		return document;
	}
	
	/* Finds document based on their types */
	@Override
	public Collection<Document> findByType(final Integer accountId, final String type) {
		Validate.notNull(type,"type cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId))
				.createCriteria("documentType")
				.add(Restrictions.eq("name", type));
		
		return criteria.list();
	}
	
	/* Finds the document associated with an account */
	@Override
	public Collection<Document> findByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId));
		
		return criteria.list();
	}

	@Override
	public Document findByTitle(final String title) {
		Validate.notNull(title,"title cannot be null");
		
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("documentTitle", title));
		
		Document document = null;
		
		if(!CollectionUtils.isEmpty(criteria.list()))
			document = (Document) criteria.list().get(0); 
		
		return document;
	}
}
