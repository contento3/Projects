package com.contento3.security.role.dto;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;
import com.contento3.security.model.Permission;
import com.contento3.security.permission.dto.*;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public class RoleDto extends Dto{
	
	private String roleName;
	

	private String roledesc;
	private Integer roleId;

	/**
	 * Permissions associated to roles
	 */
	private Collection<PermissionDto> permissions;
	
	public Collection<PermissionDto> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<PermissionDto> permissions) {
		this.permissions = permissions;
	}



	

	
	/**
	 * Account associated to user
	 */
	private AccountDto account;
	
	@Override
	public String getName() {
		return roleName;
	}
	@Override
	public Integer getId() {
		return roleId;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	public void setRoleId(final int roleId) {
		this.roleId = roleId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	

	public final AccountDto getAccount() {
		return account;
	}

	public final void setAccount(final AccountDto account) {
		this.account = account;
	}





	public String getRoleName() {
		return roleName;
	}
	
	
	public String getRoleDesc() {
		return roledesc;
	}

	public void setRoleDesc(final String desc) {
		this.roledesc = desc;
	}

}
