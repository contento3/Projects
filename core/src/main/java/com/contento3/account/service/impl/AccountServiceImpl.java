package com.contento3.account.service.impl;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountAssembler;
import com.contento3.account.service.AccountService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class AccountServiceImpl implements AccountService {
	
	/**
	 * Data access layer for account
	 */
	private AccountDao accountDao;
	
	/**
	 * Assembler for changing Account to 
	 * AccountDto and vice versa.
	 */
	private AccountAssembler assembler;
	
	public AccountServiceImpl(final AccountDao accountDao,final AccountAssembler assembler){
		Validate.notNull(accountDao,"accountDao cannot be null");
		Validate.notNull(assembler,"assembler cannot be null");
		
		this.accountDao = accountDao;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("ACCOUNT:VIEW")
	@Override
	public AccountDto findAccountById(Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		return assembler.domainToDto(accountDao.findById(accountId));
	}


}
