package com.contento3.cms.content.service;

import java.util.Collection;

import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.common.service.Service;

public interface AssociatedContentScopeService extends Service<AssociatedContentScopeDto> {

	/**
	 * Gets all the cotent scope for particular cotnent
	 * @return
	 */
	Collection<AssociatedContentScopeDto> getContentScopeForType(AssociatedContentScopeTypeEnum scopeType);
	
	/**
	 * Find content scope by id
	 * @param contentScopeId
	 * @return
	 */
	AssociatedContentScopeDto findById(Integer contentScopeId);
}
