package com.contento3.security.user.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;

/**
 * Data access layer for CMS User
 * @author HAMMAD
 *
 */
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

	@SuppressWarnings("unchecked")
	@Override
	public SaltedHibernateUser findByUsername(final String username) {
		Criteria criteria = this.getSession()
				.createCriteria(SaltedHibernateUser.class)
				.add(Restrictions
				.eq("userName", username));
		SaltedHibernateUser user = null;
		final List <SaltedHibernateUser> userList = criteria.list();
		
		if (!CollectionUtils.isEmpty(userList)){
			user = userList.get(0);
		}
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SaltedHibernateUser findUserByAccountId(final Integer accountId,final String username) {
		final Criteria criteria = this.getSession()
				.createCriteria(SaltedHibernateUser.class)
				.add(Restrictions.eq("account.accountId", accountId))
				.add(Restrictions.eq("userName", username));
		
		SaltedHibernateUser user = null;
		final List <SaltedHibernateUser> userList = criteria.list();
		
		if (!CollectionUtils.isEmpty(userList)){
			user = userList.get(0);
		}
		
		return user;
	}
	
	

}
