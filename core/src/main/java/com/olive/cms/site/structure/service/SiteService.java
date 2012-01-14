package com.olive.cms.site.structure.service;

import java.util.Collection;

import com.olive.cms.site.structure.dto.SiteDto;
import com.olive.cms.site.structure.model.Site;
import com.olive.common.service.Service;

public interface SiteService extends Service {
	
	/**
	 * Used to create a site for a given account.
	 * If there is already a site with the name within the same account
	 * then it will not create a site.
	 */
	void create(SiteDto site);

	/**
	 * 
	 * Finds all the sites for a given account
	 * @return
	 */
	Collection<SiteDto> findSiteByAccountId(Integer accountId);

	/**
	 * Finds the site by site id
	 * @return
	 */
	SiteDto findSiteById(Integer siteId);

	Collection<SiteDto> domainsToDtos(Collection<Site> domains);
	
	SiteDto domainToDto(Site domain);

	Site dtoToDomain(SiteDto dto);

	SiteDto findSiteByDomain(String domain);

}
