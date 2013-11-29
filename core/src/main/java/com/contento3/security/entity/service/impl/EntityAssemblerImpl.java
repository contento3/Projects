package com.contento3.security.entity.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entity.service.EntityAssembler;


public class EntityAssemblerImpl implements EntityAssembler{

	@Override
	public PermissionEntity dtoToDomain(EntityDto dto) {
		// TODO Auto-generated method stub
		PermissionEntity domain = new PermissionEntity();
		domain.setEntityId(dto.getId());
		domain.setEntityName(dto.getName());
		return domain;
	}

	@Override
	public EntityDto domainToDto(PermissionEntity domain) {
		// TODO Auto-generated method stub
		EntityDto dto = new EntityDto();
		dto.setEntityId(domain.getEntityId());
		dto.setEntityName(domain.getEntityName());
		return dto;
	}

	@Override
	public Collection<EntityDto> domainsToDtos(
			Collection<PermissionEntity> domains) {
		// TODO Auto-generated method stub
		Collection<EntityDto> dtos = new ArrayList<EntityDto>();
		for(PermissionEntity domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<PermissionEntity> dtosToDomains(Collection<EntityDto> dtos) {
		// TODO Auto-generated method stub
		Collection<PermissionEntity> domains = new ArrayList<PermissionEntity>();
		for(EntityDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
