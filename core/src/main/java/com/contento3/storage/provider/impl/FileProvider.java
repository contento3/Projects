package com.contento3.storage.provider.impl;

import com.contento3.dam.content.storage.S3Storage;
import com.contento3.storage.provider.StorageProvider;
import com.contento3.storage.provider.StorageProviderContext;

public class FileProvider implements StorageProvider<S3Storage> {

	@Override
	public Boolean store(final StorageProviderContext context) {
		return true;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public S3Storage fetchConfig(final Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
