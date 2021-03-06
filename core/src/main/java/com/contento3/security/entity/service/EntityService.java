package com.contento3.security.entity.service;

import java.util.Collection;

import com.contento3.common.service.StorableService;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.permission.dto.PermissionDto;

public interface EntityService extends StorableService<EntityDto>{
	EntityDto findById(Integer id);
	Collection<EntityDto> findAllEntities();
	void update(EntityDto dtoToUpdate);

}
