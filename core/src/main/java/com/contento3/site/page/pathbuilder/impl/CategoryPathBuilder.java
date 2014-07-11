package com.contento3.site.page.pathbuilder.impl;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.site.page.pathbuilder.PathBuilder;
import com.contento3.site.page.pathbuilder.context.CategoryPathBuilderContext;

public class CategoryPathBuilder implements PathBuilder<CategoryPathBuilderContext> {

	@Override
	public void build(final CategoryPathBuilderContext context) {
		String path = "";
		final Collection <CategoryDto> categories = context.getCategories();
		for (CategoryDto category : categories){
			category.setCategoryPath(buildCategoryPath(category,path));
		}
	}

	private String buildCategoryPath(final CategoryDto category,String path){
		if (category!=null){
			path = category.getName()!=null?category.getName().replace(" ", "-")+ "/" + path:"";
			return buildCategoryPath(category.getParent(),path);
		}
		return path;
	}
	
}
