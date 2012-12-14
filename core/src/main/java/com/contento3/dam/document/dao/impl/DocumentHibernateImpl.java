package com.contento3.dam.document.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.model.Document;

public class DocumentHibernateImpl
	extends GenericDaoSpringHibernateTemplate<Document, Integer>
	implements DocumentDao {

	public DocumentHibernateImpl() {
		super(Document.class);
	}


	@Override
	public Document findByUuid(Integer accountId, String uuid) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		
		criteria.add(Restrictions.eq("document_uuid", uuid))
				.add(Restrictions.eq("account.accountId", accountId));
		
		return (Document) criteria.uniqueResult();
	}

	@Override
	public Collection<Document> findByType(Integer accountId, String type) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId))
				.createCriteria("document_type")
				.add(Restrictions.eq("name", type));
		
		return criteria.list();
	}

	@Override
	public Collection<Document> findByAccountId(Integer accountId) {
		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("account.accountId", accountId));
		
		return criteria.list();
	}

}
