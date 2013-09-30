package com.contento3.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * Extends the baseline Spring Security JdbcDaoImpl and implements change password functionality.
 * 
 * Used in Chapter 4 example of customizing JdbcDaoImpl.
 * 
 * @author Mularien
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl implements IChangePassword {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	public void changePassword(final String username,final String password) {
		Validate.notNull(username,"username cannot be null");
		Validate.notNull(password,"password cannot be null");
		final UserDetails user = loadUserByUsername(username);
		final String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		getJdbcTemplate().update("UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?",
				encodedPassword, username);
	}

	@Override
	protected UserDetails createUserDetails(final String username,
			final UserDetails userFromUserQuery,
			final List<GrantedAuthority> combinedAuthorities) {
		Validate.notNull(username,"username cannot be null");
		Validate.notNull(userFromUserQuery,"userFromUserQuery cannot be null");
		Validate.notNull(combinedAuthorities,"combinedAuthorities cannot be null");
		
        String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new SaltedUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities, ((SaltedUser) userFromUserQuery).getSalt());
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		Validate.notNull(username,"username cannot be null");
		
       return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                String salt = rs.getString(4);
                return new SaltedUser(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, salt);
            }
        });
	}
	
	
}
