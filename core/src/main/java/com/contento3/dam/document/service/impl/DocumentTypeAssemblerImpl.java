package com.contento3.dam.document.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.document.service.DocumentTypeAssembler;

public class DocumentTypeAssemblerImpl implements DocumentTypeAssembler{

	@Override
	public DocumentType dtoToDomain(DocumentTypeDto dto) {
		DocumentType documentType = new DocumentType();
		
		documentType.setName( dto.getName() );
		documentType.setDescription( dto.getDescription() );
		documentType.setDocumentTypeId( dto.getDocumentTypeId() );
		
		return documentType;
	}

	@Override
	public DocumentTypeDto domainToDto(DocumentType domain) {
		DocumentTypeDto documentTypeDto = new DocumentTypeDto();
		
		documentTypeDto.setName( domain.getName() );
		documentTypeDto.setDescription( domain.getDescription() );
		documentTypeDto.setDocumentTypeId( domain.getDocumentTypeId() );
		
		return documentTypeDto;
	}

	@Override
	public Collection<DocumentTypeDto> domainsToDtos(
			Collection<DocumentType> domains) {
		Collection<DocumentTypeDto> documentTypeDtos = new ArrayList<DocumentTypeDto>();
		
		for(DocumentType documentType : domains){
			documentTypeDtos.add(domainToDto(documentType));
		}
		
		return documentTypeDtos;
	}

	@Override
	public Collection<DocumentType> dtosToDomains(
			Collection<DocumentTypeDto> dtos) {
		Collection<DocumentType> documentTypes = new ArrayList<DocumentType>();
		
		for(DocumentTypeDto documentTypeDto : dtos){
			documentTypes.add(dtoToDomain(documentTypeDto));
		}
		
		return documentTypes;
	}

}
