package com.contento3.dam.document.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.dam.document.dto.DocumentTypeDto;

public interface DocumentTypeService extends Service<DocumentTypeDto>{

	/**
	 * Returns the DocumentTypeDto that has the
	 * required name
	 * @param Name of document type
	 * @return DocumentTypeDto
	 */
	DocumentTypeDto findByName(String typeName);
	
	/**
	 * Returns all the DocumentTypeDtos that are stored
	 * in the database.
	 * @param None
	 * @return Collection of DocumentTypeDtos
	 */
	Collection<DocumentTypeDto> findAllTypes();
}
