package com.contento3.dam.document.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;

public interface DocumentService extends Service<DocumentDto>{
	DocumentDto findByUuid(Integer accountId, String uuid);
	
	Collection<DocumentDto> findByType(Integer accountId, String type);
	
	Collection<DocumentDto> findByAccountId(Integer accountId);
}
