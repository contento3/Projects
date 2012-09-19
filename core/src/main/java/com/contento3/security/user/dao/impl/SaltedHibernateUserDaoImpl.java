package com.contento3.security.user.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;

public class SaltedHibernateUserDaoImpl extends GenericDaoSpringHibernateTemplate <SaltedHibernateUser,String>
implements SaltedHibernateUserDao {

	public SaltedHibernateUserDaoImpl() {
		super(SaltedHibernateUser.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<SaltedHibernateUser> findUsersByAccountId(final Integer accountId) {
		Criteria criteria = this.getSession()
				.createCriteria(SaltedHibernateUser.class)
				.add(Restrictions
				.eq("account.accountId", accountId));
		return criteria.list();
	}
	
}
