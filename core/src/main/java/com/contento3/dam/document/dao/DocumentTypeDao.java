package com.contento3.dam.document.dao;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.document.model.DocumentType;

public interface DocumentTypeDao extends GenericDao<DocumentType, Integer> {
	DocumentType findByName(String typeName);
}
