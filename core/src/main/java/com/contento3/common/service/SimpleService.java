package com.contento3.common.service;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.content.storage.exception.InvalidStorageException;

public interface SimpleService<T> extends Service<T> {

	/**
	 * Creates an entity
	 * @param dto
	 * @return
	 * @throws EntityAlreadyFoundException
	 * @throws InvalidStorageException 
	 */
	Object create(T dto) throws EntityAlreadyFoundException,EntityNotCreatedException;


}
