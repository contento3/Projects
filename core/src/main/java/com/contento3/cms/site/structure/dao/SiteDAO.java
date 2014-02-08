package com.contento3.cms.site.structure.dao;

import java.util.Collection;

import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.dao.GenericDao;

public interface SiteDAO extends GenericDao<Site,Integer> {

	/**
	 * Returns all the site associated to the passed accountId
	 * @param accountId
	 * @param isPublished TODO
	 * @return
	 */
	Collection<Site> findByAccount(Integer accountId, boolean isPublished);

	/**
	 * return site which domain match 
	 * @param domain
	 * @param isPublished TODO
	 * @return
	 */
	Site findByDomain(String domain, boolean isPublished);
	


}
