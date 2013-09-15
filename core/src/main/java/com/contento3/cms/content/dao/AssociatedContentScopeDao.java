package com.contento3.cms.content.dao;

import java.util.Collection;

import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.common.dao.GenericDao;

public interface AssociatedContentScopeDao extends GenericDao<AssociatedContentScope, Integer> {

	/**
	 * Returns the associtedcontentscope for the provided 
	 * type for e.g. scopes for an IMAGE.
	 * @param scopeType
	 * @return Collection of AssociatedContentScope
	 */
	Collection<AssociatedContentScope> findAssociatedContentScopeByType(
			AssociatedContentScopeTypeEnum scopeType);

}
