package com.contento3.cms.page.section.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.contento3.cms.page.section.dto.PageSectionTypeDto;
import com.contento3.cms.page.section.model.PageSectionType;
import com.contento3.cms.page.section.service.PageSectionTypeAssembler;

public class PageSectionTypeAssemblerImpl implements PageSectionTypeAssembler {
	
	@Override
	public PageSectionTypeDto domainToDto (final PageSectionType domain){
		PageSectionTypeDto dto = new PageSectionTypeDto();
		dto.setDescription(domain.getDescription());
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		return dto;
	}
	
	@Override
	public PageSectionType dtoToDomain (final PageSectionTypeDto dto){
		PageSectionType domain = new PageSectionType();
		domain.setDescription(dto.getDescription());
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		return domain;
	}

	@Override
	public Collection<PageSectionTypeDto> domainsToDtos(final Set<PageSectionType> domains){
		Collection <PageSectionTypeDto> dtos = new ArrayList<PageSectionTypeDto>();
		
		Iterator<PageSectionType> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return dtos;
	}

	@Override
	public Collection<PageSectionType> dtosToDomains (final Set<PageSectionTypeDto> dtos){
		Collection <PageSectionType> domains = new ArrayList<PageSectionType>();
		
		Iterator<PageSectionTypeDto> iterator = dtos.iterator();
		while (iterator.hasNext()){
			domains.add(dtoToDomain(iterator.next()));
		}
		return domains;
	}
	
}
