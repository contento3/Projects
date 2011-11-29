package com.olive.cms.page.section.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAGE_SECTION_TYPE")
public class PageSectionType implements Serializable {
	
	private static final long serialVersionUID = 4973061231287029857L;

	@Id @GeneratedValue
	@Column(name = "PAGE_SECTION_TYPE_ID")
	private Integer id;
	
	@Column(name = "PAGE_SECTION_TYPE_NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
