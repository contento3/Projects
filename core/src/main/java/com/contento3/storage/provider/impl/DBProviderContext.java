package com.contento3.storage.provider.impl;

import com.contento3.storage.provider.StorageProviderContext;

public class DBProviderContext extends StorageProviderContext {

	private String contentDaoType;

	public String getContentDaoType() {
		return contentDaoType;
	}

	public void setContentDaoType(final String contentDaoType) {
		this.contentDaoType = contentDaoType;
	}
	
}
