package com.olive.cms.page.layout.service;

import java.util.Collection;

import com.olive.cms.page.layout.dto.PageLayoutDto;
import com.olive.common.service.Service;

public interface PageLayoutService extends Service<PageLayoutDto> {

	/**
	 * Returns a collection of page layouts for an account.
	 * @param accountId
	 * @return
	 */
	Collection<PageLayoutDto> findPageLayoutByAccount(final Integer accountId);
	
	/**
	 * Finds a page layout by id.
	 * @param pageLayoutId
	 * @return
	 */
	PageLayoutDto findPageLayoutById(final Integer pageLayoutId);

	
}
