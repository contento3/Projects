package com.contento3.cms.page.dao;

import java.util.Collection;

import com.contento3.cms.page.model.Page;
import com.contento3.common.dao.GenericDao;


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
	 * Returns the page for a given page title
	 * @param siteId id for a site
	 * @return
	 */
	Page findPageByTitle(String title);

	/**
	 * Returns the page for a given page uri
	 * @param siteId id for a site
	 * @return
	 */
	Page findPageByUri(String uri);

	/**
	 * Used to return the number of pages for a site.
	 * @param siteId
	 * @return
	 */
	Long findTotalPagesForSite(Integer siteId);

}
