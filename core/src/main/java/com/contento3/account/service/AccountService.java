package com.contento3.account.service;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.model.Account;

public interface AccountService {
	
	AccountDto findAccountById(Integer accountId);
	
    Account dtoToDomain(final AccountDto dto);

	AccountDto domainToDto(final Account domain);

	Collection<AccountDto> domainsToDtos(final Collection<Account> domains);

	
}
