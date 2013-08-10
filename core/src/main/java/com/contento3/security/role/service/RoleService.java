package com.contento3.security.role.service;
import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.role.dto.RoleDto;
public interface RoleService extends Service<RoleDto> {
	/**
	 * Find user by account id
	 * @param accountId
	 * @return
	 */
	Collection<RoleDto> findRolesByAccountId(final Integer accountId);
	
	/**
	 * Find user by name
	 * @param name
	 * @return
	 */
	RoleDto findRoleByName(final String name);

	/**
	 * Updates the SalterHibernateUserDto
	 * @param SalterHibernateUserDto
	 * @return void
	 */
	Collection <RoleDto> findAllRoles();
	

	//Integer create(final RoleDto roleDto);
	/**
	 * return group whose id match
	 * @param id
	 * @return
	 */
	RoleDto findById(Integer id);
	/**
	 * Update group item 
	 * @param groupDto
	 */
	void update(final RoleDto roleDto);
}
