package com.contento3.cms.seo.dao;

import java.util.Collection;

import com.contento3.cms.seo.model.MetaTag;
import com.contento3.cms.seo.model.MetaTagLevelEnum;
import com.contento3.common.dao.GenericDao;

public interface MetaTagDAO extends GenericDao<MetaTag, Integer> {

	/**
	 * Returns Meta tags by siteId
	 * @param siteId
	 * @return
	 */
	public Collection<MetaTag> findBySiteId(Integer siteId);

	Collection<MetaTag> findByAssocaitedId(Integer id, MetaTagLevelEnum level);
}
