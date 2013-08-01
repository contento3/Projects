package com.contento3.security.permission.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.security.permission.dto.PermissionDto;

public interface PermissionService extends Service<PermissionDto>{
	Collection<PermissionDto> findPermissionByEntityId(final Integer entityId);
	PermissionDto findById(Integer id);
	Collection<PermissionDto> findAllPermissions();
	void update(PermissionDto dtoToUpdate);
}
