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
	private long document_type_id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	public long getDocument_type_id() {
		return document_type_id;
	}

	public void setDocument_type_id(long document_type_id) {
		this.document_type_id = document_type_id;
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
