package com.contento3.common.service;

import com.contento3.common.exception.EntityCannotBeDeletedException;

public interface Service<T> {

	/**
	 * Deletes an entity
	 * @param dtoToDelete
	 * @return
	 * @throws EntityCannotBeDeletedException 
	 */
	void delete(T dtoToDelete) throws EntityCannotBeDeletedException;

}
