package com.contento3.dam.storagetype.service;

import com.contento3.common.service.Service;

import com.contento3.dam.storagetype.dto.StorageTypeDto;
/**
 * @author Syed Muhammad Ali
 * Storage Type Service
 */

public interface StorageTypeService extends Service<StorageTypeDto>{
	/**
	 * Returns the StorageTypeDto that has the
	 * required name
	 * @param Name of storage type
	 * @return StorageTypeDto
	 */
	Object findByName(String name);
}
