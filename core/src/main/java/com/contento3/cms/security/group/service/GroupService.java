package com.contento3.cms.security.group.service;

import java.util.Collection;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.security.group.dto.GroupDto;
import com.contento3.cms.security.group.model.Group;
import com.contento3.common.service.Service;

public interface GroupService extends Service<GroupDto> {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @return
	 */
	Collection <GroupDto> findByGroupName(String groupName);
	
	/**
	 * Returns the collection of all the Groups.
	 * @param
	 * @return
	 */
	Collection <GroupDto> findAllGroups();
	
	/**
     * Creates a group
     * @param groupDto
     * @return
     */
	void create(final GroupDto groupDto);
	

}
