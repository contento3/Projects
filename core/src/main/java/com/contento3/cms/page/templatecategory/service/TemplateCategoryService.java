package com.contento3.cms.page.templatecategory.service;

import java.util.Collection;

import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.StorableService;

public interface TemplateCategoryService extends
		StorableService<TemplateCategoryDto> {

	/**
	 * Finds the category by categoryName
	 * @param categoryName
	 * @return CategoryDto
	 */
	TemplateCategoryDto findCategoryByName(String categoryName,Integer accountId);

    /**
     * Create new category
     * @param categoryDto
     * @return
     */
	Integer create(TemplateCategoryDto templateCategoryDto,Integer parentId) throws EntityAlreadyFoundException;

	/**
	 * Returns a CategoryDto based on id.
	 * @param id
	 * @return
	 */
	TemplateCategoryDto findById(Integer id);

	 /**
     * Used to update the category.
     * @param categoryDto
     * @param parentCategoryId
     * @throws EntityAlreadyFoundException 
     */
	void update(TemplateCategoryDto templateCategoryDto, Integer parentCategroyId); 

	Collection<TemplateCategoryDto> findAll();
}
