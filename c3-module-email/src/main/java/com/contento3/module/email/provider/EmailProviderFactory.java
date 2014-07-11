package com.contento3.module.email.provider;

import java.util.Map;

public class EmailProviderFactory {

	private Map <String,EmailProvider> storageProviders;

	public Map <String,EmailProvider> getStorageProviders() {
		return storageProviders;
	}

	public void setStorageProviders(final Map <String,EmailProvider> storageProviders) {
		this.storageProviders = storageProviders;
	}

}
