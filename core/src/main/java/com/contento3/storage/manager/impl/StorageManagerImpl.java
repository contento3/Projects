package com.contento3.storage.manager.impl;

import java.io.File;

import com.contento3.dam.content.library.dao.ContentStorageLibraryMappingDao;
import com.contento3.dam.content.library.model.ContentStorageLibraryMapping;
import com.contento3.dam.content.storage.Storable;
import com.contento3.dam.content.storage.Storage;
import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.image.model.Image;
import com.contento3.storage.manager.StorageManager;
import com.contento3.storage.provider.StorageProvider;
import com.contento3.storage.provider.StorageProviderContext;
import com.contento3.storage.provider.StorageProviderEnum;
import com.contento3.storage.provider.StorageProviderFactory;
import com.contento3.storage.provider.impl.DBProvider;

public class StorageManagerImpl implements StorageManager {

	private StorageProviderFactory factory;
	
	private ContentStorageLibraryMappingDao cslmDao;
	
	@Override
	public Boolean addToStorage(final Storable storable){
		final ImageLibrary library = storable.getLibrary();
		final Integer accountId = library.getAccount().getAccountId();
		final ContentStorageLibraryMapping mapping = cslmDao.findByLibraryId(library.getId(), accountId);		
		final StorageProvider<Storage> dbProvider = fetchDbStorageProvider();
		
		final String contentType = mapping.getContentType();
		final String storageType = mapping.getStorageType().getName();
		
		Boolean isSavedSuccessful = false;
		//If the storageType is DB then all the data is stoed in a db row along with the file
		if (storageType.equals(StorageProviderEnum.DATABASE.toString())){
			isSavedSuccessful = dbProvider.store(buildStorageProviderContext(storable.getFile(),accountId,mapping.getContentType(),storable));
		}
		//Otherwise the bytes of file needs to be stored in the storage and other metadata into the db.
		//So first need to store in the storage and then save the row in the db
		//We need to make sure that we dont save the actual bytes into the db now.
		else {
			final StorageProvider<Storage> storageProvider = fetchStorageProvider(storageType);
			Boolean isSavedInStorage = storageProvider.store(buildStorageProviderContext(storable.getFile(),accountId,contentType,storable));
			
			Boolean isSavedInDB = false;
			if (contentType.equals("IMAGE")){
				Image image = (Image)storable;
				image.setImage(null);
				isSavedInDB = dbProvider.store(buildStorageProviderContext(storable.getFile(),accountId,contentType,image));
			}
			
			isSavedSuccessful = isSavedInDB && isSavedInStorage;
		}
		return isSavedSuccessful;
	}

	private StorageProviderContext buildStorageProviderContext(final File file,final Integer accountId,
			final String contentType,final Storable storable){
		final StorageProviderContext context = new StorageProviderContext();
		context.setFile(file);
		context.setAccountId(accountId);
		context.setContentDaoType(contentType);
		context.setStorable(storable);
		return context;
	}
	
	private StorageProvider<Storage> fetchStorageProvider(final String storageType){
		StorageProvider<Storage> provider=null;

		if (storageType.equals(StorageProviderEnum.S3.toString())){
			provider = factory.getInstance(StorageProviderEnum.S3);
		}
		else if (storageType.equals(StorageProviderEnum.DATABASE.toString())){
			provider = factory.getInstance(StorageProviderEnum.DATABASE);
		}
 
		//		TODO For file storage
		//		else if (storageType.equals(StorageProviderEnum.FILE.toString())){
		//			provider = factory.getInstance(StorageProviderEnum.FILE);
		//		}
		
		return provider;
	}

	private StorageProvider<Storage> fetchDbStorageProvider(){
		StorageProvider<Storage> provider=null;
		provider = factory.getInstance(StorageProviderEnum.DATABASE);
		return provider;
	}

	public Storage fetchContentStorage(final String storageType,final Integer accountId){

		final StorageProvider<Storage> provider = fetchStorageProvider(storageType);
		//For DB the storage config will always be NULL
		Storage storage = null;
		if (provider!=null && !(provider instanceof DBProvider)){
			storage = provider.fetchConfig(accountId);
		}
		
		return storage;
	}

	public StorageProviderFactory getFactory() {
		return factory;
	}

	public void setCslmDao(final ContentStorageLibraryMappingDao cslmDao) {
		this.cslmDao = cslmDao;
	}

	public void setFactory(final StorageProviderFactory factory) {
		this.factory = factory;
	}


}
