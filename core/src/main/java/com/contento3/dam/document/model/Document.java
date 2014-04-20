package com.contento3.dam.document.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.account.model.Account;
import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * @author nuketro0p3r
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
	
	/**
	 * Library associated with document
	 */
	@ManyToOne
	@JoinColumn(name = "LIBRARY_ID")
	private ImageLibrary contentLibrary;
	
	public ImageLibrary getContentLibrary() {
		return contentLibrary;
	}

	public void setContentLibrary(ImageLibrary contentLibrary) {
		this.contentLibrary = contentLibrary;
	}

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
