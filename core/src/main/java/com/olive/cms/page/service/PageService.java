package com.olive.cms.page.service;

import java.util.Collection;

import com.olive.cms.page.dto.PageDto;
import com.olive.cms.page.model.Page;
import com.olive.common.service.Service;

public interface PageService {

	/**
	 * 
	 * @param siteId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Collection <PageDto> getPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize);

	/**
	 * 
	 * @param siteId
	 * @return
	 */
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
    PageDto findPageWithLayout(final Integer pageId);

    /**
     * 
     * @param pageDto
     * @return
     */
    PageDto createAndReturn(final PageDto pageDto);

}
