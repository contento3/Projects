package com.contento3.security.permission.model;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.role.model.Role;
@Entity
@Table( name="PERMISSION" , schema ="PLATFORM_USERS" )
public class Permission implements org.apache.shiro.authz.Permission{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PERMISSION_ID")
	private Integer permissionId;
	
	@ManyToOne
	@JoinColumn(name = "ENTITY_ID")
	private PermissionEntity entity;

	@ManyToOne
	@JoinColumn(name = "ENTITY_OPERATION_ID")
	private EntityOperation entityOperation;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name= "ROLE_PERMISSION",
	joinColumns={
			@JoinColumn(name="ROLE_ID",unique= true)},
	inverseJoinColumns={
			@JoinColumn(name="PERMISSION_ID",unique= true)})
	private Collection<Role> roles;
	
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> Roles) {
		this.roles = Roles;
	}
	
	public void SetPermissionId(Integer permissionid)
	{
		
		this.permissionId=permissionid;
	}
	public void SetEntity(PermissionEntity entity)
	{
		
		this.entity=entity;
	}
	public void SetEntityOperation(EntityOperation entityoperation)
	{
		
		this.entityOperation=entityoperation;
	}
	public Integer getPermissionId()
	{
		return(permissionId);
	}
	public PermissionEntity getEntity()
	{
		return(entity);
		
	}
	public EntityOperation getEntityOperation()
	{
		return(entityOperation);
		
	}
	@Override
	public boolean implies(org.apache.shiro.authz.Permission arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
