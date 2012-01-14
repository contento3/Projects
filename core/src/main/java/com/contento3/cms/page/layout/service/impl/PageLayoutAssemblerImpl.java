package com.contento3.cms.page.layout.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.page.layout.service.PageLayoutAssembler;
import com.contento3.cms.page.layout.service.PageLayoutTypeAssembler;
import com.contento3.cms.page.section.service.PageSectionAssembler;


public class PageLayoutAssemblerImpl implements PageLayoutAssembler{

	private PageSectionAssembler pageSectionAssembler;
	private PageLayoutTypeAssembler pageLayoutTypeAssembler;
	
	public PageLayoutAssemblerImpl (final PageSectionAssembler pageSectionAssembler,final PageLayoutTypeAssembler pageLayoutTypeAssembler){
		this.pageSectionAssembler = pageSectionAssembler;
		this.pageLayoutTypeAssembler = pageLayoutTypeAssembler;
	}
	
	@Override
	public PageLayout dtoToDomain(final PageLayoutDto dto){
		PageLayout pageLayout = new PageLayout();
		pageLayout.setName(dto.getName());
		pageLayout.setLayoutType(pageLayoutTypeAssembler.dtoToDomain(dto.getLayoutTypeDto()));
		pageLayout.setAccountId(dto.getAccountId());
		pageLayout.setId(dto.getId());
		
	//	if (null != dto.getPageSections()){
		//	pageLayout.setPageSections(pageSectionAssembler.dtosToDomains(dto.getPageSections()));
	//	}
		return pageLayout;
	}

	@Override
	public PageLayout dtoToDomainWithPageSections(final PageLayoutDto dto){
		PageLayout pageLayout = new PageLayout();
		pageLayout.setName(dto.getName());
		pageLayout.setLayoutType(pageLayoutTypeAssembler.dtoToDomain(dto.getLayoutTypeDto()));
		pageLayout.setAccountId(dto.getAccountId());
		pageLayout.setId(dto.getId());
		
		if (null != dto.getPageSections()){
			pageLayout.setPageSections(pageSectionAssembler.dtosToDomains(dto.getPageSections()));
		}
		return pageLayout;
	}

	@Override
	public PageLayoutDto domainToDto(final PageLayout domain){
		PageLayoutDto dto = null;
		
		if (domain!=null){
			dto= new PageLayoutDto();
			dto.setName(domain.getName());
			dto.setLayoutTypeDto(pageLayoutTypeAssembler.domainToDto(domain.getLayoutType()));
			dto.setAccountId(domain.getAccountId());
			dto.setId(domain.getId());
			dto.setPageSections(pageSectionAssembler.domainsToDtos(domain.getPageSections()));
		}
		return dto;
	}

	@Override
	public Collection<PageLayoutDto> domainsToDtos(final Collection<PageLayout> domains){
		Collection<PageLayoutDto> dtos = new ArrayList<PageLayoutDto>();
		for (PageLayout page : domains){
			dtos.add(domainToDto(page));
		}
		return dtos;
	}

	@Override
	public Collection<PageLayout> dtosToDomains(final Collection<PageLayoutDto> dtos){
		Collection<PageLayout> domains = new ArrayList<PageLayout>();
		for (PageLayoutDto dto : dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}


}
