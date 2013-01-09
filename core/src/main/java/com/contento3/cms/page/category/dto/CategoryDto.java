package com.contento3.cms.page.category.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.TreeDto;

public class CategoryDto extends TreeDto{

	private Integer categoryId;

	private String name;

	private CategoryDto parent;

	private Collection<CategoryDto> child; 

	private Integer accountId;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(final Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}
	
	public Integer getId(){
		return this.categoryId;
	}
	
	
	public Collection<TreeDto> getChildDto(){
		Collection<TreeDto> collection = new ArrayList<TreeDto>();
		for(CategoryDto cat: this.child){
			collection.add(cat);
		}
		return collection;
	}



}

