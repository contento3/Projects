package com.olive.cms.page.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.olive.cms.page.template.dto.TemplateDirectoryDto;
import com.olive.cms.page.template.model.TemplateDirectory;
import com.olive.cms.page.template.service.TemplateDirectoryAssembler;

public class TemplateDirectoryAssemblerImpl implements TemplateDirectoryAssembler{

	@Override
	public TemplateDirectory dtoToDomain(TemplateDirectoryDto dto) {
		TemplateDirectory domain = new TemplateDirectory();	
		domain.setId(dto.getId());
		domain.setGlobal(dto.isGlobal());
		domain.setParent(dto.getParent());
		domain.setDirectoryName(dto.getDirectoryName());
		return domain;
	}

	@Override
	public TemplateDirectoryDto domainToDto(TemplateDirectory domain) {
		TemplateDirectoryDto dto = new TemplateDirectoryDto();	
		dto.setId(domain.getId());
		dto.setGlobal(domain.isGlobal());
		dto.setParent(domain.getParent());
		dto.setDirectoryName(domain.getDirectoryName());
		return dto;
	}

	@Override
	public Collection<TemplateDirectoryDto> domainsToDtos(
			Collection<TemplateDirectory> domains) {
		Collection <TemplateDirectoryDto> dtos = new ArrayList<TemplateDirectoryDto>();
		
		Iterator<TemplateDirectory> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return dtos;
	}

	@Override
	public Collection<TemplateDirectory> dtosToDomains(
			Collection<TemplateDirectoryDto> dtos) {
		Collection <TemplateDirectory> domains = new ArrayList<TemplateDirectory>();
		
		Iterator<TemplateDirectoryDto> iterator = dtos.iterator();
		while (iterator.hasNext()){
			domains.add(dtoToDomain(iterator.next()));
		}
		
		return domains;
	}

}
