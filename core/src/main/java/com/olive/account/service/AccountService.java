package com.olive.account.service;

import java.util.Collection;

import com.olive.account.dto.AccountDto;
import com.olive.account.model.Account;

public interface AccountService {
	
	AccountDto findAccountById(Integer accountId);
	
    Account dtoToDomain(final AccountDto dto);

	AccountDto domainToDto(final Account domain);

	Collection<AccountDto> domainsToDtos(final Collection<Account> domains);

	
}
