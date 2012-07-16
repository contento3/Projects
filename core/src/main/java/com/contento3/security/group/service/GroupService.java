package com.contento3.security.group.service;

import java.util.Collection;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.service.Service;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;


/**
 * Serves as a service layer for group.
 * @author AARIJ
 *
 */
public interface GroupService extends Service<GroupDto> {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @returns collection of GroupDto Objects.
	 */
	GroupDto findByGroupName(String groupName);
	
	/**
	 * Returns the collection of all the Groups.
	 * @param
	 * @returns collection of GroupDto Objects.
	 */
	Collection <GroupDto> findAllGroups();
	
	/**
     * Creates a group
     * @param groupDto
     * @return
     */
	void create(final GroupDto groupDto);
	/**
	 * return group whose id match
	 * @param id
	 * @return
	 */
	GroupDto findById(Integer id);
	/**
	 * Update group item 
	 * @param groupDto
	 */
	void update(final GroupDto groupDto);
	/**
	 * Delete group 
	 * @param id
	 */
	void delete(final GroupDto group);
}
