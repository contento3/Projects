package com.contento3.dam.storagetype.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.storagetype.dao.StorageTypeDao;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * Dao implementation for the objects of StorageType
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
	public StorageType findByName(final String name){
		Criteria criteria = this.getSession().createCriteria(StorageType.class);
		criteria.add(Restrictions.eq("name", name));
		
		StorageType storageType = null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			storageType = (StorageType)criteria.list().get(0);
		}	
		
		return storageType;
	}
}
