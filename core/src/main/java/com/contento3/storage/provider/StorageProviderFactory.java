package com.contento3.storage.provider;

import java.util.Map;

import com.contento3.dam.content.storage.Storage;


public class StorageProviderFactory {
	
	private Map <String,StorageProvider<Storage>> storageProviders;

	public StorageProvider<Storage> getInstance(final StorageProviderEnum provider){
		return storageProviders.get(provider.toString());
	}
	
	public void setStorageProviders(final Map <String,StorageProvider<Storage>> prov){
		storageProviders = prov;
	}
}
