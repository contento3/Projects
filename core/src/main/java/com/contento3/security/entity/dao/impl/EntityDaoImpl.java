package com.contento3.security.entity.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.entity.dao.EntityDao;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.role.model.Role;

public class EntityDaoImpl extends GenericDaoSpringHibernateTemplate <PermissionEntity,Integer>
implements EntityDao{

	public EntityDaoImpl() {
		super(PermissionEntity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<PermissionEntity> findEntitiesByEntityId(Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionEntity findByEntityName(String entityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PermissionEntity> findAllEntities() {
		// TODO Auto-generated method stub
		final Criteria criteria = this.getSession()
			    .createCriteria(PermissionEntity.class);
			return criteria.list();
		
	}
	public PermissionEntity findById(int id) {
		// TODO Auto-generated method stub
		
		Criteria criteria = this.getSession()
				.createCriteria(PermissionEntity.class)
				.add(Restrictions
				.eq("entityId", id));
		PermissionEntity entity = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			entity = (PermissionEntity) criteria.list().get(0);
		}
		
		return entity;
	}
}
