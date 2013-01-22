package com.contento3.dam.storagetype.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.storagetype.dao.StorageTypeDao;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * Service implementation for the objects of StorageType
 * @author Syed Muhammad Ali
 *
 */
public class StorageTypeHibernateImpl
	extends GenericDaoSpringHibernateTemplate<StorageType, Integer>
	implements StorageTypeDao {

	public StorageTypeHibernateImpl() {
		super(StorageType.class);
	}
	
	@Override
	public Object findByName(final String name){
		Criteria c = this.getSession().createCriteria(StorageType.class);
		c.add(Restrictions.eq("name", name));
		
		return c.list().get(0);
	}
}