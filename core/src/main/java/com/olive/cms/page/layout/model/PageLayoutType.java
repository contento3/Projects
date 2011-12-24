package com.olive.cms.page.layout.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAGE_LAYOUT_TYPE")
public class PageLayoutType implements Serializable{

	private static final long serialVersionUID = -8709656031145153646L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAGE_LAYOUT_TYPE_ID")
	private int id;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PAGE_LAYOUT_TYPE_NAME")
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	
}
