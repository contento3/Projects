package com.olive.cms.page.section.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.olive.cms.page.section.dto.PageSectionDto;
import com.olive.cms.page.section.model.PageSection;
import com.olive.cms.page.section.service.PageSectionAssembler;
import com.olive.cms.page.section.service.PageSectionTypeAssembler;

public class PageSectionAssemblerImpl implements PageSectionAssembler {
	
	private PageSectionTypeAssembler pageSectionTypeAssembler;
	
	public PageSectionAssemblerImpl(final PageSectionTypeAssembler pageSectionTypeAssembler){
		this.pageSectionTypeAssembler = pageSectionTypeAssembler;
	}
	
	@Override
	public PageSectionDto domainToDto (final PageSection domain){
		PageSectionDto dto = new PageSectionDto();
		dto.setDescription(domain.getDescription());
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setTemplateMarkup(domain.getTemplateMarkup());
		dto.setSectionTypeDto(pageSectionTypeAssembler.domainToDto(domain.getSectionType()));
		return dto;
	}

	@Override
	public PageSection dtoToDomain (final PageSectionDto dto){
		PageSection domain = new PageSection();
		domain.setDescription(dto.getDescription());
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setTemplateMarkup(dto.getTemplateMarkup());
		domain.setSectionType(pageSectionTypeAssembler.dtoToDomain(dto.getSectionTypeDto()));
		return domain;
	}

	@Override
	public Collection<PageSectionDto> domainsToDtos(final Set<PageSection> domains){
		Collection <PageSectionDto> dtos = new ArrayList<PageSectionDto>();
		
		Iterator<PageSection> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return dtos;
	}

	@Override
	public Set<PageSection> dtosToDomains (final Collection<PageSectionDto> dtos){
		Set <PageSection> domains = new HashSet<PageSection>();
		
		Iterator<PageSectionDto> iterator = dtos.iterator();
		while (iterator.hasNext()){
			domains.add(dtoToDomain(iterator.next()));
		}
		return domains;
	}
	
}
