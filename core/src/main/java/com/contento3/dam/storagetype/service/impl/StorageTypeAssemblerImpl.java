package com.contento3.dam.storagetype.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.model.StorageType;
import com.contento3.dam.storagetype.service.StorageTypeAssembler;

public class StorageTypeAssemblerImpl implements StorageTypeAssembler {

	@Override
	public StorageType dtoToDomain(StorageTypeDto dto) {
		
		StorageType storageType = new StorageType();
		
		storageType.setName( dto.getName() );
		storageType.setDescription( dto.getDescription() );
		storageType.setStart_date( dto.getStart_date() );
		storageType.setEnd_date( dto.getEnd_date() );
		storageType.setStorage_id( dto.getStorage_id() );
		
		return storageType;
	}

	@Override
	public StorageTypeDto domainToDto(StorageType domain) {
		StorageTypeDto storageTypeDto = new StorageTypeDto();
		
		storageTypeDto.setName( domain.getName() );
		storageTypeDto.setDescription( domain.getDescription() );
		storageTypeDto.setStart_date( domain.getStart_date() );
		storageTypeDto.setEnd_date( domain.getEnd_date() );
		storageTypeDto.setStorage_id( domain.getStorage_id() );
		
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
