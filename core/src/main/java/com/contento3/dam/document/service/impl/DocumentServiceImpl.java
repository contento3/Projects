package com.contento3.dam.document.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentAssembler;
import com.contento3.dam.document.service.DocumentService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class DocumentServiceImpl implements DocumentService {
	
	private DocumentAssembler documentAssembler;
	private DocumentDao documentDao;
	
	public DocumentServiceImpl(final DocumentAssembler documentAssembler, final DocumentDao documentDao){
		this.documentAssembler = documentAssembler;
		this.documentDao = documentDao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(DocumentDto documentDto) throws EntityAlreadyFoundException {
		Integer documentPk;
		
		if(documentDao.findByTitle(documentDto.getDocumentTitle()) != null)
			throw new EntityAlreadyFoundException();
		
		documentPk = documentDao.persist(documentAssembler.dtoToDomain(documentDto));
		
		return documentPk;
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(DocumentDto documentDto) throws EntityAlreadyFoundException {
		if(documentDao.findByTitle(documentDto.getDocumentTitle()) == null)	
			throw new EntityAlreadyFoundException();
		
		documentDao.update(documentAssembler.dtoToDomain(documentDto));
	}
	
	@Override
	public void delete(DocumentDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		documentDao.delete(documentAssembler.dtoToDomain(dtoToDelete));
	}
	
	//removed , propagation = Propagation.REQUIRES_NEW from annotations for JUnit
	@Transactional(readOnly = true)
	@Override
	public DocumentDto findByUuid(Integer accountId, String uuid) {
		return documentAssembler.domainToDto(documentDao.findByUuid(accountId, uuid));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<DocumentDto> findByType(Integer accountId, String type) {
		return documentAssembler.domainsToDtos(documentDao.findByType(accountId, type));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<DocumentDto> findByAccountId(Integer accountId) {
		return documentAssembler.domainsToDtos(documentDao.findByAccountId(accountId));
	}

	@Transactional(readOnly = true)
	@Override
	public DocumentDto findById(Integer documentId) {
		return documentAssembler.domainToDto(documentDao.findById(documentId));
	}
}
