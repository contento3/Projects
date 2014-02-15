package com.contento3.security.permission.service.impl;

import java.util.Collection;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.security.permission.service.PermissionAssembler;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;

public class PermissionServiceImpl implements PermissionService{
	private PermissionDao permissionDao;
	private PermissionAssembler permissionAssembler;
	PermissionServiceImpl(PermissionDao permissionDao, PermissionAssembler permissionAssembler)
	{
		this.permissionDao=permissionDao;
		this.permissionAssembler= permissionAssembler;
	}
	
	@RequiresPermissions("PERMISSION:ADD")
	@Override
	public Integer create(PermissionDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		// TODO Auto-generated method stub
		final Permission permission = permissionAssembler.dtoToDomain(dto);
		Integer permissionId=permissionDao.persist(permission);
		return permissionId;
	}
	@RequiresPermissions("PERMISSION:DELETE")
	@Override
	public void delete(PermissionDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		permissionDao.delete(permissionAssembler.dtoToDomain(dtoToDelete));
	}
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public Collection<PermissionDto> findPermissionByEntityId(Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public PermissionDto findById(Integer id) {
		// TODO Auto-generated method stub
		return permissionAssembler.domainToDto(permissionDao.findById(id));
		
	}
	@RequiresPermissions("PERMISSION:EDIT")
	@Override
	public void update(PermissionDto dtoToUpdate) {
		// TODO Auto-generated method stub
		permissionDao.update( permissionAssembler.dtoToDomain(dtoToUpdate) );
	}
	@RequiresPermissions("PERMISSION:VIEW_LISTING")
	@Override
	public Collection<PermissionDto> findAllPermissions() {
		// TODO Auto-generated method stub
		return permissionAssembler.domainsToDtos(permissionDao.findAllPermissions());
	}
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public Collection<PermissionDto> findNullParentIdPermission(Integer roleId) {
		// TODO Auto-generated method stub
		Collection<Permission> permissions = permissionDao.findNullParentIdPermission(roleId);
		Collection<PermissionDto> permissionDtos = permissionAssembler.domainsToDtos(permissions);
		return permissionDtos;
	}
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public Collection<PermissionDto> findPermissionByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		Collection<Permission> permissions = permissionDao.findPermissionsByRoleId(roleId);
		Collection<PermissionDto> permissionDtos = permissionAssembler.domainsToDtos(permissions);
		return permissionDtos;
	}

}
