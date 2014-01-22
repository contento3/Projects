package com.contento3.storage.provider;

import java.io.File;

import com.contento3.dam.content.storage.Storable;

public class StorageProviderContext {

	private Integer accountId;
	
	private File file;

	private String contentDaoType;

	private Storable storable;
	
	public String getContentDaoType() {
		return contentDaoType;
	}

	public void setContentDaoType(final String contentDaoType) {
		this.contentDaoType = contentDaoType;
	}
	
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public Storable getStorable() {
		return storable;
	}

	public void setStorable(Storable storable) {
		this.storable = storable;
	}
	
	
}
