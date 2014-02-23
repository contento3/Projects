package com.contento3.security.role.service.impl;

import java.util.Collection;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.role.dao.RoleDao;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;
import com.contento3.security.role.service.RoleAssembler;
import com.contento3.security.role.service.RoleService;
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService{

	private  RoleDao roleDao;
	
	private RoleAssembler roleAssembler;
	
	public RoleServiceImpl(final RoleDao roleDao,final RoleAssembler roleAssembler)
	{
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
	}
	
	@RequiresPermissions("ROLE:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(RoleDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		final Role role = roleAssembler.dtoToDomain(dto,new Role());
		Integer roleId = roleDao.persist(role);
		return roleId;
	}
	
	@RequiresPermissions("ROLE:DELETE")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(RoleDto dtoToDelete)	throws EntityCannotBeDeletedException {
		roleDao.delete(roleAssembler.dtoToDomain(dtoToDelete,new Role()));
	}
	
	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<RoleDto> findRolesByAccountId(Integer accountId) {
		return roleAssembler.domainsToDtos(roleDao.findRolesByAccountId(accountId));
	}
	
	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public RoleDto findRoleByName(String name) {
		return roleAssembler.domainToDto(roleDao.findByRolename(name),new RoleDto());		
	}

	@RequiresPermissions("ROLE:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(RoleDto dtoToUpdate) {
		roleDao.update(roleAssembler.dtoToDomain(dtoToUpdate,new Role()));
	}

	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public RoleDto findById(final Integer id) {
		return roleAssembler.domainToDto(roleDao.findById(id),new RoleDto());
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("ROLE:VIEW")
	@Override
	public Collection<RoleDto> findRolesByGroupId(final Integer Id) {
		return roleAssembler.domainsToDtos(roleDao.findRolesByGroupId(Id));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("ROLE:VIEW")
	@Override
	public RoleDto findRoleById(final Integer Id) {
		return roleAssembler.domainToDto(roleDao.findById(Id),new RoleDto());
	}

}
