package com.contento3.dam.document.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.model.Document;

public class DocumentDaoHibernateImpl
	extends GenericDaoSpringHibernateTemplate<Document, Integer>
	implements DocumentDao {

	public DocumentDaoHibernateImpl() {
		super(Document.class);
	}


	@Override
	public Document findByUuid(final Integer accountId, final String uuid) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		
		criteria.add(Restrictions.eq("documentUuid", uuid))
				.add(Restrictions.eq("account.accountId", accountId));
		
		return (Document) criteria.uniqueResult();
	}

	@Override
	public Collection<Document> findByType(final Integer accountId, final String type) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId))
				.createCriteria("documentType")
				.add(Restrictions.eq("name", type));
		
		return criteria.list();
	}

	@Override
	public Collection<Document> findByAccountId(final Integer accountId) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId));
		
		return criteria.list();
	}
}
