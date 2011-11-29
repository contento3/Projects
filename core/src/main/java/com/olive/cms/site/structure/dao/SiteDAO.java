package com.olive.cms.site.structure.dao;

import java.util.Collection;

import com.olive.cms.site.structure.model.Site;
import com.olive.common.dao.GenericDao;

public interface SiteDAO extends GenericDao<Site,Integer> {

	/**
	 * Returns all the site associated to the passed accountId
	 * @param accountId
	 * @return
	 */
	public Collection<Site> findByAccount(Integer accountId);

}
