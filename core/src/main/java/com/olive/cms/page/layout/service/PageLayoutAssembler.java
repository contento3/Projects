package com.olive.cms.page.layout.service;

import java.util.Collection;

import com.olive.cms.page.layout.dto.PageLayoutDto;
import com.olive.cms.page.layout.model.PageLayout;
/**
 * PageLayoutAssemlber that converts page layout dto to domain objects
 * @author HAMMAD
 *
 */
public interface PageLayoutAssembler {

	PageLayout dtoToDomain(final PageLayoutDto dto);
	
	PageLayoutDto domainToDto(final PageLayout domain);
	
	Collection<PageLayoutDto> domainsToDtos(final Collection<PageLayout> domains);

	Collection<PageLayout> dtosToDomains(final Collection<PageLayoutDto> dtos);
	
	PageLayout dtoToDomainWithPageSections(final PageLayoutDto dto);

}
