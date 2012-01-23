package com.contento3.account.service;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.model.Account;
import com.contento3.common.assembler.Assembler;

/**
 * Used to convert AccountDto to AccountDomain and 
 * AccountDomain to AccountDto
 * @author HAMMAD
 *
 */
public interface AccountAssembler extends Assembler<AccountDto,Account> {
	
}
