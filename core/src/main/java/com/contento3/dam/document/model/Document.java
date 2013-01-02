package com.contento3.dam.document.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * @author nuketro0p3r
 *
 */
@Entity
@Table(name = "DOCUMENT", schema = "CMS")
public class Document implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;
	
	@Column(columnDefinition="TEXT", length = 75, name = "DOCUMENT_UUID",
			unique=true, nullable=false)
	private String documentUuid = UUID.randomUUID().toString();
	
	@Column(name = "DOCUMENT_TITLE")
	private String documentTitle;
	
	@Column(name = "URL")
	private String url;
	
	@OneToOne
	@JoinColumn(name = "STORAGE_TYPE_ID")
	private StorageType storageType;
	
	@Column(name = "DOCUMENT_CONTENT")
	private byte[] documentContent;

	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@OneToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID")
	private DocumentType documentType;
	
	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocumentUuid() {
		return documentUuid;
	}

	public void setDocumentUuid(String documentUuid) {
		this.documentUuid = documentUuid;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public byte[] getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

}
