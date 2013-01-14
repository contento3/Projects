package com.contento3.dam.document.dto;

import com.contento3.account.model.Account;
import com.contento3.common.dto.Dto;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.storagetype.model.StorageType;

public class DocumentDto extends Dto {
	
	/**
	 * Id for the document
	 */
	private Integer documentId;

	/**
	 * Unique random uuid
	 */
	private String documentUuid;

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
	private StorageType storageType;

	/**
	 * The contents of the document
	 */
	private byte[] documentContent;

	/**
	 * The account associated with the document entitiy.
	 */
	private Account account;

	/**
	 * The type of the document.
	 */
	private DocumentType documentType;
	
	/* Getter and Setter function definitions */
	
	/* Returns the document id of a Document object */
	public Integer getDocumentId() {
		return documentId;
	}
	
	/* Sets the document id of a Document object */
	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}
	
	/* Returns the unique uid of a Document object */
	public String getDocumentUuid() {
		return documentUuid;
	}
	
	/* Sets the unique uid of a Document object */
	public void setDocumentUuid(final String documentUuid) {
		this.documentUuid = documentUuid;
	}
	
	/* Returns the title of a Document object */
	public String getDocumentTitle() {
		return documentTitle;
	}
	
	/* Sets a title for a Document object */
	public void setDocumentTitle(final String documentTitle) {
		this.documentTitle = documentTitle;
	}
	
	/* Returns the URL of a Document object */
	public String getUrl() {
		return url;
	}
	
	/* Sets the URL of a Document object */
	public void setUrl(final String url) {
		this.url = url;
	}
	
	/* Returns the StorageType of a Document object */
	public StorageType getStorageType() {
		return storageType;
	}
	
	/* Sets the StorageType of a Document object */
	public void setStorageType(final StorageType storageType) {
		this.storageType = storageType;
	}
	
	/* Returns the Document */
	public byte[] getDocumentContent() {
		return documentContent;
	}
	
	/* Sets the Document */
	public void setDocumentContent(final byte[] documentContent) {
		this.documentContent = documentContent;
	}
	
	/* Returns the Account associated with a Document object */
	public Account getAccount() {
		return account;
	}
	
	/* Sets the Account associated with a Document object */
	public void setAccount(final Account account) {
		this.account = account;
	}
	
	/* Returns the DocumentType of a Document object */
	public DocumentType getDocumentType() {
		return documentType;
	}
	
	/* Sets the DocumentType of a Document object */
	public void setDocumentType(final DocumentType documentType) {
		this.documentType = documentType;
	}
}
