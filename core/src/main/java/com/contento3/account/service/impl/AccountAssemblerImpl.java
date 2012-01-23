package com.contento3.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.model.Account;
import com.contento3.account.service.AccountAssembler;


public class AccountAssemblerImpl implements AccountAssembler {

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

	@Override
	public Collection<Account> dtosToDomains(Collection<AccountDto> dtos) {
		Collection<Account> domains = new ArrayList<Account>();
		for (AccountDto accountDto : dtos){
			domains.add(dtoToDomain(accountDto));
		}
		return domains;
	}

}
