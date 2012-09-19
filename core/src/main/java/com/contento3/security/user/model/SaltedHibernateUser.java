package com.contento3.security.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;

@Entity
@Table( name="USERS" , schema ="PLATFORM_USERS" )
public class SaltedHibernateUser {

	/**
	 * Primary key of users table
	 */
	@Id
	@Column(name="USERNAME")
	private String userName;
	
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	

	public Account getAccount() {
		return account;
	}


	public void setAccount(final Account account) {
		this.account = account;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(final String userName) {
		this.userName = userName;
	}


}
