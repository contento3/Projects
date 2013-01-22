package com.contento3.dam.document.dto;

import com.contento3.common.dto.Dto;

public class DocumentTypeDto extends Dto {
	
	private long documentTypeId;
	
	private String name;
	
	private String description;
	
	public long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(final long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
