
package com.contento3.cms.page.templatecategory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "TEMPLATE_CATEGORIES")
public class TemplateCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Primary key for category


	 */
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_CATEGORY_ID")
	private Integer temlateCategoryId;
	
	@Column(name = "TEMPLATE_CATEGORY_NAME")
	private String templateCategoryName;
	
	public int getTemlateCategoryId() {
		return temlateCategoryId;
	}
	public void setTemlateCategoryId(int temlateCategoryId) {
		this.temlateCategoryId = temlateCategoryId;
	}
	
	
	@Column(name = "TEMPLATE_CATEGORY_DESCRIPTION")
	private String templateCategoryDescription;
	
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