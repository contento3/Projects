package com.contento3.account.service.impl;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.dto.AccountTypeDto;
import com.contento3.account.model.Account;
import com.contento3.account.model.AccountType;
import com.contento3.account.service.AccountTypeAssembler;
import com.contento3.account.service.ModuleAssembler;

public class AccountTypeAssemblerImpl implements AccountTypeAssembler {
	
	private ModuleAssembler moduleAssembler;
	
	public AccountTypeAssemblerImpl (final ModuleAssembler moduleAssembler){
		this.moduleAssembler = moduleAssembler;
	}
	
	@Override
	public AccountType dtoToDomain(final AccountTypeDto dto,final AccountType domain) {
		domain.setAccountTypeId(dto.getAccountTypeId());
		domain.setAccountTypeName(dto.getAccountTypeName());
		domain.setDescription(dto.getDescription());
		domain.setModules(moduleAssembler.dtosToDomains(dto.getModules()));
		return domain;
	}

	@Override
	public AccountTypeDto domainToDto(AccountType domain, AccountTypeDto dto) {
		dto.setAccountTypeId(domain.getAccountTypeId());
		dto.setAccountTypeName(domain.getAccountTypeName());
		dto.setDescription(domain.getDescription());
		dto.setModules(moduleAssembler.domainsToDtos(domain.getModules()));
		return dto;
	}

	@Override
	public Collection<AccountTypeDto> domainsToDtos(
			Collection<AccountType> domains) {
		return null;
	}

	@Override
	public Collection<AccountType> dtosToDomains(Collection<AccountTypeDto> dtos) {
		return null;
	}

	
}
