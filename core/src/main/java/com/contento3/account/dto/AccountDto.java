package com.contento3.account.dto;

import java.io.Serializable;

public class AccountDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountId;

	private String name;
	
	private boolean isEnabled;

	private AccountTypeDto accountTypeDto;
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public AccountTypeDto getAccountTypeDto() {
		return accountTypeDto;
	}
	public void setAccountTypeDto(final AccountTypeDto accountTypeDto) {
		this.accountTypeDto = accountTypeDto;
	}

	
}
