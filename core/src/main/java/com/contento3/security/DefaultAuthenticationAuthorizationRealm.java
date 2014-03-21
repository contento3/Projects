package com.contento3.security;

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

import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;

public class DefaultAuthenticationAuthorizationRealm extends AuthorizingRealm {

	private static final Logger LOGGER = Logger.getLogger(DefaultAuthenticationAuthorizationRealm.class);

	private transient SaltedHibernateUserService saltedHibernateUserService;
	
	private transient GroupService groupService;
	
	/**
	 * This is called when the users logs in.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		SaltedHibernateUserDto user;
		try {
			user = saltedHibernateUserService.findUserByUsername(upToken.getUsername());
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
		
		final SaltedHibernateUserDto userr = (SaltedHibernateUserDto) principals.fromRealm(getName()).iterator().next();
	    final Integer userId= userr.getId();
		final Collection<GroupDto> groups = (Collection<GroupDto>) groupService.findByUserId(userId);
		
		if(groups != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(fetchPermissions(groups));
			return info;
		}
		return null;
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
	
	private Collection<String> fetchPermissions(final Collection<GroupDto> groups)
	{
		Collection<String> permissions = new ArrayList<String>();
		EntityDto entity;

		EntityOperationDto entityOperation;
		for(GroupDto group: groups)
		{
		      final Collection<RoleDto> groupRoles = group.getRoles();	
		      for(RoleDto role: groupRoles)
				{
					final Collection<PermissionDto> tempPermissions = role.getPermissions();
					for(PermissionDto permission: tempPermissions)
					{
						entity= permission.getEntity();
						entityOperation = permission.getEntityOperation();
						permissions.add(entity.getName()+":"+entityOperation.getName());
					}
				}
		}
		return permissions;
	}
	
	public void setSaltedHibernateUserService(final SaltedHibernateUserService saltedHibernateUserService){
		this.saltedHibernateUserService = saltedHibernateUserService;
	}
	
	public void setGroupService(final GroupService groupService){
		this.groupService = groupService;
	}
	
	@Override
	public void setCredentialsMatcher(final CredentialsMatcher credentialsMatcher){
		super.setCredentialsMatcher(credentialsMatcher);
	}
}

