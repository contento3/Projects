package com.contento3.dam.document.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.document.dao.DocumentTypeDao;
import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.service.DocumentTypeAssembler;
import com.contento3.dam.document.service.DocumentTypeService;

/**
 * @author Syed Muhammad Ali
 * Service implementation for the DocumentType entity.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class DocumentTypeServiceImpl implements DocumentTypeService {
	
	/**
	 * Assembler to assemble document type
	 */
	private DocumentTypeAssembler documentTypeAssembler;
	
	/**
	 * DAO for document type
	 */
	private DocumentTypeDao documentTypeDao;

	/**
	 * Constructor
	 */
	public DocumentTypeServiceImpl(final DocumentTypeAssembler documentTypeAssembler, final DocumentTypeDao documentTypeDao){
		this.documentTypeAssembler = documentTypeAssembler;
		this.documentTypeDao = documentTypeDao;
	}

	/**
	 * Creates a document type using DocumentTypeDto
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Object create(DocumentTypeDto dto)
			throws EntityAlreadyFoundException {
		Integer documentTypePk;
		
		if(documentTypeDao.findByName(dto.getName()) == null)	
			throw new EntityAlreadyFoundException();
			
		documentTypePk = documentTypeDao.persist(documentTypeAssembler.dtoToDomain(dto));
		
		return documentTypePk;
	}

	/**
	 * Deletes a document type using a DocumentTypeDto
	 */
	@Transactional(readOnly = true)
	@Override
	public void delete(DocumentTypeDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Finds a document type dto
	 */
	@Transactional(readOnly = true)
	@Override
	public DocumentTypeDto findByName(String typeName) {
		return documentTypeAssembler.domainToDto( documentTypeDao.findByName(typeName) );
	}

	/**
	 * Finds all document type dtos
	 */
	@Transactional(readOnly = true)
	@Override
	public Collection<DocumentTypeDto> findAllTypes() {
		return documentTypeAssembler.domainsToDtos( documentTypeDao.findAllTypes() );
	}

}
