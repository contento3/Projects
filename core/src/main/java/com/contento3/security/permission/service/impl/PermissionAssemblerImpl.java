package com.contento3.security.permission.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.security.entity.service.EntityAssembler;
import com.contento3.security.entityoperation.service.EntityOperationAssembler;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.permission.service.PermissionAssembler;

public class PermissionAssemblerImpl implements PermissionAssembler{
	private EntityAssembler entityAssembler;
	private EntityOperationAssembler entityOperationAssembler;
	PermissionAssemblerImpl(final EntityAssembler entityAssembler,EntityOperationAssembler entityOperationAssembler)
	{
		this.entityAssembler=entityAssembler;
		this.entityOperationAssembler=entityOperationAssembler;
	}

	@Override
	public Permission dtoToDomain(PermissionDto dto) {
		Permission domain = new Permission();
		domain.SetPermissionId(dto.getId());
		domain.SetEntity(entityAssembler.dtoToDomain(dto.getEntity()));
		domain.SetEntityOperation(entityOperationAssembler.dtoToDomain(dto.getEntityOperation()));
		return domain;
	}

	@Override
	public PermissionDto domainToDto(Permission domain) {
		PermissionDto dto = new PermissionDto();
		dto.setPermissionId(domain.getPermissionId());
		dto.setEntity(entityAssembler.domainToDto(domain.getEntity()));
		dto.setEntityOperation(entityOperationAssembler.domainToDto(domain.getEntityOperation()));
		return dto;
	}

	@Override
	public Collection<PermissionDto> domainsToDtos(
			final Collection<Permission> domains) {
		Collection<PermissionDto> dtos = new ArrayList<PermissionDto>();
		for(Permission domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<Permission> dtosToDomains(final Collection<PermissionDto> dtos) {
		Collection<Permission> domains = new ArrayList<Permission>();
		for(PermissionDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
