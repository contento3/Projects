package com.contento3.security.role.dto;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;
import com.contento3.security.group.model.GroupAuthority;
import com.contento3.security.role.model.RolePermission;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public class RoleDto extends Dto{
	
	private String rolename;
	

	private String roledesc;
	private Integer roleid;
	/**
	 * Permissions associated to role
	 */
	private Collection<RolePermission> permissions;
	
	/**
	 * Members associated to role
	 */
	private Collection<SaltedHibernateUserDto> members;
	
	/**
	 * Return role related permission
	 * @return
	 */
	public Collection<RolePermission> getpermissions() {
		return permissions;
	}

	/**
	 * Sets role permission
	 * @param permissions
	 */
	public void setpermissions(final Collection<RolePermission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Return role related users
	 * @return
	 */
	public Collection<SaltedHibernateUserDto> getMembers() {
		return members;
	}

	/**
	 * Sets rp;e members (users)
	 * @param members
	 */
	public void setMembers(final Collection<SaltedHibernateUserDto> members) {
		this.members = members;
	}

	
	/**
	 * Account associated to user
	 */
	private AccountDto account;
	
	@Override
	public String getName() {
		return rolename;
	}

	public void setRoleName(final String roleName) {
		this.rolename = roleName;
	}
	public void setRoleid(final int roleId) {
		this.roleid = roleId;
	}
	public Integer getRoleid() {
		return roleid;
	}

	public final AccountDto getAccount() {
		return account;
	}

	public final void setAccount(final AccountDto account) {
		this.account = account;
	}





	public String getRoleName() {
		return rolename;
	}
	
	public String getRoleDesc() {
		return roledesc;
	}

	public void setRoleDesc(final String desc) {
		this.roledesc = desc;
	}

}
