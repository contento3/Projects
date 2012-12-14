package com.contento3.dam.document.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.document.dao.DocumentTypeDao;
import com.contento3.dam.document.model.DocumentType;

public class DocumentTypeHibernateImpl
	extends GenericDaoSpringHibernateTemplate<DocumentType, Integer>
	implements DocumentTypeDao {

	public DocumentTypeHibernateImpl() {
		super(DocumentType.class);
	}
	@Override
	public DocumentType findByName(String typeName) {
		Criteria criteria = this.getSession().createCriteria(DocumentType.class);
		criteria.add(Restrictions.eq("name", typeName));
		
		return (DocumentType) criteria.uniqueResult();
	}
	

}