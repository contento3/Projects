package com.contento3.common.service;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

public interface Service<T> {

	/**
	 * Creates an entity
	 * @param dto
	 * @return
	 * @throws EntityAlreadyFoundException
	 */
	Object create(T dto) throws EntityAlreadyFoundException;

	/**
	 * Deletes an entity
	 * @param dtoToDelete
	 * @return
	 * @throws EntityCannotBeDeletedException 
	 */
	void delete(T dtoToDelete) throws EntityCannotBeDeletedException;

}
