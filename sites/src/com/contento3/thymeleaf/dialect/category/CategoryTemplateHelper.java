package com.contento3.thymeleaf.dialect.category;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;

public class CategoryTemplateHelper {

	/**
	 * CategoryService to fetch category data
	 */
	final transient CategoryService categoryService;

	public CategoryTemplateHelper() {
		final ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.categoryService = (CategoryService) ctx.getBean("categoryService");
	}
    
	/**
     * Gets the list of categories for a given parent
     *
     * @param categoryId
     * @param accountId
     * @return Collection <CategoryDto>
     */
	public Collection <CategoryDto> getCategoriesForParent(final Integer categoryId,final Integer accountId) {
    	final Collection<CategoryDto> categoryList = this.categoryService.findChildCategories(categoryId,accountId);
    	return categoryList;
    }
    
	
}