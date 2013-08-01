package com.contento3.security.role.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.contento3.security.group.model.GroupAuthorityLinkPK;

@Entity
@Table(name="ROLE_PERMISSION")
public class RolePermission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Composite primary key for RolePermission
	 */
	@EmbeddedId
	private RolePermissionLink primaryKey;

	public RolePermissionLink getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(final RolePermissionLink primaryKey) {
		this.primaryKey = primaryKey;
	}

}
