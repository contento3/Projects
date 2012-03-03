package com.contento3.cms.security.group.service;

import java.util.Collection;
import com.contento3.cms.security.group.dto.GroupDto;
import com.contento3.cms.security.group.model.Group;

public interface GroupService {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @return
	 */
	Collection <GroupDto> findByGroupName(String groupName);

}
