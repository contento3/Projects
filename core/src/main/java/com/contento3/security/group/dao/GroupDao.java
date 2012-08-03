package com.contento3.security.group.dao;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.group.model.Group;

/**
 * 
 * Data access layer to access data for Group entity.
 * 
 */
public interface GroupDao extends GenericDao <Group, Integer> {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @return
	 */
	Group findByGroupName(String groupName);
	
}
