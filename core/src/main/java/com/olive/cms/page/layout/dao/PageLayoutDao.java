package com.olive.cms.page.layout.dao;

import java.util.Collection;

import com.olive.cms.page.layout.model.PageLayout;
import com.olive.common.dao.GenericDao;

public interface PageLayoutDao extends GenericDao<PageLayout,Integer> {

	/**
	 * Returns a collection of PageLayout for the account
	 * @return Collection of PageLayout
	 */
	Collection <PageLayout> findPageLayoutByAccount(final Integer accountId);
}
