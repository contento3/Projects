package com.contento3.site.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import com.contento3.security.DefaultAuthenticationAuthorizationRealm;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;
import com.contento3.security.role.model.Role;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;

public class SiteUserAuthorisationAuthenticationRealm extends AuthorizingRealm {

	private static final Logger LOGGER = Logger.getLogger(DefaultAuthenticationAuthorizationRealm.class);

	private transient GroupDao groupDao;
	
	private transient PermissionDao permissionDao;
	
	/**
	 * This is called when the site users logs in.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
//		SaltedHibernateUser user;
//		try {
//			user = saltedHibernateUserDao.findByUsername(upToken.getUsername());
//		} catch (Exception idEx) {
//				throw new AuthenticationException(idEx);
//		}			
//		if (user == null) {
//			throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
//		}
//		
		LOGGER.info("Found user with username [{"+ upToken.getUsername()+"}]");
		return new SimpleAuthenticationInfo("guest123","guest123","authenticationAuthorizationRealm");
	}


	/**
	this function loads user authorization data from "userManager" data source (database)
	User, Role are custom POJOs (beans) and are loaded from database.  
	WildcardPermission implements shiros Permission interface, so my permissions in database gets accepted by shiro security
	**/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
//		final SaltedHibernateUser userr = (SaltedHibernateUser) principals.fromRealm(getName()).iterator().next();
//	    final Integer userId= userr.getUserId();
//		final SaltedHibernateUser user = saltedHibernateUserDao.findById(userId);
//		final Collection<Group> groups = (Collection<Group>) groupDao.findByUserId(userId);
		
		//if(groups != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(fetchPermissions(null));
			return info;
		//}
	//	return null;
	}

	private Collection<String> fetchRoles(final Collection<Group> groups)
	{
		Collection<String> roleNames = new ArrayList<String>();
		for(Group group: groups){
			Collection<Role> groupRoles = group.getRoles();	
		    for(Role role: groupRoles){
		    	roleNames.add(role.getRoleName());
			}
		}
		return roleNames;
	}
	
	private Collection<String> fetchPermissions(final Collection<Group> groups)
	{
		Collection<String> permissions = new ArrayList<String>();
		PermissionEntity entity;

		EntityOperation entityOperation;
//		for(Group group: groups)
//		{
//			final Collection<Role> groupRoles = group.getRoles();	
//		    for(Role role: groupRoles){
		    	final Collection<com.contento3.security.permission.model.Permission> tempPermissions = permissionDao.findAll();
		    	//role.getPermissions();
				for(com.contento3.security.permission.model.Permission permission: tempPermissions){
					entity= permission.getEntity();
					entityOperation = permission.getEntityOperation();
				permissions.add(entity.getEntityName()+":"+entityOperation.getEntityOperationName());
				}
		//	}
	//	}
		return permissions;
	}
	

	public void setPermissionDao(final PermissionDao permissionDao){
		this.permissionDao = permissionDao;
	}
	
//	@Override
//	public boolean supports(AuthenticationToken token){
//		return false;
//	}
//	
	@Override
	public void setCredentialsMatcher(final CredentialsMatcher credentialsMatcher){
		super.setCredentialsMatcher(credentialsMatcher);
	}
}

