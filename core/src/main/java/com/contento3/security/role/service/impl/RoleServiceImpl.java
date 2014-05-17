package com.contento3.security.role.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.group.model.Group;
import com.contento3.security.model.Permission;
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
	
	@RequiresPermissions("ROLE:VIEW_LISTING")
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
	public void update(RoleDto dtoToUpdate) throws EntityAlreadyFoundException {
		Validate.notNull(dtoToUpdate,"roleDto cannot be null");
		Role role = roleDao.findById(dtoToUpdate.getRoleId());
		if (null!=role && !role.getRoleId().equals(role.getRoleId())){
			throw new EntityAlreadyFoundException("Group with same name already exist");
		}
		else if (null==role) {
			role = new Role();
		}
		
		roleDao.update(roleAssembler.dtoToDomain(dtoToUpdate,role));
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
	
	@Override
	public boolean isPermissionAssigned(Integer permissionId) {
		
		Collection<Permission> permissions = roleDao.findByPermissionId(permissionId);
		if(permissions != null && permissions.size() > 0)
			return true;
		return false;
	}

}
