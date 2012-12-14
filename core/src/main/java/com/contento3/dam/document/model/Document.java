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

import org.hibernate.annotations.GenericGenerator;

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
	private Integer document_id;
	
	@Column(columnDefinition="TEXT", length = 75, name = "DOCUMENT_UUID",
			unique=true, nullable=false)
	private String document_uuid = UUID.randomUUID().toString();
	
	@Column(name = "DOCUMENT_TITLE")
	private String document_title;
	
	@Column(name = "URL")
	private String url;
	
	@OneToOne
	@JoinColumn(name = "STORAGE_TYPE_ID")
	private StorageType storage_type;
	
	@Column(name = "DOCUMENT_CONTENT")
	private byte[] document_content;

	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@OneToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID")
	private DocumentType document_type;
	
	public Integer getDocument_id() {
		return document_id;
	}

	public void setDocument_id(Integer document_id) {
		this.document_id = document_id;
	}

	public String getDocument_uuid() {
		return document_uuid;
	}

	public void setDocument_uuid(String document_uuid) {
		this.document_uuid = document_uuid;
	}

	public String getDocument_title() {
		return document_title;
	}

	public void setDocument_title(String document_title) {
		this.document_title = document_title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StorageType getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(StorageType storage_type) {
		this.storage_type = storage_type;
	}

	public byte[] getDocument_content() {
		return document_content;
	}

	public void setDocument_content(byte[] document_content) {
		this.document_content = document_content;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public DocumentType getDocument_type() {
		return document_type;
	}

	public void setDocument_type(DocumentType document_type) {
		this.document_type = document_type;
	}
}
