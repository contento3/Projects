package com.contento3.site.user.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;



public class UserDto {
	/**
	 * Id that identifies a site
	 */
	private Integer siteId;
	
	/**
	 * username of a user
	 */
	@NotEmpty @Email
	private String username;
	
	/**
	 * password of a user
	 */
	@NotEmpty
	private String password;
	
	/**
	 * passwordReminder
	 */

	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	private String middleName;
	
	@NotEmpty
	private String passwordReminder;

	public String getUsername() {
		return username;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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

	public void setSiteId(final Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}
	
}
