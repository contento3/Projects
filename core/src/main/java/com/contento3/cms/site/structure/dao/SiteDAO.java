package com.contento3.cms.site.structure.dao;

import java.util.Collection;

import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.dao.GenericDao;

public interface SiteDAO extends GenericDao<Site,Integer> {

	/**
	 * Returns all the site associated to the passed accountId and by their status
	 * @param accountId
	 * @param isPublished 
	 * @return
	 */
	Collection<Site> findByAccount(Integer accountId, boolean isPublished);

	/**
	 * return site which domain match and has specified status
	 * @param domain
	 * @param isPublished 
	 * @return
	 */
	Site findByDomain(String domain, boolean isPublished);
	


}
