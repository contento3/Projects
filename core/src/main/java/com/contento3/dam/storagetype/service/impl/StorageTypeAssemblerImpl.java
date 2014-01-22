package com.contento3.dam.storagetype.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.model.StorageType;
import com.contento3.dam.storagetype.service.StorageTypeAssembler;

public class StorageTypeAssemblerImpl implements StorageTypeAssembler {

	@Override
	public StorageType dtoToDomain(final StorageTypeDto dto) {
		
		StorageType storageType = new StorageType();
		
		storageType.setName( dto.getName() );
		storageType.setDescription( dto.getDescription() );
		storageType.setStartDate( dto.getStartDate() );
		storageType.setEndDate( dto.getEndDate() );
		storageType.setStorageTypeId( dto.getStorageTypeId() );
		
		return storageType;
	}

	@Override
	public StorageTypeDto domainToDto(final StorageType domain) {
		StorageTypeDto storageTypeDto = new StorageTypeDto();
		
		storageTypeDto.setName( domain.getName() );
		storageTypeDto.setDescription( domain.getDescription() );
		storageTypeDto.setStartDate( domain.getStartDate() );
		storageTypeDto.setEndDate( domain.getEndDate() );
		storageTypeDto.setStorageTypeId( domain.getStorageTypeId() );
		
		return storageTypeDto;
	}

	@Override
	public Collection<StorageTypeDto> domainsToDtos(
			Collection<StorageType> domains) {
		Collection<StorageTypeDto> storageTypeDtos = new ArrayList<StorageTypeDto>();
		
		for(StorageType storageType : domains){
			storageTypeDtos.add(domainToDto(storageType));
		}
		
		return storageTypeDtos;
	}

	@Override
	public Collection<StorageType> dtosToDomains(Collection<StorageTypeDto> dtos) {
		Collection<StorageType> storageTypes = new ArrayList<StorageType>();
		
		for(StorageTypeDto storageTypeDto : dtos){
			storageTypes.add(dtoToDomain(storageTypeDto));
		}
		
		return storageTypes;
	}

}
