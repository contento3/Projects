package com.contento3.common.security;

public class PasswordInfo {

	public String password;

	public String salt;

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

}
