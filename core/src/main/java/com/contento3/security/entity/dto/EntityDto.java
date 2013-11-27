package com.contento3.security.entity.dto;

import com.contento3.common.dto.Dto;

public class EntityDto extends Dto{
	private String entityName;
	private Integer entityId;
	@Override
	public String getName() {
		return entityName;
	}
	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}
	public Integer getId() {
		return entityId;
	}
	public void setEntityId(final Integer entityId) {
		this.entityId = entityId;
	}
	
}
