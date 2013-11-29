package com.contento3.security.entityoperation.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.entityoperation.dao.EntityOperationDao;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.role.model.Role;

public class EntityOperationDaoImpl extends GenericDaoSpringHibernateTemplate <EntityOperation,Integer>
implements EntityOperationDao{

	public EntityOperationDaoImpl() {
		super(EntityOperation.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<EntityOperation> findEntityOperationsByEntityOperationId(
			Integer entityOperationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityOperation findByEntityOperationName(String entityOperationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<EntityOperation> findAllEntityOperations() {
		// TODO Auto-generated method stub
		final Criteria criteria = this.getSession()
			    .createCriteria(EntityOperation.class);
			return criteria.list();
		
	}
	public EntityOperation findById(int id) {
		// TODO Auto-generated method stub
		
		Criteria criteria = this.getSession()
				.createCriteria(EntityOperation.class)
				.add(Restrictions
				.eq("entityOperationid", id));
		EntityOperation entityOperation = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			entityOperation = (EntityOperation) criteria.list().get(0);
		}
		
		return entityOperation;
	}

}
