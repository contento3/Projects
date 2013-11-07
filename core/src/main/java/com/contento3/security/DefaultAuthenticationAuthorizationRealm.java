package com.contento3.security;

import java.util.ArrayList;
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

import com.contento3.security.entity.dao.EntityDao;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entityoperation.dao.EntityOperationDao;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;
import com.contento3.security.role.model.Role;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.site.user.model.User;

public class DefaultAuthenticationAuthorizationRealm extends AuthorizingRealm {

	private static final Logger LOGGER = Logger.getLogger(DefaultAuthenticationAuthorizationRealm.class);

	private transient SaltedHibernateUserDao saltedHibernateUserDao;
	private transient GroupDao groupDao;
	
	
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
		
		SaltedHibernateUser userr = (SaltedHibernateUser) principals.fromRealm(getName()).iterator().next();
	    Integer userId= userr.getUserId();
		Set<Role>	roles = new HashSet<Role>();
		Set<Permission>	permissions		= new HashSet<Permission>();
		Collection<SaltedHibernateUser> principalsList	= principals.byType(SaltedHibernateUser.class); 
		SaltedHibernateUser user = saltedHibernateUserDao.findById(userId);
		Collection<Group> groups = (Collection<Group>) groupDao.findByUserId(userId);
		
		if( groups != null ) {
		        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		     //   Collection<GroupRole> groupRoles = group.getRoles();
		        
		           // info.addRoles(fetchRoles(groups));
		            info.addStringPermissions( fetchPermissions(groups));
		        
		        return info;
		    }
		return null;
//		try {
//		
//		} 
//		catch (InvalidDataException idEx) { //userManger exceptions
//				throw new AuthorizationException(idEx);
//		} catch (ResourceException rEx) {
//			throw new AuthorizationException(rEx);
//		} 
//from here
		//THIS IS THE MAIN CODE YOU NEED TO DO !!!!
		//SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		//info.setRoles(roles); //fill in roles 
		//info.setObjectPermissions(permissions); //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)
			
		//return info;
	}
	public Collection<String> fetchRoles(Collection<Group> groups)
	{
		Collection<String> RoleNames = new ArrayList();
		
		for(Group group: groups)
		{
		      Collection<Role> groupRoles = group.getRoles();	
		      for(Role role: groupRoles)
				{
					RoleNames.add(role.getRoleName());
				}
		}
		
		return RoleNames;
	}
	public Collection<String> fetchPermissions(Collection<Group> groups)
	{
		Collection<String> permissions = new ArrayList();
		PermissionEntity entity;
		//EntityDao entityDao;
		//EntityOperationDao entityOperationDao;
		EntityOperation entityOperation;
		for(Group group: groups)
		{
		      Collection<Role> groupRoles = group.getRoles();	
		      for(Role role: groupRoles)
				{
					Collection<com.contento3.security.permission.model.Permission> tempPermissions = role.getPermissions();
					for(com.contento3.security.permission.model.Permission permission: tempPermissions)
					{
						entity= permission.getEntity();
						entityOperation = permission.getEntityOperation();
						permissions.add(entity.getEntityName()+":"+entityOperation.getEntityOperationName());
					}
				}
		}
		return permissions;
	}
	
	public void setSaltedHibernateUserDao(final SaltedHibernateUserDao saltedHibernateUserDao){
		this.saltedHibernateUserDao = saltedHibernateUserDao;
	}
	public void setGroupDao(final GroupDao groupDao){
		this.groupDao = groupDao;
	}
	
	@Override
	public void setCredentialsMatcher(final CredentialsMatcher credentialsMatcher){
		super.setCredentialsMatcher(credentialsMatcher);
	}
}

