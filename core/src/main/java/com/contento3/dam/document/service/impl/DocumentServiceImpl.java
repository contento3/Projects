package com.contento3.dam.document.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.service.DocumentAssembler;
import com.contento3.dam.document.service.DocumentService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class DocumentServiceImpl implements DocumentService {
	
	private DocumentAssembler documentAssembler;
	private DocumentDao documentDao;
	
	public DocumentServiceImpl(final DocumentAssembler documentAssembler, final DocumentDao documentDao){
		Validate.notNull(documentAssembler,"documentAssembler cannot be null");
		Validate.notNull(documentDao,"documentDao cannot be null");
		this.documentAssembler = documentAssembler;
		this.documentDao = documentDao;
	}
	@RequiresPermissions("DOCUMENT:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(DocumentDto documentDto) throws EntityAlreadyFoundException {
		Validate.notNull(documentDto,"documentDto cannot be null");
		Integer documentPk;
		
		if(documentDao.findByTitle(documentDto.getDocumentTitle()) != null)
			throw new EntityAlreadyFoundException();
		
		documentPk = documentDao.persist(documentAssembler.dtoToDomain(documentDto));
		
		return documentPk;
	}
	@RequiresPermissions("DOCUMENT:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(DocumentDto documentDto) throws EntityAlreadyFoundException {
		Validate.notNull(documentDto,"documentDto cannot be null");
		Document document = documentDao.findByTitle(documentDto.getDocumentTitle());
		
		/**
		 * If a document with the same title exist, and it does not have the same
		 * docId as the documentDto then it must be a duplication.
		 */
		if( (document != null) && (document.getDocumentId() != documentDto.getDocumentId()) )	
			throw new EntityAlreadyFoundException();
		
		documentDao.update(documentAssembler.dtoToDomain(documentDto, document));
	}
	@RequiresPermissions("DOCUMENT:DELETE")
	@Override
	public void delete(DocumentDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		documentDao.delete(documentAssembler.dtoToDomain(dtoToDelete));
	}
	
	//removed , propagation = Propagation.REQUIRES_NEW from annotations for JUnit
	@RequiresPermissions("DOCUMENT:VIEW")
	@Transactional(readOnly = true)
	@Override
	public DocumentDto findByUuid(Integer accountId, String uuid) {
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(uuid,"uuid cannot be null");
		return documentAssembler.domainToDto(documentDao.findByUuid(accountId, uuid));
	}
	@RequiresPermissions("DOCUMENT:VIEW")
	@Transactional(readOnly = true)
	@Override
	public Collection<DocumentDto> findByType(Integer accountId, String type) {
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(type,"type cannot be null");
		return documentAssembler.domainsToDtos(documentDao.findByType(accountId, type));
	}
	@RequiresPermissions("DOCUMENT:VIEW")
	@Transactional(readOnly = true)
	@Override
	public Collection<DocumentDto> findByAccountId(Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return documentAssembler.domainsToDtos(documentDao.findByAccountId(accountId));
	}
	@RequiresPermissions("DOCUMENT:VIEW")
	@Transactional(readOnly = true)
	@Override
	public DocumentDto findById(Integer documentId) {
		Validate.notNull(documentId,"documentId cannot be null");
		return documentAssembler.domainToDto(documentDao.findById(documentId));
	}
}
