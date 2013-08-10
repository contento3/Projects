package com.contento3.security.role.dto;
import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;

public class RoleDto extends Dto{
	
	private String rolename;
	

	private String roledesc;
	private Integer roleid;
	
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
