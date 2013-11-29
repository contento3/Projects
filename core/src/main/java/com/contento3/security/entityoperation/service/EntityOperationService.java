package com.contento3.security.entityoperation.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entityoperation.dto.EntityOperationDto;

public interface EntityOperationService extends Service<EntityOperationDto>{

	EntityOperationDto findById(Integer id);
	Collection<EntityOperationDto> findAllEntityOperations();
	void update(EntityOperationDto dtoToUpdate);
}
