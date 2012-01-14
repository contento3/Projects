package com.contento3.account.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

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
