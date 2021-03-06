package com.contento3.cms.site.structure.domain.service;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.common.service.StorableService;

public interface SiteDomainService extends StorableService<SiteDomainDto> {
	
	/**
	 * Returns the siteDomainDto
	 * @param domainName for a domain
	 * @return 
	 */
	
	SiteDomainDto findSiteDomainByName(String domainName);
	
	/**
	 * Updates SiteDomain
	 * @param dto
	 */
	void update(SiteDomainDto dto);

	/**
	 * Deletes SiteDomain
	 * @param dto
	 */
	void delete(SiteDomainDto dto);
	
	/**
	 * Finds SiteDomain by id.
	 * @param siteDomainId
	 * @return
	 */
	SiteDomainDto findById(Integer siteDomainId);
}
