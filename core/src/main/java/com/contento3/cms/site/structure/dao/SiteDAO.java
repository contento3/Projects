package com.contento3.cms.site.structure.dao;

import java.util.Collection;

import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.dao.GenericDao;

public interface SiteDAO extends GenericDao<Site,Integer> {

	/**
	 * Returns all the site associated to the passed accountId
	 * @param accountId
	 * @return
	 */
	Collection<Site> findByAccount(Integer accountId);

	Site findByDomain(String domain);

}
