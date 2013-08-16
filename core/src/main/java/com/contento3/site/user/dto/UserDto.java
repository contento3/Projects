package com.contento3.site.user.dto;

public class UserDto {

	/**
	 * Id that identifies a site
	 */
	private String siteId;
	
	/**
	 * username of a user
	 */
	private String username;
	
	/**
	 * password of a user
	 */
	private String password;
	
	/**
	 * passwordReminder
	 */
	private String passwordReminder;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPasswordReminder() {
		return passwordReminder;
	}

	public void setPasswordReminder(final String passwordReminder) {
		this.passwordReminder = passwordReminder;
	}

	public void setSiteId(final String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}
	
}
