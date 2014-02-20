package com.contento3.security.permission.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.permission.model.Permission;

public interface PermissionDao extends GenericDao<Permission, Integer>{
	
	Collection<Permission> findAllPermissions();
	Collection<Permission> findPermissionsByRoleId(Integer RoleId);
	Permission findPermissionByEntityAndOperationId(Integer entityId, Integer operationId);
	
}
