package com.contento3.account.dto;


public class ModuleCategoryDto {

	private Integer moduleCategoryId;

	private String categoryName;

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
