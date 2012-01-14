package com.olive.cms.page.section.service;

import java.util.Collection;
import java.util.Set;

import com.olive.cms.page.section.dto.PageSectionTypeDto;
import com.olive.cms.page.section.model.PageSectionType;

public interface PageSectionTypeAssembler {

	public PageSectionTypeDto domainToDto (PageSectionType domain);
	
	public PageSectionType dtoToDomain (PageSectionTypeDto dto);
	
	public Collection<PageSectionTypeDto> domainsToDtos(Set<PageSectionType> domains);
	
	public Collection<PageSectionType> dtosToDomains (Set<PageSectionTypeDto> dtos);

}
