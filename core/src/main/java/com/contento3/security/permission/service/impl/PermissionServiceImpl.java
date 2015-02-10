package com.contento3.security.permission.service.impl;

import java.util.Collection;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.permission.service.PermissionAssembler;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.security.role.service.RoleService;

public class PermissionServiceImpl implements PermissionService{
	
	final static String MSG_ENTITY_CANT_DELETE = "Permission is assigned to role.";
	
	private PermissionDao permissionDao;
	private RoleService roleService;
	
	private PermissionAssembler permissionAssembler;
	PermissionServiceImpl(PermissionDao permissionDao, PermissionAssembler permissionAssembler, final RoleService roleService)
	{
		this.permissionDao=permissionDao;
		this.permissionAssembler= permissionAssembler;
		this.roleService = roleService;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("PERMISSION:ADD")
	@Override
	public Integer create(PermissionDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		
		Permission permission = permissionDao.findPermissionByEntityAndOperationId(dto.getEntity().getId(),dto.getEntityOperation().getId());

		if (permission!=null){
			throw new EntityAlreadyFoundException("Permission already found");
		}

		permission = permissionAssembler.dtoToDomain(dto);
		return permissionDao.persist(permission);
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("PERMISSION:DELETE")
	@Override
	public void delete(PermissionDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		
		boolean isAssigned = roleService.isPermissionAssigned(dtoToDelete.getId());
		
		if(isAssigned) throw new EntityCannotBeDeletedException(MSG_ENTITY_CANT_DELETE);
		
		permissionDao.delete(permissionAssembler.dtoToDomain(dtoToDelete));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public Collection<PermissionDto> findPermissionByEntityId(Integer entityId) {
		return null;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "PERMISSION:VIEW", "PERMISSION:EDIT" }, logical = Logical.OR) 
	@Override
	public PermissionDto findById(Integer id) {
		return permissionAssembler.domainToDto(permissionDao.findById(id));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("PERMISSION:EDIT")
	@Override
	public void update(final PermissionDto dtoToUpdate) throws EntityAlreadyFoundException {
		Permission permission = permissionDao.findPermissionByEntityAndOperationId(dtoToUpdate.getEntity().getId(),dtoToUpdate.getEntityOperation().getId());
		
		if (permission!=null && !permission.getPermissionId().equals(dtoToUpdate.getId())){
			throw new EntityAlreadyFoundException("Permission already found");
		}
		else if (permission==null){
			permissionDao.update(permissionAssembler.dtoToDomain(dtoToUpdate));
		}
		else {
			return;
		}
	}
	
	@RequiresPermissions("PERMISSION:VIEW_LISTING") 
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<PermissionDto> findAllPermissions() {
		return permissionAssembler.domainsToDtos(permissionDao.findAllPermissions());
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("PERMISSION:VIEW")
	@Override
	public Collection<PermissionDto> findPermissionByRoleId(final Integer roleId) {
		Collection<Permission> permissions = permissionDao.findPermissionsByRoleId(roleId);
		Collection<PermissionDto> permissionDtos = permissionAssembler.domainsToDtos(permissions);
		return permissionDtos;
	}

}
