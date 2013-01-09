package com.contento3.security.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.model.Permission;

public interface PermissionDao extends GenericDao<Permission,Integer>{

	/**
	 * Finds the Collection of Permission
	 * @param accountId
	 * @param role
	 * @return
	 */
	Collection <Permission> findByRole(Integer accountId,Integer role);

}
