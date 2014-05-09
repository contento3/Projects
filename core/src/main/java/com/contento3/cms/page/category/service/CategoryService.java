package com.contento3.cms.page.category.service;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.service.StorableService;

public interface CategoryService extends StorableService<CategoryDto>{

	/**
	 * Finds the category by categoryName
	 * @param categoryName
	 * @return CategoryDto
	 * @throws EntityNotFoundException 
	 */
	CategoryDto findCategoryByName(String categoryName,Integer accountId) throws EntityNotFoundException;

	/**
	 * Finds the categories whose parentId are null
	 * @return CategoryDto
	 * @throws EntityNotFoundException 
	 */
	Collection<CategoryDto> findNullParentIdCategory(Integer accountId) throws EntityNotFoundException;

	/**
	 * Finds the child categories by their parentId
	 * @return CategoryDto
	 * @throws EntityNotFoundException 
	 */
	Collection<CategoryDto> findChildCategories(Integer parentId,Integer accountId) throws EntityNotFoundException;


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
	 * @throws EntityNotFoundException 
	 */
	Collection<CategoryDto> findByAccountId(Integer accountId) throws EntityNotFoundException;

	/**
	 * Returns a CategoryDto based on id.
	 * @param id
	 * @return
	 * @throws EntityNotFoundException 
	 */
	CategoryDto findById(Integer id) throws EntityNotFoundException;

	 /**
     * Used to update the category.
     * @param categoryDto
     * @param parentCategoryId
     * @throws EntityAlreadyFoundException 
     */
	void update(CategoryDto categoryDto, Integer parentCategroyId); 

}//end