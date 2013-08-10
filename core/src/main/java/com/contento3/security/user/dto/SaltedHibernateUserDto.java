package com.contento3.security.user.dto;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;

public class SaltedHibernateUserDto extends Dto {

	/**
	 * Primary key of users table
	 */
	private Integer id;
	
	/**
	 * unique username
	 */
	private String username;

	/**
	 * User enabled or not
	 */
	private boolean enabled;

	/**
	 * User's password
	 */
	private String password;

	/**
	 * Salt for user password
	 */
	private String salt;
	
	/**
	 * Account associated to user
	 */
	private AccountDto account;

	/**
	 * FirstName of the user
	 */
	private String firstName;
	
	/**
	 * Last Name of the user
	 */
	private String lastName;
	
	/**
	 * Email of the user
	 */
	private String email;
	

	@Override
	public String getName() {
		return username;
	}

	public void setUserName(final String userName) {
		this.username = userName;
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
		return username;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}
	

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}



}
