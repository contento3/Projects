package com.contento3.dam.document.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.service.DocumentAssembler;

public class DocumentAssemblerImpl implements DocumentAssembler {
	
	public DocumentAssemblerImpl(){
		//nothing for now.
	}
	
	@Override
	public Document dtoToDomain(DocumentDto documentDto) {
		Document document = new Document();
		
		document.setAccount(documentDto.getAccount());
		document.setDocument_content(documentDto.getDocumentContent());
		document.setDocument_id(documentDto.getDocumentId());
		document.setDocument_title(documentDto.getDocumentTitle());
		document.setDocument_type(documentDto.getDocumentType());
		document.setDocument_uuid(documentDto.getDocumentUuid());
		document.setStorage_type(documentDto.getStorageType());
		document.setUrl(documentDto.getUrl());
		
		return document;
	}

	@Override
	public DocumentDto domainToDto(Document domain) {
		DocumentDto documentDto = new DocumentDto();
		
		documentDto.setAccount(domain.getAccount());
		documentDto.setDocumentContent(domain.getDocument_content());
		documentDto.setDocumentId(domain.getDocument_id());
		documentDto.setDocumentTitle(domain.getDocument_title());
		documentDto.setDocumentType(domain.getDocument_type());
		documentDto.setDocumentUuid(domain.getDocument_uuid());
		documentDto.setStorageType(domain.getStorage_type());
		documentDto.setUrl(domain.getUrl());
		
		return documentDto;
	}

	@Override
	public Collection<DocumentDto> domainsToDtos(Collection<Document> domains) {
		Collection<DocumentDto> documentDtos = new ArrayList<DocumentDto>();
		
		for(Document document : domains){
			documentDtos.add(domainToDto(document));
		}
		
		return documentDtos;
	}

	@Override
	public Collection<Document> dtosToDomains(Collection<DocumentDto> dtos) {
		Collection<Document> documents = new ArrayList<Document>();
		
		for(DocumentDto documentDto : dtos){
			documents.add(dtoToDomain(documentDto));
		}
		
		return documents;
	}

}
