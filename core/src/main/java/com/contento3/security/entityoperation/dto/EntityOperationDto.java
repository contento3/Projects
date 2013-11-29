package com.contento3.security.entityoperation.dto;

import com.contento3.common.dto.Dto;

public class EntityOperationDto extends Dto{
	private String entityOperationName;
	private Integer entityOperationId;
	@Override
	public String getName() {
		return entityOperationName;
	}
	public void setEntityName(final String entityOperationName) {
		this.entityOperationName = entityOperationName;
	}
	public Integer getId() {
		return entityOperationId;
	}
	public void setEntityOperationId(final Integer entityOperationId) {
		this.entityOperationId = entityOperationId;
	}

}
