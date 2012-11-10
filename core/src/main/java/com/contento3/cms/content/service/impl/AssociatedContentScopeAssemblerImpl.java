package com.contento3.cms.content.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.cms.content.service.AssociatedContentScopeAssembler;

public class AssociatedContentScopeAssemblerImpl implements
		AssociatedContentScopeAssembler {

	@Override
	public AssociatedContentScope dtoToDomain(final AssociatedContentScopeDto dto) {
		AssociatedContentScope domain = new AssociatedContentScope();
		domain.setId(dto.getId());
		domain.setScope(dto.getScope());
		domain.setType(dto.getType());
		return domain;
	}

	@Override
	public AssociatedContentScopeDto domainToDto(final AssociatedContentScope domain) {
		AssociatedContentScopeDto dto = new AssociatedContentScopeDto();
		dto.setId(domain.getId());
		dto.setScope(domain.getScope());
		dto.setType(domain.getType());
		return dto;
	}

	@Override
	public Collection<AssociatedContentScopeDto> domainsToDtos(final Collection<AssociatedContentScope> domains) {
		Collection<AssociatedContentScopeDto> dtos = new ArrayList<AssociatedContentScopeDto>();
		for(AssociatedContentScope domain : domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<AssociatedContentScope> dtosToDomains(final Collection<AssociatedContentScopeDto> dtos) {
		Collection<AssociatedContentScope> domains = new ArrayList<AssociatedContentScope>();
		for(AssociatedContentScopeDto dto : dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

}