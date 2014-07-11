package com.contento3.site.page.pathbuilder.context;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;

public class CategoryPathBuilderContext extends PathBuilderContext {

	Collection <CategoryDto> categories;

	public CategoryPathBuilderContext(final Collection <CategoryDto> categories){
		this.categories = categories;
	}
	
	public Collection<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(final Collection<CategoryDto> categories) {
		this.categories = categories;
	}
	
}
