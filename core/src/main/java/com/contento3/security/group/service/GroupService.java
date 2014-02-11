package com.contento3.security.group.service;

import java.util.Collection;

import com.contento3.common.service.StorableService;
import com.contento3.security.group.dto.GroupDto;


/**
 * Serves as a service layer for group.
 * @author AARIJ
 *
 */
public interface GroupService extends StorableService<GroupDto> {
	
	/**
	 * Returns the collection of Groups that matches the given/searched group name
	 * @param Group name
	 * @returns collection of GroupDto Objects.
	 */
	GroupDto findByGroupName(String groupName);

	/**
     * Creates a group
     * @param groupDto
     * @return
     */
	Integer create(final GroupDto groupDto);
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
	Collection<GroupDto> findByUserId(Integer id);
	void update(final GroupDto groupDto);
	/**
	 * Delete group 
	 * @param id
	 * @throws Exception 
	 */
	void deleteWithException(GroupDto group) throws Exception;

	/**
	 * Find Group by accountId
	 * @param accountId
	 * @return
	 */
	Collection<GroupDto> findByAccountId(Integer accountId);
}
