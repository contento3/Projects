package com.contento3.security.role.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.contento3.security.group.model.Group;
import com.contento3.security.model.Permission;
@Embeddable
public class RolePermissionLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Permission used in composite pk generation
	 */
	@ManyToOne
	@JoinColumn(name="PERMISSION_ID")
	private Permission permission;
	
	/**
	 * Role used in composite pk generation
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
