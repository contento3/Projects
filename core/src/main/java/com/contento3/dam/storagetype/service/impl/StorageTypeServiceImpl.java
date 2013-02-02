package com.contento3.dam.storagetype.service.impl;

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
		this.storageTypeAssembler = storageTypeAssembler;
		this.storageTypeDao = storageTypeDao;
	}
	
	@Override
	public Object create(final StorageTypeDto storageTypeDto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		Integer storageTypePk;
		
		if(storageTypeDao.findByName(storageTypeDto.getName()) == null)	
			throw new EntityAlreadyFoundException();
			
		storageTypePk = storageTypeDao.persist(storageTypeAssembler.dtoToDomain(storageTypeDto));
		
		return storageTypePk;
	}

	@Override
	public void delete(final StorageTypeDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object findByName(final String name) {
		return storageTypeAssembler.domainToDto( (StorageType) storageTypeDao.findByName(name) );
	}

}
