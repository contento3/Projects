package com.contento3.dam.document.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import com.contento3.account.service.AccountAssembler;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.service.DocumentAssembler;
import com.contento3.dam.document.service.DocumentTypeAssembler;
import com.contento3.dam.storagetype.service.StorageTypeAssembler;

public class DocumentAssemblerImpl implements DocumentAssembler {
	@Resource(name="accountAssembler")
	AccountAssembler accountAssembler;
	
	@Resource(name="documentTypeAssembler")
	DocumentTypeAssembler documentTypeAssembler;
	
	@Resource(name="storageTypeAssembler")
	StorageTypeAssembler storageTypeAssembler;
	
	@Override
	public Document dtoToDomain(final DocumentDto documentDto) {
		Document document = new Document();
		
		document.setAccount( accountAssembler.dtoToDomain(documentDto.getAccount()) );
		document.setDocumentContent(documentDto.getDocumentContent());
		document.setDocumentId(documentDto.getDocumentId());
		document.setDocumentTitle(documentDto.getDocumentTitle());
		document.setDocumentType( documentTypeAssembler.dtoToDomain( documentDto.getDocumentTypeDto() ) );
		document.setDocumentUuid(documentDto.getDocumentUuid());
		document.setStorageType( storageTypeAssembler.dtoToDomain( documentDto.getStorageTypeDto()) );
		document.setUrl(documentDto.getUrl());
		
		return document;
	}

	@Override
	public DocumentDto domainToDto(final Document domain) {
		DocumentDto documentDto = new DocumentDto();
		
		documentDto.setAccount( accountAssembler.domainToDto(domain.getAccount()) );
		documentDto.setDocumentContent(domain.getDocumentContent());
		documentDto.setDocumentId(domain.getDocumentId());
		documentDto.setDocumentTitle(domain.getDocumentTitle());
		documentDto.setDocumentTypeDto( documentTypeAssembler.domainToDto(domain.getDocumentType()) );
		documentDto.setDocumentUuid(domain.getDocumentUuid());
		documentDto.setStorageTypeDto( storageTypeAssembler.domainToDto(domain.getStorageType()) );
		documentDto.setUrl(domain.getUrl());
		
		return documentDto;
	}

	@Override
	public Collection<DocumentDto> domainsToDtos(final Collection<Document> domains) {
		Collection<DocumentDto> documentDtos = new ArrayList<DocumentDto>();
		
		for(Document document : domains){
			documentDtos.add(domainToDto(document));
		}
		
		return documentDtos;
	}

	@Override
	public Collection<Document> dtosToDomains(final Collection<DocumentDto> dtos) {
		Collection<Document> documents = new ArrayList<Document>();
		
		for(DocumentDto documentDto : dtos){
			documents.add(dtoToDomain(documentDto));
		}
		
		return documents;
	}

}
