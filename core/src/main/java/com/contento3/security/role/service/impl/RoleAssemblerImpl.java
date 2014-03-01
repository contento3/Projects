package com.contento3.security.role.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.account.service.AccountAssembler;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;
import com.contento3.security.role.service.RoleAssembler;
import com.contento3.security.permission.service.PermissionAssembler;

public class RoleAssemblerImpl implements RoleAssembler{
	private AccountAssembler accountAssembler;
	private PermissionAssembler permissionAssembler;
	RoleAssemblerImpl(final AccountAssembler accountAssembler, final PermissionAssembler permissionAssembler)
	{
		this.accountAssembler = accountAssembler;
		this.permissionAssembler=permissionAssembler;
	}
	
	@Override
	public Role dtoToDomain(final RoleDto dto,final Role domain) {
		domain.setRoleId(dto.getId());
		domain.setRoleName(dto.getName());
		domain.setDescription(dto.getRoleDesc());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		domain.setPermissions(permissionAssembler.dtosToDomains(dto.getPermissions()));
		return domain;
	}

	@Override
	public RoleDto domainToDto(final Role domain,final RoleDto dto) {
		
		dto.setRoleId(domain.getRoleId());
		dto.setRoleDesc(domain.getDescription());
		dto.setRoleName(domain.getRoleName());
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		dto.setPermissions(permissionAssembler.domainsToDtos(domain.getPermissions()));
		return dto;
	}

	@Override
	public Collection<RoleDto> domainsToDtos(Collection<Role> domains) {
		final Collection<RoleDto> dtos = new ArrayList<RoleDto>();
		for(Role domain:domains){
			dtos.add(domainToDto(domain,new RoleDto()));
		}
		return dtos;
	}

	@Override
	public Collection<Role> dtosToDomains(Collection<RoleDto> dtos) {
		final Collection<Role> domains = new ArrayList<Role>();
		for(RoleDto dto:dtos){
			domains.add(dtoToDomain(dto,new Role()));
		}
		return domains;
	}

}
