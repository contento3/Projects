package com.contento3.common.service;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;

public interface Service<T> {

	/**
	 * Creates an entity
	 * @param dto
	 * @return
	 * @throws EntityAlreadyFoundException
	 */
	Object create(T dto) throws EntityAlreadyFoundException,EntityNotCreatedException;

	/**
	 * Deletes an entity
	 * @param dtoToDelete
	 * @return
	 * @throws EntityCannotBeDeletedException 
	 */
	void delete(T dtoToDelete) throws EntityCannotBeDeletedException;

}
