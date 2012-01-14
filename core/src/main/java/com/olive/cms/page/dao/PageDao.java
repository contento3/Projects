package com.olive.cms.page.dao;

import java.util.Collection;

import com.olive.cms.page.model.Page;
import com.olive.common.dao.GenericDao;


public interface PageDao extends GenericDao<Page,Integer> {

	/**
	 * Returns the collection of pages for a given site
	 * @param siteId id for a site
	 * @return
	 */
	Collection <Page> findPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize);

	/**
	 * Returns the collection of pages for a given site
	 * @param siteId id for a site
	 * @return
	 */
	Collection <Page> findPageBySiteId(Integer siteId);

	/**
	 * Returns a page for a path and siteId provided.
	 * @param path
	 * @param siteId
	 * @return
	 */
	Page findPageByPathAndSiteId(String path,Integer siteId);

	/**
	 * Used to return the number of pages for a site.
	 * @param siteId
	 * @return
	 */
	Long findTotalPagesForSite(Integer siteId);

}
