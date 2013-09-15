package com.contento3.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;

public class DefaultAuthenticationAuthorizationRealm extends AuthorizingRealm {

	private static final Logger LOGGER = Logger.getLogger(DefaultAuthenticationAuthorizationRealm.class);

	private transient SaltedHibernateUserDao saltedHibernateUserDao;
	
	
	/**
	 * This is called when the users logs in.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		SaltedHibernateUser user;
		try {
			user = saltedHibernateUserDao.findByUsername(upToken.getUsername());
		} catch (Exception idEx) {
				throw new AuthenticationException(idEx);
		}
			
		if (user == null) {
			throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
		}
		
		LOGGER.info("Found user with username [{"+ upToken.getUsername()+"}]");
		return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), SimpleByteSource.Util.bytes(user.getSalt()), getName());
	}


	/**
	this function loads user authorization data from "userManager" data source (database)
	User, Role are custom POJOs (beans) and are loaded from database.  
	WildcardPermission implements shiros Permission interface, so my permissions in database gets accepted by shiro security
	**/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		Set<String>	roles = new HashSet<String>();
		Set<Permission>	permissions		= new HashSet<Permission>();
		Collection<SaltedHibernateUser> principalsList	= principals.byType(SaltedHibernateUser.class);
			
//		try {
//		
//		} 
//		catch (InvalidDataException idEx) { //userManger exceptions
//				throw new AuthorizationException(idEx);
//		} catch (ResourceException rEx) {
//			throw new AuthorizationException(rEx);
//		} 

		//THIS IS THE MAIN CODE YOU NEED TO DO !!!!
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setRoles(roles); //fill in roles 
		info.setObjectPermissions(permissions); //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)
			
		return info;
	}
	
	public void setSaltedHibernateUserDao(final SaltedHibernateUserDao saltedHibernateUserDao){
		this.saltedHibernateUserDao = saltedHibernateUserDao;
	}
	
	@Override
	public void setCredentialsMatcher(final CredentialsMatcher credentialsMatcher){
		super.setCredentialsMatcher(credentialsMatcher);
	}
}

