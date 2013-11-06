package com.contento3.security;


import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

public class SiteUserJdbcDaoImpl extends CustomJdbcDaoImpl {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;
	
	public void changePassword(final String username,final String password,final Integer siteId) {
		Validate.notNull(username,"username cannot be null");
		Validate.notNull(password,"password cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		
		final UserDetails user = loadUserByUsername(username);
		final String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		getJdbcTemplate().update("UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ? and SITE_ID = ?",
				encodedPassword, username, siteId);
	}

}
