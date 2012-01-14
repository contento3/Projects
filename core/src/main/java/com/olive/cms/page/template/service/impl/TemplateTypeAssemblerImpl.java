package com.olive.cms.page.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.olive.cms.page.template.dto.TemplateTypeDto;
import com.olive.cms.page.template.model.TemplateType;
import com.olive.cms.page.template.service.TemplateTypeAssembler;

public class TemplateTypeAssemblerImpl implements TemplateTypeAssembler {

	@Override
	public TemplateType dtoToDomain(TemplateTypeDto dto) {
		TemplateType domain = new TemplateType();
		domain.setTemplateTypeId(dto.getTemplateTypeId());
		domain.setTemplateTypeName(dto.getTemplateTypeName());
		domain.setDescription(dto.getDescription());
		domain.setContentType(dto.getContentType());
		return domain;
	}

	@Override
	public TemplateTypeDto domainToDto(TemplateType domain) {
		TemplateTypeDto dto = new TemplateTypeDto();
		dto.setTemplateTypeId(domain.getTemplateTypeId());
		dto.setTemplateTypeName(domain.getTemplateTypeName());
		dto.setDescription(domain.getDescription());
		dto.setContentType(domain.getContentType());
		
		return dto;
	}

	@Override
	public Collection<TemplateTypeDto> domainsToDtos(
			Collection<TemplateType> domains) {
		Collection <TemplateTypeDto> dtos = new ArrayList<TemplateTypeDto>();
		
		Iterator<TemplateType> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return dtos;
	}

	@Override
	public Collection<TemplateType> dtosToDomains(
			Collection<TemplateTypeDto> dtos) {
		Collection <TemplateType> domains = new ArrayList<TemplateType>();
		
		Iterator<TemplateTypeDto> iterator = dtos.iterator();
		while (iterator.hasNext()){
			domains.add(dtoToDomain(iterator.next()));
		}
		
		return domains;
	}

}
