package com.contento3.cms.security.group.dao;

import java.util.Collection;

import com.contento3.cms.security.group.model.Group;
import com.contento3.common.dao.GenericDao;

public interface GroupDao extends GenericDao <Group, String> {
	
	Collection <Group> findByGroupName(String GroupName);
	
}
