package com.contento3.account.dto;

import java.io.Serializable;

public class AccountDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountId;

	private String name;
	
	private boolean isEnabled;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	
}
