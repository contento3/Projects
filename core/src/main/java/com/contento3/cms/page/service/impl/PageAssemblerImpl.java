package com.contento3.cms.page.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.page.category.service.CategoryAssembler;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.layout.service.PageLayoutAssembler;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.service.PageAssembler;
import com.contento3.cms.site.structure.service.SiteAssembler;

public class PageAssemblerImpl implements PageAssembler {

	private SiteAssembler siteAssembler;
	private PageLayoutAssembler pageLayoutAssembler;
	private CategoryAssembler categoryAssembler;

	public PageAssemblerImpl(final SiteAssembler siteAssembler,final PageLayoutAssembler pageLayoutAssembler,
			final CategoryAssembler categoryAssembler){
		this.siteAssembler = siteAssembler;
		this.pageLayoutAssembler = pageLayoutAssembler;
		this.categoryAssembler = categoryAssembler;
	}
		
	public Page dtoToDomain(final PageDto dto){
		Page page = new Page();
		page.setPageId(dto.getPageId());
		page.setUri(dto.getUri());
		page.setTitle(dto.getTitle());
		page.setSite(siteAssembler.dtoToDomain(dto.getSite()));
		
		if(dto.getCategories() != null)
			page.setCategories(categoryAssembler.dtosToDomains(dto.getCategories()));
		else 
			page.setCategories(null);
		
		if (null!=dto.getPageLayoutDto()){
			page.setPageLayout(pageLayoutAssembler.dtoToDomainWithPageSections(dto.getPageLayoutDto()));
		}
		return page;
	}

	public Page dtoToDomain(final PageDto dto,final Page domain){
		domain.setPageId(dto.getPageId());
		domain.setUri(dto.getUri());
		domain.setTitle(dto.getTitle());
		domain.setSite(siteAssembler.dtoToDomain(dto.getSite()));
		
		if(dto.getCategories() != null)
			domain.setCategories(categoryAssembler.dtosToDomains(dto.getCategories()));
		else
			domain.setCategories(null);
		
		if (null!=dto.getPageLayoutDto()){
			domain.setPageLayout(pageLayoutAssembler.dtoToDomainWithPageSections(dto.getPageLayoutDto()));
		}
		return domain;
	}

	public PageDto domainToDto(final Page domain){
		PageDto dto = new PageDto();
		dto.setPageId(domain.getPageId());
		dto.setUri(domain.getUri());
		dto.setTitle(domain.getTitle());
		dto.setSite(siteAssembler.domainToDto(domain.getSite()));
		
		if(domain.getCategories()!=null)
			dto.setCategories(categoryAssembler.domainsToDtos(domain.getCategories()));
		else 
			dto.setCategories(null);
		dto.setPageLayoutDto(pageLayoutAssembler.domainToDto(domain.getPageLayout()));
		return dto;
	}

	public Collection<PageDto> domainsToDtos(final Collection<Page> domains){
		Collection<PageDto> dtos = new ArrayList<PageDto>();
		for (Page page : domains){
			dtos.add(domainToDto(page));
		}
		return dtos;
	}

	@Override
	public Collection<Page> dtosToDomains(Collection<PageDto> dtos) {
		Collection<Page> domains = new ArrayList<Page>();
		for (PageDto page : dtos){
			domains.add(dtoToDomain(page));
		}
		return domains;
	}

}
