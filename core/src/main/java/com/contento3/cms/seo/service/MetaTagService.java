package com.contento3.cms.seo.service;

import java.util.Collection;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.model.MetaTagLevelEnum;

public interface MetaTagService {
	
	/**
	 * Create MetaTag
	 * @param dto
	 * @return
	 */
	public Integer create(final MetaTagDto dto);
	
	
	/**
	 * Update MetaTag
	 * @param metaTagDto
	 */
	public void update(final MetaTagDto metaTagDto);
	
	/**
	 * Delete tag
	 * @param dto
	 */
	public void delete(final MetaTagDto dto);
	
	/**
	 * Returns Meta tags by siteId
	 * @param siteId
	 * @return
	 */
	public Collection<MetaTagDto> findByAssociatedId(Integer assocaitedId,MetaTagLevelEnum level);
	
	/**
	 * Return MetaTag Dto
	 * @param id
	 * @return
	 */
	public MetaTagDto findByID(Integer id);

}
