package com.contento3.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.dto.AccountDto;
import com.contento3.account.model.Account;
import com.contento3.account.service.AccountService;

public class AccountServiceImpl implements AccountService {
	
	private AccountDao accountDao;
	
	AccountServiceImpl(final AccountDao accountDao){
		this.accountDao = accountDao;
	}

	@Override
	public AccountDto findAccountById(Integer accountId){
		return domainToDto(accountDao.findById(accountId));
	}

	@Override
	public Account dtoToDomain(final AccountDto dto){
		Account account = new Account();
		account.setAccountId(dto.getAccountId());
		account.setName(dto.getName());
		return account;
	}

	@Override
	public AccountDto domainToDto(final Account domain){
		AccountDto dto = new AccountDto();
		dto.setName(dto.getName());
		dto.setAccountId(domain.getAccountId());
		return dto;
	}

	@Override
	public Collection<AccountDto> domainsToDtos(final Collection<Account> domains){
		Collection<AccountDto> dtos = new ArrayList<AccountDto>();
		for (Account account : domains){
			dtos.add(domainToDto(account));
		}
		return dtos;
	}

}
