package com.contento3.cms.seo.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.model.MetaTag;
import com.contento3.cms.seo.model.MetaTagLevelEnum;
import com.contento3.cms.seo.service.MetaTagAssembler;
import com.contento3.cms.site.structure.service.SiteAssembler;

public class MetaTagAssemblerImpl implements MetaTagAssembler {
	
	final SiteAssembler siteAssembler;
	
	public MetaTagAssemblerImpl(final SiteAssembler siteAssembler) {
		this.siteAssembler =  siteAssembler;
	}

	public MetaTag dtoToDomain(final MetaTagDto dto) {
		
		MetaTag domain = new MetaTag();
		domain.setMetaTagId(dto.getMetaTagId());
		domain.setAttribute(dto.getAttribute());
		domain.setAttributeValue(dto.getAttributeValue());
		domain.setAttributeContent(dto.getAttributeContent());
		domain.setLevel(dto.getLevel().toString());
		domain.setAssociatedId(dto.getAssociatedId());
		domain.setSite(siteAssembler.dtoToDomain(dto.getSite()));
		return domain;
	}
	
	public MetaTagDto domainToDto(final MetaTag domain) {
		
		MetaTagDto dto = new MetaTagDto();
		dto.setMetaTagId(domain.getMetaTagId());
		dto.setAttribute(domain.getAttribute());
		dto.setAttributeValue(domain.getAttributeValue());
		dto.setAttributeContent(domain.getAttributeContent());
		
		if (domain.getLevel().equalsIgnoreCase("page")){
			dto.setLevel(MetaTagLevelEnum.PAGE);
		}
		else {
			dto.setLevel(MetaTagLevelEnum.SITE);
		}
		dto.setAssociatedId(domain.getAssociatedId());
		dto.setSite(siteAssembler.domainToDto(domain.getSite()));
		return dto;
	}
	
	public Collection<MetaTagDto> domainsToDtos(final Collection<MetaTag> domains) {
		
		Collection<MetaTagDto> dtos = new ArrayList<MetaTagDto>();
		for (MetaTag tag : domains){
			dtos.add(domainToDto(tag));
		}
		return dtos;
	}

	public Collection<MetaTag> dtosToDomains(Collection<MetaTagDto> dtos) {
		
		Collection<MetaTag> domains = new ArrayList<MetaTag>();
		for (MetaTagDto tag : dtos){
			domains.add(dtoToDomain(tag));
		}
		return domains;
	}
}
