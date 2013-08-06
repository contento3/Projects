package com.contento3.security.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;

@Entity
@Table( name="USER" , schema ="PLATFORM_USERS" )
public class SaltedHibernateUser {

	/**
	 * Primary key of user table
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="USERNAME")
	private String userName;

	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="SALT")
	private String salt;
	
	@Column(name="ENABLED")
	private boolean enabled;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	public String getPassword() {
		return password;
	}


	public void setPassword(final String password) {
		this.password = password;
	}


	public String getSalt() {
		return salt;
	}


	public void setSalt(final String salt) {
		this.salt = salt;
	}


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


	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}


	public boolean isEnabled() {
		return enabled;
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

	public Integer getUserId() {
		return userId;
	}

	public void setId(final Integer userId) {
		this.userId = userId;
	}

}
