package com.contento3.cms.page.layout.dao;

import com.contento3.cms.page.layout.model.PageLayoutType;
import com.contento3.common.dao.GenericDao;

public interface PageLayoutTypeDAO extends GenericDao<PageLayoutType,Integer> {

	/**
	 * Finds the layout type by name
	 * @param name
	 * @return
	 */
	PageLayoutType findByName(String name);

}
