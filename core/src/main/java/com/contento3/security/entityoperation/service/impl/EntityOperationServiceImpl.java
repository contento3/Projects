package com.contento3.security.entityoperation.service.impl;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.entity.dao.EntityDao;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entity.service.EntityAssembler;
import com.contento3.security.entity.service.EntityService;
import com.contento3.security.entityoperation.dao.EntityOperationDao;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.entityoperation.model.EntityOperation;
import com.contento3.security.entityoperation.service.EntityOperationAssembler;
import com.contento3.security.entityoperation.service.EntityOperationService;

public class EntityOperationServiceImpl implements EntityOperationService{

	private EntityOperationDao entityOperationDao;
	private EntityOperationAssembler entityOperationAssembler;
	EntityOperationServiceImpl(EntityOperationDao entityOperationDao,EntityOperationAssembler entityOperationAssembler)
	{
		this.entityOperationDao=entityOperationDao;
		this.entityOperationAssembler=entityOperationAssembler;
	}
	@Override
	public Object create(EntityOperationDto dto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		// TODO Auto-generated method stub
		final EntityOperation entityOperation = new EntityOperation();
		Integer EntityOperationId=entityOperationDao.persist(entityOperation);
		return EntityOperationId;
	}

	@Override
	public void delete(EntityOperationDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		entityOperationDao.delete(entityOperationAssembler.dtoToDomain(dtoToDelete));
	}

	@Override
	public EntityOperationDto findById(Integer id) {
		// TODO Auto-generated method stub
		return entityOperationAssembler.domainToDto(entityOperationDao.findById(id));
	}

	@Override
	public Collection<EntityOperationDto> findAllEntityOperations() {
		// TODO Auto-generated method stub
		return entityOperationAssembler.domainsToDtos(entityOperationDao.findAllEntityOperations());
	}

	@Override
	public void update(EntityOperationDto dtoToUpdate) {
		// TODO Auto-generated method stub
		entityOperationDao.update( entityOperationAssembler.dtoToDomain(dtoToUpdate) );
	}

}
