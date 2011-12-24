package com.olive.account.dao;

import java.util.Collection;

import com.olive.account.model.Account;
import com.olive.common.dao.GenericDao;

/**
 * Data access layer for Account entity.
 * @author HAMMAD
 *
 */
public interface AccountDao extends GenericDao<Account,Integer> {

	Collection<Account> findAccountBySiteId(final Integer siteId);
}
