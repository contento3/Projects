package com.contento3.dam.storagetype.service;

import java.util.Collection;

import com.contento3.common.service.StorableService;
import com.contento3.dam.storagetype.dto.StorageTypeDto;
/**
 * @author Syed Muhammad Ali
 * Storage Type Service
 */

public interface StorageTypeService extends StorableService<StorageTypeDto>{
	/**
	 * Returns the StorageTypeDto that has the
	 * required name
	 * @param Name of storage type
	 * @return StorageTypeDto
	 */
	StorageTypeDto findByName(String name);
	
	/**
	 * Finds all the storage type in the app
	 * @param name
	 * @return Collection
	 */
	Collection <StorageTypeDto> findAll();
}
