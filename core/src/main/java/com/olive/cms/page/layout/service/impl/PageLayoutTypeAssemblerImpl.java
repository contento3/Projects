package com.olive.cms.page.layout.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.olive.cms.page.layout.dto.PageLayoutTypeDto;
import com.olive.cms.page.layout.model.PageLayoutType;
import com.olive.cms.page.layout.service.PageLayoutTypeAssembler;

public class PageLayoutTypeAssemblerImpl implements PageLayoutTypeAssembler {

	@Override
	public PageLayoutType dtoToDomain(final PageLayoutTypeDto dto){
		PageLayoutType pageLayoutType = new PageLayoutType();
		pageLayoutType.setName(dto.getName());
		pageLayoutType.setId(dto.getId());
		pageLayoutType.setDescription(dto.getDescription());
		return pageLayoutType;
	}
	
	@Override
	public PageLayoutTypeDto domainToDto(final PageLayoutType domain){
		PageLayoutTypeDto dto = new PageLayoutTypeDto();
		dto.setName(domain.getName());
		dto.setId(domain.getId());
		dto.setDescription(domain.getDescription());
		return dto;
	}
	
	@Override
	public Collection<PageLayoutTypeDto> domainsToDtos(final Collection<PageLayoutType> domains){
		Collection<PageLayoutTypeDto> dtos = new ArrayList<PageLayoutTypeDto>();
		for (PageLayoutType page : domains){
			dtos.add(domainToDto(page));
		}
		return dtos;
	}

	@Override
	public Collection<PageLayoutType> dtosToDomains(final Collection<PageLayoutTypeDto> dtos){
		Collection<PageLayoutType> domains = new ArrayList<PageLayoutType>();
		for (PageLayoutTypeDto dto : dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

}
