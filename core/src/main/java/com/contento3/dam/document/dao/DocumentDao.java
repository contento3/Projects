package com.contento3.dam.document.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.document.model.Document;

/**
 * Data access layer for Document entity.
 * @author Syed Muhammad Ali
 *
 */

public interface DocumentDao extends GenericDao<Document, Integer>{
	Document findByUuid(Integer accountId, String uuid);
	Collection<Document> findByType(Integer accountId, String type);
	Collection<Document> findByAccountId(Integer accountId);
}
