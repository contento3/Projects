package com.olive.cms.page.section.service;

import java.util.Collection;
import java.util.Set;

import com.olive.cms.page.section.dto.PageSectionDto;
import com.olive.cms.page.section.model.PageSection;

public interface PageSectionAssembler {

	public PageSectionDto domainToDto (PageSection domain);

	public PageSection dtoToDomain (PageSectionDto dto);

	public Collection<PageSectionDto> domainsToDtos(Set<PageSection> domains);

	public Set<PageSection> dtosToDomains (Collection<PageSectionDto> dtos);

}
