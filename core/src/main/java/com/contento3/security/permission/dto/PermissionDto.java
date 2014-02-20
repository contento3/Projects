package com.contento3.security.permission.dto;

import org.apache.shiro.authz.Permission;

import com.contento3.common.dto.Dto;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entityoperation.dto.EntityOperationDto;

public class PermissionDto extends Dto implements org.apache.shiro.authz.Permission {
	private Integer permissionId;
	private EntityDto entity;
	private EntityOperationDto entityOperation;
	
	@Override
	public Integer getId()
	{
		return permissionId;
	}
	
	@Override
	public String getName()
	{
		return (entity.getName()+":"+entityOperation.getName());
	}
	
	public EntityDto getEntity()
	{
		return entity;
	}
	public EntityOperationDto getEntityOperation()
	{
		return entityOperation;
	}
	
	public void setPermissionId(Integer permissionId)
	{
		this.permissionId= permissionId;
	}
	public void setEntity(EntityDto entity)
	{
		this.entity = entity;
	}
	public void setEntityOperation(EntityOperationDto entityOperation)
	{
		this.entityOperation = entityOperation ;
	}

	@Override
	public boolean implies(Permission arg0) {
		return false;
	}
}
 