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
	//private PermissionAssembler permissionAssembler;
	RoleAssemblerImpl(final AccountAssembler accountAssembler)
 {
	 this.accountAssembler = accountAssembler;
//	 this.permissionAssembler=permissionAssembler;
 }
	@Override
	public Role dtoToDomain(RoleDto dto) {
		// TODO Auto-generated method stub
		Role domain = new Role();
		domain.setRoleId(dto.getRoleid());
		domain.setRoleName(dto.getRoleName());
		domain.setDescription(dto.getRoleDesc());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
		
	}

	@Override
	public RoleDto domainToDto(Role domain) {
		// TODO Auto-generated method stub
		RoleDto dto = new RoleDto();
		dto.setRoleid(domain.getRoleId());
		dto.setRoleDesc(domain.getDescription());
		dto.setRoleName(domain.getRoleName());
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		
		return dto;
	}

	@Override
	public Collection<RoleDto> domainsToDtos(Collection<Role> domains) {
		// TODO Auto-generated method stub
		Collection<RoleDto> dtos = new ArrayList<RoleDto>();
		for(Role domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<Role> dtosToDomains(Collection<RoleDto> dtos) {
		// TODO Auto-generated method stub
		Collection<Role> domains = new ArrayList<Role>();
		for(RoleDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}
	/*@Override
	/*public Role dtoToDomain(RoleDto dto, Role domain) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
