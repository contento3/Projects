package com.contento3.cms.site.structure.service;

import java.util.Collection;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.service.Service;

public interface SiteService extends Service<SiteDto> {
	
	/**
	 * Used to create a site for a given account.
	 * If there is already a site with the name within the same account
	 * then it will not create a site.
	 */
	Integer create(SiteDto site);

	/**
	 * 
	 * Finds all the sites for a given account
	 * @return
	 */
	Collection<SiteDto> findSitesByAccountId(Integer accountId);

	/**
	 * Finds the site by site id
	 * @return
	 */
	SiteDto findSiteById(Integer siteId);

	/**
	 * Finds the site by domains
	 * @param domain
	 * @return
	 */
	SiteDto findSiteByDomain(String domain);
	
	/**
	 * Update site data into DB
	 */
	SiteDto update(SiteDto site);

}