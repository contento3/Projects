package com.contento3.cms.page.service;

import java.util.Collection;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.model.Page;
import com.contento3.common.service.Service;

public interface PageService {

	/**
	 * 
	 * @param siteId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	//TODO change it to findPageBySiteId
	Collection <PageDto> getPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize);

	/**
	 * 
	 * @param siteId
	 * @return
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
