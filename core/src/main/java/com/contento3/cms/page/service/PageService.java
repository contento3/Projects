package com.contento3.cms.page.service;

import java.util.Collection;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.common.exception.EntityAlreadyFoundException;

/**
 * Serves as a service layer for page.
 * @author HAMMAD
 *
 */
public interface PageService {

	/**
	 * Returns the {@link Collection} of {@link PageDto} by siteId
	 * @param siteId
	 * @return {@link Collection} of PageDto
	 */
	 PageDto findById(Integer pageId)  throws PageNotFoundException;

	/**
	 * Returns the {@link Collection} of {@link PageDto} by siteId
	 * @param siteId
	 * @return {@link Collection} of PageDto
	 */
	 Collection <PageDto> findPageBySiteId(Integer siteId);
	 /**
	  * return page 
	  * @param siteId
	  * @param pageId
	  * @return
	  */
	 PageDto findPageBySiteId(final Integer siteId,final Integer pageId) ;
	 
	 
    /**
     * Finds Number of pages for a site
     * @param siteId
     * @return
     */
    Long findTotalPagesForSite(Integer siteId);
    
    /**
     * 
     * @param pageDto
     * @return
     */
    Integer create(final PageDto pageDto);

    /**
     * Finds the Page with its layout info by id 
     * @param pageId
     * @return PageDto
     */
    PageDto findPageWithLayout(final Integer pageId)  throws PageNotFoundException;

    /**
     * Creates a new page and an returns the PageDto
     * @param pageDto
     * @return PageDto
     * @throws EntityAlreadyFoundException 
     */
    PageDto createAndReturn(final PageDto pageDto) throws EntityAlreadyFoundException  ;

    /**
     * Finds the page by path and siteId
     * @param path
     * @param siteId
     * @return PageDto
     */
	PageDto findByPathForSite(String path, Integer siteId)  throws PageNotFoundException;

    /**
     * Used to update the page.
     * @param pageId
     * @throws EntityAlreadyFoundException 
     */
    void update(final PageDto pageDto) throws EntityAlreadyFoundException;

}
