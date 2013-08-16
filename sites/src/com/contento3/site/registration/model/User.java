package com.contento3.site.registration.model;

public class User {

	private String username;
	
	private String password;
	
	private String passwordReminder;
	
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPasswordReminder(String passwordReminder) {
		this.passwordReminder = passwordReminder;
	}

	public String getPasswordReminder() {
		return passwordReminder;
	}
	
	
}
