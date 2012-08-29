package com.contento3.security.user.dto;

import com.contento3.common.dto.Dto;

public class SaltedHibernateUserDto extends Dto {

	/**
	 * Primary key of users table
	 */
	private String userName;
	

	public String getUserName() {
		return userName;
	}


	public void setUserName(final String userName) {
		this.userName = userName;
	}
}
