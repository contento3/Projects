package com.contento3.cms.site.structure.domain.service;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.common.service.Service;

public interface SiteDomainService extends Service<SiteDomainDto> {
	
	/**
	 * Returns the siteDomainDto
	 * @param domainName for a domain
	 * @return 
	 */
	
	SiteDomainDto findSiteDomainByName(String domainName);
	

	void update(SiteDomainDto dto);
	void delete(SiteDomainDto dto);
}
