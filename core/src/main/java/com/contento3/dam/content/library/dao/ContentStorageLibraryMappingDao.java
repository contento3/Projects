package com.contento3.dam.content.library.dao;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.content.library.model.ContentStorageLibraryMapping;

public interface ContentStorageLibraryMappingDao extends GenericDao<ContentStorageLibraryMapping, Integer> {

	/**
	 * Finds the mapping for a given library,
	 * content type i.e. image,document,video etc 
	 * and for a given accountId
	 * @param libraryName Name of a library
 	 * @param contentType String value of content type
	 * @param accountId Given accountId 
	 */
	ContentStorageLibraryMapping findByLibraryAndContentType(String libraryName,String contentType,Integer accountId);

	/**
	 * Finds the mapping for a given library id,
	 * content type i.e. image,document,video etc 
	 * and for a given accountId
	 * @param libraryId Id of a library
 	 * @param contentType String value of content type
	 * @param accountId Given accountId 
	 */
	ContentStorageLibraryMapping findByLibraryId(Integer libraryId,Integer accountId);
}
