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

	/**
	 * Getters/Setters for the DocumentDto
	 */
	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocumentUuid() {
		return documentUuid;
	}

	public void setDocumentUuid(final String documentUuid) {
		this.documentUuid = documentUuid;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(final String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(final StorageType storageType) {
		this.storageType = storageType;
	}

	public byte[] getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(final byte[] documentContent) {
		this.documentContent = documentContent;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(final DocumentType documentType) {
		this.documentType = documentType;
	}
}
