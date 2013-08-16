package com.contento3.cms.site.structure.domain.dao;


import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.common.dao.GenericDao;

public interface SiteDomainDao  extends GenericDao<SiteDomain,Integer>{


	/**
	 * Returns the siteDomain
	 * @param domainName for a domain
	 * @return 
	 */
	
	SiteDomain findSiteDomainByName(String domainName);
	

	
}
