package com.contento3.thymeleaf.dialect.category;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.site.page.pathbuilder.PathBuilder;
import com.contento3.site.page.pathbuilder.context.CategoryPathBuilderContext;
import com.contento3.site.page.pathbuilder.impl.CategoryPathBuilder;
import com.contento3.cms.page.service.impl.PageServiceImpl;
import com.contento3.common.exception.EntityNotFoundException;

public class CategoryTemplateHelper {

	private static final Logger LOGGER = Logger.getLogger(PageServiceImpl.class);

	/**
	 * CategoryService to fetch category data
	 */
	final transient CategoryService categoryService;

	final PathBuilder<CategoryPathBuilderContext> pathBuilder;
	
	public CategoryTemplateHelper() {
		final ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.categoryService = (CategoryService) ctx.getBean("categoryService");
		this.pathBuilder = new CategoryPathBuilder();
	}
    
	/**
     * Gets the list of categories for a given parent
     *
     * @param categoryId
     * @param accountId
     * @return Collection <CategoryDto>
     */
	public Collection <CategoryDto> getCategoriesForParent(final Integer categoryId,final Integer accountId) {
    	Collection<CategoryDto> categoryList=null;
		try {
			categoryList = this.categoryService.findChildCategories(categoryId,accountId);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	final CategoryPathBuilderContext context = new CategoryPathBuilderContext(categoryList);
    	
    	pathBuilder.build(context);
    	return categoryList;
    }
    
}