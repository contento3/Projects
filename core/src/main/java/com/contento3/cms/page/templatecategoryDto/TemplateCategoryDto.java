package com.contento3.cms.page.templatecategoryDto;

import com.contento3.common.dto.Dto;

public class TemplateCategoryDto extends Dto {
	private Integer temlateCategoryId;
	private String templateCategoryName;
	private String templateCategoryDescription;
	
	// for combo data loader
	private Integer id;
	private String name;
	
	public Integer getTemlateCategoryId() {
		return temlateCategoryId;
	}
	public Integer getId() {
		return temlateCategoryId;
	}
	public void setTemlateCategoryId(Integer temlateCategoryId) {
		this.temlateCategoryId = temlateCategoryId;
		this.id = temlateCategoryId;
		
	}
	public String getTemplateCategoryName() {
		return templateCategoryName;
	}
	public String getName() {
		return templateCategoryName;
	}
	public void setTemplateCategoryName(String templateCategoryName) {
		this.templateCategoryName = templateCategoryName;
		this.name= templateCategoryName;
	}
	public String getTemplateCategoryDescription() {
		return templateCategoryDescription;
	}
	public void setTemplateCategoryDescription(String templateCategoryDescription) {
		this.templateCategoryDescription = templateCategoryDescription;
	}
}
