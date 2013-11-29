package com.contento3.security.entityoperation.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.entityoperation.service.EntityOperationAssembler;

public class EntityOperationAssemblerImpl implements EntityOperationAssembler{

	@Override
	public EntityOperation dtoToDomain(EntityOperationDto dto) {
		// TODO Auto-generated method stub
		EntityOperation domain = new EntityOperation();
		domain.setEntityOperationId(dto.getId());
		domain.setEntityOperationName(dto.getName());
		return domain;
	}

	@Override
	public EntityOperationDto domainToDto(EntityOperation domain) {
		// TODO Auto-generated method stub
		EntityOperationDto dto = new EntityOperationDto();
		dto.setEntityOperationId(domain.getEntityId());
		dto.setEntityName(domain.getEntityOperationName());
		return dto;
	}

	@Override
	public Collection<EntityOperationDto> domainsToDtos(
			Collection<EntityOperation> domains) {
		// TODO Auto-generated method stub
		Collection<EntityOperationDto> dtos = new ArrayList<EntityOperationDto>();
		for(EntityOperation domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<EntityOperation> dtosToDomains(
			Collection<EntityOperationDto> dtos) {
		// TODO Auto-generated method stub
		Collection<EntityOperation> domains = new ArrayList<EntityOperation>();
		for(EntityOperationDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
