package com.olive.cms.page.template.dto;

import java.util.Collection;

import com.olive.cms.page.template.model.TemplateDirectory;

public class TemplateDirectoryDto {

	private Integer id;
	
	private String directoryName;
	
	private TemplateDirectory parent;
	
	private Collection<TemplateDirectory> childDirectories;

	private boolean isGlobal;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public TemplateDirectory getParent() {
		return parent;
	}

	public void setParent(TemplateDirectory parent) {
		this.parent = parent;
	}

	public Collection<TemplateDirectory> getChildDirectories() {
		return childDirectories;
	}

	public void setChildDirectories(Collection<TemplateDirectory> childDirectories) {
		this.childDirectories = childDirectories;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

}
