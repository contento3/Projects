package com.olive.cms.page.layout.dao;

import com.olive.cms.page.layout.model.PageLayoutType;
import com.olive.common.dao.GenericDao;

public interface PageLayoutTypeDAO extends GenericDao<PageLayoutType,Integer> {

	/**
	 * Finds the layout type by name
	 * @param name
	 * @return
	 */
	PageLayoutType findByName(String name);

}
