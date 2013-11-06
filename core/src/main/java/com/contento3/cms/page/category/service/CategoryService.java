package com.contento3.cms.page.category.service;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.Service;

public interface CategoryService extends Service<CategoryDto>{

	/**
	 * Finds the category by categoryName
	 * @param categoryName
	 * @return CategoryDto
	 */
	CategoryDto findCategoryByName(String categoryName,Integer accountId);

	/**
	 * Finds the categories whose parentId are null
	 * @return CategoryDto
	 */
	Collection<CategoryDto> findNullParentIdCategory(Integer accountId);

	/**
	 * Finds the child categories by their parentId
	 * @return CategoryDto
	 */
	Collection<CategoryDto> findChildCategories(Integer parentId,Integer accountId);


    /**
     * Create new category
     * @param categoryDto
     * @return
     */
	Integer create(CategoryDto categoryDto,Integer parentId) throws EntityAlreadyFoundException;

	/**
	 * Finds all the category for a given account
	 * @param accountId
	 * @return Collection<CategoryDto>
	 */
	Collection<CategoryDto> findByAccountId(Integer accountId);

	/**
	 * Returns a CategoryDto based on id.
	 * @param id
	 * @return
	 */
	CategoryDto findById(Integer id);

	 /**
     * Used to update the category.
     * @param categoryDto
     * @param parentCategoryId
     * @throws EntityAlreadyFoundException 
     */
	void update(CategoryDto categoryDto, Integer parentCategroyId); 

}//end