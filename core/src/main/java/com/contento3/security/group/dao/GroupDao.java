package com.contento3.security.group.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.group.model.Group;

/**
 * 
 * Data access layer to access data for Group entity.
 * 
 */
public interface GroupDao extends GenericDao <Group, Integer> {
	
	/**
	 * Returns a Groups that matches the given/searched group name
	 * @param Group name
	 * @return
	 */
	Group findByGroupName(String groupName);
	
	/**
	 * Returns the collection of Groups that are associated to this given user 
	 * @param userid
	 * @return
	 */
	Collection<Group> findByUserId(Integer userid);

	/**
	 * Returns the collection of Groups that are associated to this account 
	 * @param accountId
	 * @return
	 */
	Collection<Group> findByAccountId(Integer accountId);
	
}
