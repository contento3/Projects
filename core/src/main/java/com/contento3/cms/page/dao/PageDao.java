package com.contento3.cms.page.dao;

import java.util.Collection;

import com.contento3.cms.page.model.Page;
import com.contento3.common.dao.GenericDao;

/**
 * 
 * @author Yawar
 *
 */
public interface PageDao extends GenericDao<Page,Integer> {

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

	/**
	 * Returns the page for a given page title
	 * @param siteId id for a site
	 * @return
	 */
	Page findPageByTitleAndSiteId(String title, Integer siteId);

	/**
	 * Returns the Collection of Pages with isNavigation equal true for a given site
	 * @param siteId id for a site
	 * @return
	 */
	Collection<Page> findNavigablePages(Integer siteId);
	
	/**
	 * Returns the Collection of Pages with isNavigation equal true for a given site
	 * @param siteId id for a site
	 * @return
	 */
	Collection<Page> findPagesByCategory(Collection<Integer> categoryId, Integer accountId, Integer siteId);
}
