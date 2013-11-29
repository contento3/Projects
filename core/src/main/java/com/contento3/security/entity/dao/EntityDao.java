package com.contento3.security.entity.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.entity.model.PermissionEntity;


public interface EntityDao extends GenericDao<PermissionEntity, Integer> {
	Collection<PermissionEntity> findEntitiesByEntityId(final Integer entityId);
	PermissionEntity findByEntityName(String entityName);
	Collection<PermissionEntity> findAllEntities();

}
