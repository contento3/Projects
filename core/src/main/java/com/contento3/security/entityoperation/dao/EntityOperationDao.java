package com.contento3.security.entityoperation.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.entityoperation.model.EntityOperation;

public interface EntityOperationDao extends GenericDao<EntityOperation, Integer>{

	Collection<EntityOperation> findEntityOperationsByEntityOperationId(final Integer entityOperationId);
	EntityOperation findByEntityOperationName(String entityOperationName);
	Collection<EntityOperation> findAllEntityOperations();
	
}
