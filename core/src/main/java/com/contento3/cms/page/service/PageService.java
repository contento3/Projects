package com.contento3.cms.page.service;

import java.util.Collection;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.model.Page;

/**
 * Serves as a service layer for page.
 * @author HAMMAD
 *
 */
public interface PageService {

	/**
	 * Returns the {@link Collection} of PageDto by siteId
	 * @param siteId unique id for {@link Site} class
	 * @param pageNumber  
	 * @param pageSize
	 * @return {@link Collection} of PageDto
	 */
	//TODO change it to findPageBySiteId
	Collection <PageDto> getPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize);

	/**
	 * Returns the {@link Collection} of PageDto by siteId
	 * @param siteId
	 * @return {@link Collection} of PageDto
	 */
	//TODO change it to findPageBySiteId
	Collection <PageDto> getPageBySiteId(Integer siteId);

	/**
	 * 
	 * @param domains
	 * @return
	 */
	Collection<PageDto> domainsToDtos(final Collection<Page> domains);

	/**
	 * 
	 * @param domain
	 * @return
	 */
	PageDto domainToDto(final Page domain);

	/**
	 * 
	 * @param dto
	 * @return
	 */
    Page dtoToDomain(final PageDto dto);
	
    /**
     * 
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
     * 
     * @param pageId
     * @return
     */
    PageDto findPageWithLayout(final Integer pageId)  throws PageNotFoundException;

    /**
     * 
     * @param pageDto
     * @return
     */
    PageDto createAndReturn(final PageDto pageDto)  ;

    /**
     * 
     * @param path
     * @param siteId
     * @return
     */
	PageDto findByPathForSite(String path, Integer siteId)  throws PageNotFoundException;

}
