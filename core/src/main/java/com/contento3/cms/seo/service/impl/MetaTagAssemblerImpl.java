package com.contento3.cms.seo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.model.MetaTag;
import com.contento3.cms.seo.service.MetaTagAssembler;

public class MetaTagAssemblerImpl implements MetaTagAssembler {

	public MetaTag dtoToDomain(final MetaTagDto dto) {
		
		MetaTag domain = new MetaTag();
		domain.setMetaTagId(dto.getMetaTagId());
		domain.setAttribute(dto.getAttribute());
		domain.setAttributeValue(dto.getAttributeValue());
		domain.setAttributeContent(dto.getAttributeContent());
		domain.setLevel(dto.getLevel());
		domain.setAssociatedId(dto.getAssociatedId());
		domain.setSite(dto.getSite());
		return domain;
	}
	
	public MetaTagDto domainToDto(final MetaTag domain) {
		
		MetaTagDto dto = new MetaTagDto();
		dto.setMetaTagId(domain.getMetaTagId());
		dto.setAttribute(domain.getAttribute());
		dto.setAttributeValue(domain.getAttributeValue());
		dto.setAttributeContent(domain.getAttributeContent());
		dto.setLevel(domain.getLevel());
		dto.setAssociatedId(domain.getAssociatedId());
		dto.setSite(domain.getSite());
		return dto;
	}
	
	public Collection<MetaTagDto> domainsToDtos(final Collection<MetaTag> domains){
		
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
