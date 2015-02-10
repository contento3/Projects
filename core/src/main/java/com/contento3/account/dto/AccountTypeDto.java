package com.contento3.account.dto;

import java.util.Collection;

public class AccountTypeDto {

	private Integer accountTypeId;
	
	private String accountTypeName;
	
	private String description;
	
	/**
	 * Modules for this account.
	 */
	private Collection<ModuleDto> modules;

	public Integer getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<ModuleDto> getModules() {
		return modules;
	}

	public void setModules(Collection<ModuleDto> modules) {
		this.modules = modules;
	}

}
