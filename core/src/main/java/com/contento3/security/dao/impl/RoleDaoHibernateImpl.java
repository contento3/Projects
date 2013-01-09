package com.contento3.security.dao.impl;

import java.util.Collection;

import com.contento3.security.model.Role;

/**
 * Data access layer for Role
 * @author hammad.afridi
 *
 */
public interface RoleDaoHibernateImpl {

	/**
	 * @param accountId Id of a account
	 * @param userId Id of a user
	 * @return Collection <Role>
	 */
	Collection <Role> findByUser(Integer accountId,Integer userId);
	
	/**
	 * Find the roles for a group
	 * @param accountId Id for the account
	 * @param groupId Id for the group
	 * @return Collection <Role>
	 */
	Collection <Role> findByGroup(Integer accountId,Integer groupId);
}
