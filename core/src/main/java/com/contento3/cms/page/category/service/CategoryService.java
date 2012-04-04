package com.contento3.cms.page.category.service;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.Service;

public interface CategoryService extends Service<CategoryDto>{
	
	/**
	 * Finds the category by categoryName
	 * @param categoryName
	 * @return CategoryDto
	 */
	CategoryDto findCategoryByName(final String categoryName);
	
	/**
	 * Finds the categories whose parentId are null
	 * @return CategoryDto
	 */
	Collection<CategoryDto> findNullParentIdCategory();
	
	/**
	 * Finds the child categories by their parentId
	 * @return CategoryDto
	 */
	Collection<CategoryDto> findChildCategories(final Integer parentId);
	
	
	 /**
     * Used to update the category.
     * @param categoryDto
     * @throws EntityAlreadyFoundException 
     */
    void update(final CategoryDto categoryDto) ;
	
}//end
