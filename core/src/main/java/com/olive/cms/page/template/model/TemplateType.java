package com.olive.cms.page.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPLATE_TYPE")
public class TemplateType {
	
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_TYPE_ID")
	private Integer templateTypeId;

	@Column(name = "TEMPLATE_TYPE_NAME")
	private String templateTypeName;

	@Column(name = "DESCRIPTION")
	private String description;
	

	@Column(name = "CONTENT_TYPE")
	private String contentType;
	//TODO
//	private TemplateContentTypeEnum contentType;
	
	public Integer getTemplateTypeId() {
		return templateTypeId;
	}

	public void setTemplateTypeId(Integer templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
