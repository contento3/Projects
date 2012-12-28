package com.contento3.dam.document.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.dam.document.dto.DocumentDto;

/**
 * @author Syed Muhammad Ali
 * Service for the document entities.
 */

public interface DocumentService extends Service<DocumentDto>{

	/**
	 * Returns the DocumentDto that is associated with the account and has
	 * the required unique identifier
	 * @param accountId and uuid
	 * @return DocumentDto
	 */
	DocumentDto findByUuid(Integer accountId, String uuid);

	/**
	 * Returns the collection of DocumentDtos that are associated with
	 * the given accountId and are of the required document type.
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
	 */
	void update(DocumentDto documentDto);
}
