package com.contento3.dam.document.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT_TYPE", schema = "CMS")
public class DocumentType  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name = "DOCUMENT_TYPE_ID")
	private long documentTypeId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	public long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
