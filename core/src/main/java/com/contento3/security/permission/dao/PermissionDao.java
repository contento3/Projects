package com.contento3.security.permission.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.role.model.Role;

public interface PermissionDao extends GenericDao<Permission, Integer>{
	
	public Collection<Permission> findAllPermissions();
}
