package com.contento3.cms.security.group.dao;

import java.util.Collection;

import com.contento3.cms.security.group.model.Group;
import com.contento3.common.dao.GenericDao;

/**
 * 
 * Data access layer to access data for Group entity.
 * 
 */
public interface GroupDao extends GenericDao <Group, String> {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @return
	 */
	Collection <Group> findByGroupName(String groupName);
	
}
