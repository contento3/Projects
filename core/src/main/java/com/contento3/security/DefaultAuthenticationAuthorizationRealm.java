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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.model.Permission;
import com.contento3.security.permission.service.PermissionAssembler;
import com.contento3.security.role.model.Role;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DefaultAuthenticationAuthorizationRealm extends AuthorizingRealm {

	private static final Logger LOGGER = Logger.getLogger(DefaultAuthenticationAuthorizationRealm.class);

	private transient SaltedHibernateUserDao saltedHibernateUserDao;
	
	private transient GroupDao groupDao;
	
	private transient PermissionAssembler permissionAssembler;
	
	private transient SaltedHibernateUserAssembler userAssembler;
	
	/**
	 * This is called when the users logs in.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		SaltedHibernateUser user;
		SaltedHibernateUserDto userDto;
		try {
			user = saltedHibernateUserDao.findByUsername(upToken.getUsername());
			userDto = userAssembler.domainToDto(user);
		} catch (Exception idEx) {
				throw new AuthenticationException(idEx);
		}
			
		if (userDto == null) {
			throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
		}
		
		LOGGER.info("Found user with username [{"+ upToken.getUsername()+"}]");
		return new SimpleAuthenticationInfo(userDto, userDto.getPassword().toCharArray(), SimpleByteSource.Util.bytes(userDto.getSalt()), getName());
	}


	/**
	this function loads user authorization data from "userManager" data source (database)
	User, Role are custom POJOs (beans) and are loaded from database.  
	WildcardPermission implements shiros Permission interface, so my permissions in database gets accepted by shiro security
	**/
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		
		final SaltedHibernateUserDto userr = (SaltedHibernateUserDto) principals.fromRealm(getName()).iterator().next();
	    final Integer userId= userr.getId();
		final Collection<Group> groups = (Collection<Group>) groupDao.findByUserId(userId);
		
		if(groups != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(fetchPermissions(groups));
			return info;
		}
		return null;
	}

	private Collection<String> fetchPermissions(final Collection<Group> groups)
	{
		Collection<String> permissions = new ArrayList<String>();
		EntityDto entity;

		EntityOperationDto entityOperation;
		for(Group group: groups)
		{
		      final Collection<Role> groupRoles = group.getRoles();	
		      for(Role role: groupRoles)
				{
					final Collection<Permission> tempPermissions = role.getPermissions();
					final Collection<PermissionDto> tempPermissionsDtos = permissionAssembler.domainsToDtos(tempPermissions);
					for(PermissionDto permission: tempPermissionsDtos)
					{
						entity= permission.getEntity();
						entityOperation = permission.getEntityOperation();
						permissions.add(entity.getName()+":"+entityOperation.getName());
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
	
	public void setPermissionAssembler (final PermissionAssembler permissionAssembler){
		this.permissionAssembler = permissionAssembler;
	}

	public void setUserAssembler (final SaltedHibernateUserAssembler userAssembler){
		this.userAssembler = userAssembler;
	}
	

	@Override
	public void setCredentialsMatcher(final CredentialsMatcher credentialsMatcher){
		super.setCredentialsMatcher(credentialsMatcher);
	}
}

