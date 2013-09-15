package com.contento3.dam.document.dto;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;
import com.contento3.dam.storagetype.dto.StorageTypeDto;

public class DocumentDto extends Dto {
	/**
	 * Id for the document
	 */
	private Integer documentId;
	
	/**
	 * Title of the document
	 */
	private String documentTitle;

	/**
	 * Url of the document
	 */
	private String url;

	/**
	 * The storage type of the document
	 */
	private StorageTypeDto storageTypeDto;

	/**
	 * The contents of the document
	 */
	private byte[] documentContent;

	/**
	 * The account associated with the document entitiy.
	 */
	private AccountDto account;

	/**
	 * The type of the document.
	 */
	private DocumentTypeDto documentTypeDto;
	
	/** 
	 * Getter and Setter function definitions 
	 */
	
	/** 
	 * Returns the document id of a Document object 
	 */
	public Integer getDocumentId() {
		return documentId;
	}
	
	/** 
	 * Sets the document id of a Document object 
	 */
	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}
	
	/** 
	 * Returns the title of a Document object 
	 */
	public String getDocumentTitle() {
		return documentTitle;
	}
	
	/** 
	 * Sets a title for a Document object 
	 */
	public void setDocumentTitle(final String documentTitle) {
		this.documentTitle = documentTitle;
	}
	
	/** 
	 * Returns the URL of a Document object 
	 */
	public String getUrl() {
		return url;
	}
	
	/** 
	 * Sets the URL of a Document object 
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
	
	/** 
	 * Returns the StorageType of a Document object 
	 */
	public StorageTypeDto getStorageTypeDto() {
		return storageTypeDto;
	}
	
	/** 
	 * Sets the StorageType of a Document object 
	 */
	public void setStorageTypeDto(final StorageTypeDto storageTypeDto) {
		this.storageTypeDto = storageTypeDto;
	}
	
	/** 
	 * Returns the Document 
	 */
	public byte[] getDocumentContent() {
		return documentContent;
	}
	
	/** 
	 * Sets the Document 
	 */
	public void setDocumentContent(final byte[] documentContent) {
		this.documentContent = documentContent;
	}
	
	/** 
	 * Returns the Account associated with a Document object 
	 */
	public AccountDto getAccount() {
		return account;
	}

	/** 
	 * Sets the Account associated with a Document object 
	 */
	public void setAccount(final AccountDto account) {
		this.account = account;
	}
	
	/** 
	 * Returns the DocumentType of a Document object 
	 */
	public DocumentTypeDto getDocumentTypeDto() {
		return documentTypeDto;
	}
	
	/** 
	 * Sets the DocumentType of a Document object 
	 */
	public void setDocumentTypeDto(final DocumentTypeDto documentTypeDto) {
		this.documentTypeDto = documentTypeDto;
	}

}
