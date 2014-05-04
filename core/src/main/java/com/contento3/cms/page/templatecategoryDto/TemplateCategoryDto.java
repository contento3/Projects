package com.contento3.cms.page.templatecategoryDto;

import com.contento3.common.dto.Dto;

public class TemplateCategoryDto extends Dto {
	
	private Integer templateCategoryId;
	
	private String templateCategoryName;
	
	private String templateCategoryDescription;
	
	@Override
	public Integer getId(){
		return this.getTemplateCategoryId();
	}
	
	@Override
	public String getName(){
		return this.templateCategoryName;
	}
	
	public Integer getTemplateCategoryId() {
		return templateCategoryId;
	}
	
	public void setTemplateCategoryId(Integer templateCategoryId) {
		this.templateCategoryId = templateCategoryId;
	}
	
	public String getTemplateCategoryName() {
		return templateCategoryName;
	}
	
	public void setTemplateCategoryName(String templateCategoryName) {
		this.templateCategoryName = templateCategoryName;
	}
	
	public String getTemplateCategoryDescription() {
		return templateCategoryDescription;
	}
	
	public void setTemplateCategoryDescription(String templateCategoryDescription) {
		this.templateCategoryDescription = templateCategoryDescription;
	}
}
