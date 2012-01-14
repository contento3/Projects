package com.contento3.cms.page.template.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPLATE_DIRECTORY")
public class TemplateDirectory {

	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_DIRECTORY_ID")
	private Integer id;
	
	@Column(name = "DIRECTORY_NAME")
	private String directoryName;
	
	@ManyToOne
	@JoinColumn(name="parent")
	private TemplateDirectory parent;
	
	@OneToMany(mappedBy="parent")
	private Collection<TemplateDirectory> childDirectories;

	@Column(name = "IS_GLOBAL")
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
