package com.contento3.security.user.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.user.model.SaltedHibernateUser;

public interface SaltedHibernateUserDao extends GenericDao<SaltedHibernateUser, String>{
	
	/**
	 * Find user by account id
	 * @param accountId
	 * @return
	 */
	Collection<SaltedHibernateUser> findUsersByAccountId(final Integer accountId);

}
