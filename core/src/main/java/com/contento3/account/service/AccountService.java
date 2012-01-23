package com.contento3.account.service;

import com.contento3.account.dto.AccountDto;

public interface AccountService {
	
	AccountDto findAccountById(Integer accountId);
	
}
