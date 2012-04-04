package com.contento3.cms.page.category.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.contento3.cms.page.category.model.Category;

public class CategoryDto {
	
	private Integer categoryId;

	private String categoryName;
	
	private CategoryDto parent;

	private Collection<CategoryDto> child; 

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(final Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryDto getParent() {
		return parent;
	}

	public void setParent(final CategoryDto parent) {
		this.parent = parent;
	}

	public 	Collection<CategoryDto> getChild() {
		return child;
	}

	public void setChild(final 	Collection<CategoryDto> child) {
		this.child = child;
	}
	

}
