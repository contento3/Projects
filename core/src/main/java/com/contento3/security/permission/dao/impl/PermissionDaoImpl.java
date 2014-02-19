package com.contento3.security.permission.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.permission.model.Permission;

public class PermissionDaoImpl extends GenericDaoSpringHibernateTemplate <Permission,Integer>
implements PermissionDao{

	public PermissionDaoImpl() {
		super(Permission.class);
	}

	@Override
	public Permission findById(Integer id) {
		final Criteria criteria = this.getSession()
				.createCriteria(Permission.class)
				.add(Restrictions
				.eq("permissionId", id));
		Permission permission = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			permission = (Permission) criteria.list().get(0);
		}
		
		return permission;
	}
	
	public Collection<Permission> findAllPermissions() {
		final Criteria criteria = this.getSession()
			    .createCriteria(Permission.class);
			return criteria.list();
		
	}
	
	@Override
	public Collection<Permission> findPermissionsByRoleId(Integer RoleId) {
		final Criteria criteria = this.getSession()
				.createCriteria(Permission.class)
				      .createAlias("roles", "role")
				      .add(Restrictions.eq("role.roleid", RoleId));
				  Collection<Permission> permissions = criteria.list();
				  return permissions;
	}

	@Override
	public Permission findPermissionByEntityAndOperationId(final Integer entityId,final Integer operationId) {
		Permission permission = null;
		final Criteria criteria = this.getSession()
				.createCriteria(Permission.class)
					.add(Restrictions.eq("entityOperation.entityOperationId", operationId))
				    .add(Restrictions.eq("entity.entityId", entityId));
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			permission = (Permission) criteria.list().get(0);
		}
		
		return permission;
	}
}
