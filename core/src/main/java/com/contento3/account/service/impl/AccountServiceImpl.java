package com.contento3.account.service.impl;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountAssembler;
import com.contento3.account.service.AccountService;

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
	
	AccountServiceImpl(final AccountDao accountDao,final AccountAssembler assembler){
		this.accountDao = accountDao;
		this.assembler = assembler;
	}

	@Override
	public AccountDto findAccountById(Integer accountId){
		return assembler.domainToDto(accountDao.findById(accountId));
	}


}
