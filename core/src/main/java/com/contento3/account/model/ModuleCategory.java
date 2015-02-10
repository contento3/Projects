package com.contento3.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "MODULE_CATEGORY", schema ="MODULES")
public class ModuleCategory {

	@Id @GeneratedValue
	@Column(name = "MODULE_CATEGORY_ID")
	private Integer moduleCategoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "CATEGORY_DESCRIPTION")
	private String categoryDescription;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(final String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	public Integer getModuleCategoryId() {
		return moduleCategoryId;
	}

	public void setModuleCategoryId(final Integer moduleCategoryId) {
		this.moduleCategoryId = moduleCategoryId;
	}


}
