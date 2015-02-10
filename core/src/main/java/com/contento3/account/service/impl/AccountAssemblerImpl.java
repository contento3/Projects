package com.contento3.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.dto.AccountTypeDto;
import com.contento3.account.model.Account;
import com.contento3.account.model.AccountType;
import com.contento3.account.service.AccountAssembler;
import com.contento3.account.service.AccountTypeAssembler;


public class AccountAssemblerImpl implements AccountAssembler {

	private AccountTypeAssembler accountTypeAssembler;
	
	public AccountAssemblerImpl (final AccountTypeAssembler accountTypeAssembler){
		this.accountTypeAssembler = accountTypeAssembler;
	}
	
	@Override
	public Account dtoToDomain(final AccountDto dto){
		Validate.notNull(dto,"account dto cannot be null");

		Account account = new Account();
		account.setAccountId(dto.getAccountId());
		account.setName(dto.getName());
		account.setEnabled(dto.isEnabled());
		account.setAccountType(accountTypeAssembler.dtoToDomain(dto.getAccountTypeDto(), new AccountType()));
		return account;
	}

	@Override
	public AccountDto domainToDto(final Account domain){
		AccountDto dto = new AccountDto();
		dto.setName(dto.getName());
		dto.setAccountId(domain.getAccountId());
		dto.setEnabled(domain.isEnabled());
		dto.setAccountTypeDto(accountTypeAssembler.domainToDto(domain.getAccountType(), new AccountTypeDto()));
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

	@Override
	public Collection<Account> dtosToDomains(final Collection<AccountDto> dtos) {
		Collection<Account> domains = new ArrayList<Account>();
		for (AccountDto accountDto : dtos){
			domains.add(dtoToDomain(accountDto));
		}
		return domains;
	}

}
