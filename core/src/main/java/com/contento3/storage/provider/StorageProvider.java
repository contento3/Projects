package com.contento3.storage.provider;


public interface StorageProvider<T> {

	public Boolean store(StorageProviderContext context);
	
	public void remove();

	public T fetchConfig(Integer accountId);
}
