package com.contento3.security.permission.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entityoperation.model.EntityOperation;
@Entity
@Table( name="PERMISSION" , schema ="PLATFORM_USERS" )
public class Permission {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PERMISSION_ID")
	private Integer permissionId;
	
	@ManyToOne
	@JoinColumn(name = "ENTITY_ID")
	private PermissionEntity entity;

	@ManyToOne
	@JoinColumn(name = "ENTITY_OPERATION_ID")
	private EntityOperation entityOperation;
	
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
	
	
}
