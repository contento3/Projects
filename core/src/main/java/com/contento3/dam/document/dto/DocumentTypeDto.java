package com.contento3.dam.document.dto;

import com.contento3.common.dto.Dto;

public class DocumentTypeDto extends Dto {
	
	/**
	 * Id for the document type
	 */
	private long documentTypeId;

	/**
	 * name of document type
	 */
	private String name;

	/**
	 * description of the document type
	 */
	private String description;

	/**
	 * get the document type id
	 */
	public long getDocumentTypeId() {
		return documentTypeId;
	}

	/**
	 * sets the document type id
	 */
	public void setDocumentTypeId(final long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	/**
	 * gets the document type name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the document type name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * gets the document type description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the document type description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
}
