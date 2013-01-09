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
	/* Finds a document by using it's unique uid */
	Document findByUuid(Integer accountId, String uuid);
	
	/* Finds a Document object its title */
	Document findByTitle(String title);
	
	/* Finds document based on their types */
	Collection<Document> findByType(Integer accountId, String type);
	
	/* Finds the document associated with an account */
	Collection<Document> findByAccountId(Integer accountId);
}
