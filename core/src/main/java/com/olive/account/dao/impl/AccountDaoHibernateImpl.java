package com.olive.account.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.account.dao.AccountDao;
import com.olive.account.model.Account;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class AccountDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Account,Integer> 
								  	 implements AccountDao {

	public AccountDaoHibernateImpl(){
		super(Account.class);
	}
	
	@Override
	public Collection<Account> findAccountBySiteId(final Integer siteId){
		Criteria criteria = this.getSession()
		.createCriteria(Account.class)
		.add(Restrictions
		.eq("siteId", siteId));
		return criteria.list();
	}

}
