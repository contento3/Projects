package com.contento3.storage.manager;

import com.contento3.dam.content.storage.Storable;
import com.contento3.dam.content.storage.Storage;

public interface StorageManager {

	/**
	 * Adds the Storable object to a storage
	 * based on the library it uses.The method uses the 
	 * {@link com.contento3.storage.provider.StorageProviderFactory} 
	 * factory class to get the appropriate provider 
	 * for the Storable object.
	 * @param storable
	 */
	Boolean addToStorage(Storable storable);

	Storage fetchContentStorage(String storageType,Integer accountId);
	
}
