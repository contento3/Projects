package com.contento3.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Represents a permission for a particular Role.This 
 * is the combination of Entity and EntiyOperation. 
 * @author hammad.afridi
 *
 */
@Entity
@Table(name = "PERMISSION")
public class Permission implements org.apache.shiro.authz.Permission{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERMISSION_ID", length = 11, nullable = false, updatable = false)
	private Integer permissionId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Entity entity;
	
	@OneToOne(cascade = CascadeType.ALL)
	private SecuredEntityOperation entityOperation;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(final Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(final Entity entity) {
		this.entity = entity;
	}

	public SecuredEntityOperation getEntityOperation() {
		return entityOperation;
	}

	public void setEntityOperation(final SecuredEntityOperation entityOperation) {
		this.entityOperation = entityOperation;
	}

	@Override
	public boolean implies(org.apache.shiro.authz.Permission arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
