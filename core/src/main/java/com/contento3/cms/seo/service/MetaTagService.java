package com.contento3.cms.seo.service;

import com.contento3.cms.seo.dto.MetaTagDto;

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

}
