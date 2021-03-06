package com.contento3.security;

import org.apache.commons.lang.Validate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;

/**
 * Illustrates an extension to InMemoryDaoImpl which allows password changes.
 * 
 * @author Mularien
 */
public class InMemoryChangePasswordDaoImpl extends InMemoryDaoImpl implements IChangePassword {
	/* (non-Javadoc)
	 * @see com.packtpub.springsecurity.security.IChangePassword#changePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void changePassword(String username, String password) {
		// get the UserDetails
		Validate.notNull(username,"username cannot be null");
		Validate.notNull(password,"password cannot be null");
		
		User userDetails = (User) getUserMap().getUser(username);
		// create a new UserDetails with the new password
		User newUserDetails = new User(userDetails.getUsername(),password,
				userDetails.isEnabled(),userDetails.isAccountNonExpired(),
				userDetails.isCredentialsNonExpired(),
				userDetails.isAccountNonLocked(),userDetails.getAuthorities());
		// add to the map
		getUserMap().addUser(newUserDetails);
	}
}
