package com.contento3.cms.seo.service;

import java.util.Collection;

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
	
	/**
	 * Return Dtos
	 * @param dtos
	 * @return
	 */
	public Collection<MetaTag> dtosToDomains(Collection<MetaTagDto> dtos);
	
	/**
	 * Return domains
	 * @param domains
	 * @return
	 */
	public Collection<MetaTagDto> domainsToDtos(final Collection<MetaTag> domains);

}
