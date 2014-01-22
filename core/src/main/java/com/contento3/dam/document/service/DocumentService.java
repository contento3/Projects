package com.contento3.dam.document.service;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.SimpleService;
import com.contento3.dam.document.dto.DocumentDto;

/**
 * @author Syed Muhammad Ali
 * Service for the document entities.
 */

public interface DocumentService extends SimpleService<DocumentDto>{

	/**
	 * Returns the DocumentDto that has the given unique uid, and belongs
	 * to the account with the given accountId
	 * @param accountId and uuid
	 * @return DocumentDto
	 */
	DocumentDto findById(Integer documentId);
	
	/**
	 * Returns the DocumentDto that has the given unique uid, and belongs
	 * to the account with the given accountId
	 * @param accountId and uuid
	 * @return DocumentDto
	 */
	DocumentDto findByUuid(Integer accountId, String uuid);

	/**
	 * Returns a collection of DocumentDtos that belongs to the account with
	 * the given accountId, and who's type matches the given type.
	 * @param accountId and type of Document
	 * @return Collection of DocumentDto
	 */
	Collection<DocumentDto> findByType(Integer accountId, String type);

	/**
	 * Returns the collection of DocumentDtos that are associated with
	 * the given accountId
	 * @param accountId
	 * @return Collection of DocumentDto
	 */
	Collection<DocumentDto> findByAccountId(Integer accountId);

	/**
	 * Returns nothing.
	 * @param documentDto that is to be updated.
	 * @return void
	 * @throws EntityAlreadyFoundException 
	 */
	void update(DocumentDto documentDto) throws EntityAlreadyFoundException;
}
