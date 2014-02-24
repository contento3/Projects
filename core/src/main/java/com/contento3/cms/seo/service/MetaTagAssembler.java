package com.contento3.cms.seo.service;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.model.MetaTag;

public interface MetaTagAssembler {

	/**
	 * Dto to Domain
	 * @param dto
	 * @return
	 */
	public MetaTag dtoToDomain(final MetaTagDto dto);
	
	/**
	 * Domain to Dto
	 * @param domain
	 * @return
	 */
	public MetaTagDto domainToDto(final MetaTag domain);
}
