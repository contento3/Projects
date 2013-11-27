package com.contento3.security.entity.service.impl;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.entity.dao.EntityDao;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entity.model.PermissionEntity;
import com.contento3.security.entity.service.EntityAssembler;
import com.contento3.security.entity.service.EntityService;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.model.Permission;

public class EntityServiceImpl implements EntityService{
	private EntityDao entityDao;
	private EntityAssembler entityAssembler;
	EntityServiceImpl(EntityDao entityDao,EntityAssembler entityAssembler)
	{
		this.entityDao=entityDao;
		this.entityAssembler=entityAssembler;
	}

	@Override
	public Object create(EntityDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		// TODO Auto-generated method stub
		final PermissionEntity entity = new PermissionEntity();
		Integer EntityId=entityDao.persist(entity);
		return EntityId;
	}

	@Override
	public void delete(EntityDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		entityDao.delete(entityAssembler.dtoToDomain(dtoToDelete));
	}

	@Override
	public EntityDto findById(Integer id) {
		// TODO Auto-generated method stub
		return entityAssembler.domainToDto(entityDao.findById(id));
	}

	@Override
	public Collection<EntityDto> findAllEntities() {
		// TODO Auto-generated method stub
		return entityAssembler.domainsToDtos(entityDao.findAllEntities());
	}

	@Override
	public void update(EntityDto dtoToUpdate) {
		entityDao.update( entityAssembler.dtoToDomain(dtoToUpdate) );
		// TODO Auto-generated method stub
		
	}

}