package com.contento3.account.dao;

import java.util.Collection;

import com.contento3.account.model.Account;
import com.contento3.common.dao.GenericDao;

/**
 * Data access layer for Account entity.
 * @author HAMMAD
 *
 */
public interface AccountDao extends GenericDao<Account,Integer> {

	/**
	 * Finds account by siteId
	 * @param siteId Unique Id of site
	 * @return Collection<Account>
	 */
	Collection<Account> findAccountBySiteId(Integer siteId);
}
