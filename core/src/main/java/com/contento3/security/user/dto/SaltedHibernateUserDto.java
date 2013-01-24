package com.contento3.security.user.dto;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;

public class SaltedHibernateUserDto extends Dto {

	/**
	 * Primary key of users table
	 */
	private String userName;
	
	/**
	 * User enabled or not
	 */
	private boolean enabled;
	
	private String password;

	/**
	 * Account associated to user
	 */
	private AccountDto account;
	
	@Override
	public String getName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public final AccountDto getAccount() {
		return account;
	}

	public final void setAccount(final AccountDto account) {
		this.account = account;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

}
