package com.contento3.security.role.dao;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.contento3.security.model.Permission;
import com.contento3.security.role.model.Role;

public class RolePermissionLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * PERMISSIOM_ID used in composite pk generation
	 */
	@ManyToOne
	@JoinColumn(name="PERMISSION_ID")
	private Permission permission;
	
	/**
	 * ROLE_ID used in composite pk generation
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Role role;


	public Permission getPermission() {
		return permission;
	}

	public void setPermission(final Permission permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

}
