package com.contento3.security.permission.service.impl;

import java.util.Collection;

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

	@Override
	public Integer create(PermissionDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		// TODO Auto-generated method stub
		final Permission permission = permissionAssembler.dtoToDomain(dto);
		Integer permissionId=permissionDao.persist(permission);
		return permissionId;
	}

	@Override
	public void delete(PermissionDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		permissionDao.delete(permissionAssembler.dtoToDomain(dtoToDelete));
	}

	@Override
	public Collection<PermissionDto> findPermissionByEntityId(Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionDto findById(Integer id) {
		// TODO Auto-generated method stub
		return permissionAssembler.domainToDto(permissionDao.findById(id));
		
	}
	@Override
	public void update(PermissionDto dtoToUpdate) {
		// TODO Auto-generated method stub
		permissionDao.update( permissionAssembler.dtoToDomain(dtoToUpdate) );
	}
	@Override
	public Collection<PermissionDto> findAllPermissions() {
		// TODO Auto-generated method stub
		return permissionAssembler.domainsToDtos(permissionDao.findAllPermissions());
	}

}
