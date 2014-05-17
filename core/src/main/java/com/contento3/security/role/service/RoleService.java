package com.contento3.security.role.service;
import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.SimpleService;
import com.contento3.security.model.Permission;
import com.contento3.security.role.dto.RoleDto;
public interface RoleService extends SimpleService<RoleDto> {
	/**
	 * Find user by account id
	 * @param accountId
	 * @return
	 */
	Collection<RoleDto> findRolesByAccountId(final Integer accountId);
	public Collection<RoleDto> findRolesByGroupId(Integer Id);
	/**
	 * Find user by name
	 * @param name
	 * @return
	 */
	RoleDto findRoleByName(final String name);

	/**
	 * return group whose id match
	 * @param id
	 * @return
	 */
	RoleDto findById(Integer id);
	/**
	 * Update group item 
	 * @param groupDto
	 * @throws EntityAlreadyFoundException 
	 */
	void update(final RoleDto roleDto) throws EntityAlreadyFoundException;
	
	/**
	 * Finds role by Id
	 * @param Id Primary key roleId
	 * @return RoleDto
	 */
	RoleDto findRoleById(Integer Id);
	
	/**
	 * Check if permission assigned to role.
	 * @param permissionId
	 * @return
	 */
	boolean isPermissionAssigned(Integer permissionId);

}
