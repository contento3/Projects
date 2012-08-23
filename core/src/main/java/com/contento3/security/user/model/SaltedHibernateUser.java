package com.contento3.security.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="USERS" )
public class SaltedHibernateUser {

	/**
	 * Primary key of users table
	 */
	@Id
	@Column(name="USERNAME")
	private String userName;
	

	public String getUserName() {
		return userName;
	}


	public void setUserName(final String userName) {
		this.userName = userName;
	}


}
