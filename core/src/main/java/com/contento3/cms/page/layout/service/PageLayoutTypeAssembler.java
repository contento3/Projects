package com.contento3.cms.page.layout.service;

import java.util.Collection;

import com.contento3.cms.page.layout.dto.PageLayoutTypeDto;
import com.contento3.cms.page.layout.model.PageLayoutType;

public interface PageLayoutTypeAssembler {

	PageLayoutType dtoToDomain(PageLayoutTypeDto dto);
	
	PageLayoutTypeDto domainToDto(PageLayoutType domain);
	
	Collection<PageLayoutTypeDto> domainsToDtos(Collection<PageLayoutType> domains);

	Collection<PageLayoutType> dtosToDomains(Collection<PageLayoutTypeDto> dtos);

}
