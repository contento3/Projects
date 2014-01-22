package com.contento3.dam.storagetype.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.storagetype.dao.StorageTypeDao;
import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.model.StorageType;
import com.contento3.dam.storagetype.service.StorageTypeAssembler;
import com.contento3.dam.storagetype.service.StorageTypeService;

public class StorageTypeServiceImpl implements StorageTypeService {
	
	private StorageTypeAssembler storageTypeAssembler;
	private StorageTypeDao storageTypeDao;
	
	public StorageTypeServiceImpl(final StorageTypeAssembler storageTypeAssembler, final StorageTypeDao storageTypeDao){
		Validate.notNull(storageTypeAssembler,"storageTypeAssembler cannot be null");
		Validate.notNull(storageTypeDao,"storageTypeDao cannot be null");
		
		this.storageTypeAssembler = storageTypeAssembler;
		this.storageTypeDao = storageTypeDao;
	}
	
	@Override
	public Object create(final StorageTypeDto storageTypeDto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		Validate.notNull(storageTypeDto,"storageTypeDto cannot be null");
		Integer storageTypePk;
		
		if(storageTypeDao.findByName(storageTypeDto.getName()) == null)	
			throw new EntityAlreadyFoundException();
			
		storageTypePk = storageTypeDao.persist(storageTypeAssembler.dtoToDomain(storageTypeDto));
		
		return storageTypePk;
	}

	@Override
	public void delete(final StorageTypeDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		// TODO Auto-generated method stub
		
	}

	@Override
	public StorageTypeDto findByName(final String name) {
		return storageTypeAssembler.domainToDto( (StorageType) storageTypeDao.findByName(name) );
	}

	@Override
	public Collection <StorageTypeDto> findAll() {
		return storageTypeAssembler.domainsToDtos(storageTypeDao.findAll() );
	}

}
