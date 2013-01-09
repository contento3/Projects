package com.contento3.dam.document.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

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
		Criteria criteria = this.getSession().createCriteria(Document.class);
		
		criteria.add(Restrictions.eq("documentUuid", uuid))
				.add(Restrictions.eq("account.accountId", accountId));
		
		return (Document) criteria.uniqueResult();
	}
	
	/* Finds document based on their types */
	@Override
	public Collection<Document> findByType(final Integer accountId, final String type) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId))
				.createCriteria("documentType")
				.add(Restrictions.eq("name", type));
		
		return criteria.list();
	}
	
	/* Finds the document associated with an account */
	@Override
	public Collection<Document> findByAccountId(final Integer accountId) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId));
		
		return criteria.list();
	}

	@Override
	public Document findByTitle(final String title) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("documentTitle", title));
		
		return (Document) criteria.uniqueResult();
	}
}
