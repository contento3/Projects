package com.contento3.security.user.dao.impl;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;

public class SaltedHibernateUserDaoImpl extends GenericDaoSpringHibernateTemplate <SaltedHibernateUser,Integer>
implements SaltedHibernateUserDao {

	public SaltedHibernateUserDaoImpl() {
		super(SaltedHibernateUser.class);
	}
}
