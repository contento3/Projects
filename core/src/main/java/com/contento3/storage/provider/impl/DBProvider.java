package com.contento3.storage.provider.impl;

import java.util.Map;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.content.storage.S3Storage;
import com.contento3.dam.content.storage.Storage;
import com.contento3.dam.document.dao.DocumentDao;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.model.Image;
import com.contento3.storage.provider.StorageProvider;
import com.contento3.storage.provider.StorageProviderContext;

public class DBProvider implements StorageProvider<Storage> {

	private Map<String,GenericDao<?,Integer>> contentDaos;
	
	@Override
	public Boolean store(final StorageProviderContext context) {
		final GenericDao<?,Integer> genericDao = fetchContentDao(context.getContentDaoType());
		Boolean isSuccessful = true;
		Integer pk = null;
		if (null==context.getStorable()){
			throw new IllegalStateException("Storable object not found in DBProvider class");
		}
		else {
			if (genericDao instanceof ImageDao){
				final ImageDao imageDao = (ImageDao)genericDao;
				Image image = (Image)context.getStorable();
				pk = imageDao.persist(image);
			}
			else if (genericDao instanceof DocumentDao){
				final DocumentDao documentDao = (DocumentDao)genericDao;
				pk = documentDao.persist((Document)context.getStorable());
			}	
		}
		
		if (null==pk){
			isSuccessful = false;
		}
		
		return isSuccessful;
	}

	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public S3Storage fetchConfig(final Integer accountId) {
		//This is not going to implemented as the db doesnt have 
		//any configuration like S3 rather the object is stored 
		//in the db in the appropriate table. 
		return null;
	}

	public void setContentDaos(final Map<String,GenericDao<?,Integer>> contentDaos){
		this.contentDaos = contentDaos;
	}
	
	private GenericDao<?,Integer> fetchContentDao(final String contentType){
		return contentDaos.get(contentType);
	}
}
