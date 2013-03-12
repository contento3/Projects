package com.contento3.dam.document.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.document.dao.DocumentTypeDao;
import com.contento3.dam.document.model.DocumentType;

/**
 * @author Syed Muhammad Ali
 * Service implementation for object of DocumentType
 */
public class DocumentTypeDaoHibernateImpl
	extends GenericDaoSpringHibernateTemplate<DocumentType, Integer>
	implements DocumentTypeDao {
	
	/* Constructor */
	public DocumentTypeDaoHibernateImpl() {
		super(DocumentType.class);
	}
	
	/* Finds the DocumentType of a Document by name */
	@Override
	public DocumentType findByName(final String typeName) {
		final Criteria criteria = this.getSession().createCriteria(DocumentType.class);
		criteria.add(Restrictions.eq("name", typeName));
		
		DocumentType documentType = null;
		
		if(!CollectionUtils.isEmpty(criteria.list()))
			documentType = (DocumentType) criteria.list().get(0);
		
		return documentType;
	}

	@Override
	public Collection<DocumentType> findAllTypes() {
		final Criteria criteria = this.getSession().createCriteria(DocumentType.class);
		
		return criteria.list();
	}
	

}