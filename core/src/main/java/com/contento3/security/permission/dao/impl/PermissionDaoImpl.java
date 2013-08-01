package com.contento3.security.permission.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.role.dao.RoleDao;
import com.contento3.security.role.model.Role;

public class PermissionDaoImpl extends GenericDaoSpringHibernateTemplate <Permission,Integer>
implements PermissionDao{

	public PermissionDaoImpl() {
		super(Permission.class);
		// TODO Auto-generated constructor stub
	}
	public Permission findById(int id) {
		// TODO Auto-generated method stub
		
		Criteria criteria = this.getSession()
				.createCriteria(Permission.class)
				.add(Restrictions
				.eq("permissionid", id));
		Permission permission = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			permission = (Permission) criteria.list().get(0);
		}
		
		return permission;
	}
	
	public Collection<Permission> findAllPermissions() {
		// TODO Auto-generated method stub
		final Criteria criteria = this.getSession()
			    .createCriteria(Permission.class);
			return criteria.list();
		
	}

}
