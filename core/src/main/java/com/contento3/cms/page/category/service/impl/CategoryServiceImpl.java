package com.contento3.cms.page.category.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dao.CategoryDao;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.category.service.CategoryAssembler;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.common.exception.EntityCannotBeDeletedException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class CategoryServiceImpl implements CategoryService {
	
	/**
	 * Data access layer for category.
	 */
	private CategoryDao categoryDao;
	
	/**
	 * Assembler for category
	 */
	private CategoryAssembler categoryAssembler;
	
	CategoryServiceImpl(final CategoryAssembler categoryAssembler,final CategoryDao categoryDao){
		this.categoryDao=categoryDao;
		this.categoryAssembler=categoryAssembler;
	}//end CategoryServiceImpl()
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final CategoryDto categoryDto)  {
		 return categoryDao.persist(categoryAssembler.dtoToDomain(categoryDto));
	}//end create()
	

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CategoryDto findCategoryByName(final String categoryName) {
		Category category = categoryDao.findCategoryByName(categoryName);
		CategoryDto categoryDto = categoryAssembler.domainToDto(category);
		 return categoryDto;
	}//end findCategoryByName()

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<CategoryDto> findNullParentIdCategory(){
		Collection<Category> categories = categoryDao.findNullParentIdCategory();
		Collection<CategoryDto> categoryDtos = categoryAssembler.domainsToDtos(categories);
		return categoryDtos;
	}//end findNullParentIdCategory()
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<CategoryDto> findChildCategories(final Integer parentId){
		Collection<Category> categories = categoryDao.findChildCategories(parentId);
		Collection<CategoryDto> categoryDtos = categoryAssembler.domainsToDtos(categories);
		return categoryDtos;
	}//end findChildCategories()
	
	
	@Transactional(readOnly = false)
	@Override
	public void update(final CategoryDto categoryDto,final Integer parentCategoryId){
		 final Category category = categoryAssembler.dtoToDomain(categoryDto);
		 if (null!=parentCategoryId){
			 final Category parent = categoryDao.findById(parentCategoryId);
			 category.setParent(parent);
			 parent.getChild().add(category);
		 }
		 categoryDao.update(category);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<CategoryDto> findBySiteId(final Integer siteId){
		 return categoryAssembler.domainsToDtos(categoryDao.findAll());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public CategoryDto findById(Integer id) {
		return categoryAssembler.domainToDto(categoryDao.findById(id));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(CategoryDto dtoToDelete) throws EntityCannotBeDeletedException {
		final Collection <Category> categoryChildren = categoryDao.findChildCategories(dtoToDelete.getCategoryId());
		if (CollectionUtils.isEmpty(categoryChildren)){
			categoryDao.delete(categoryAssembler.dtoToDomain(dtoToDelete));
		}
		else {
			throw new EntityCannotBeDeletedException("Category ["+dtoToDelete.getCategoryName()+ "] have " +categoryChildren.size()+ " child categories.");
		}
	}
	
	
}//end CategoryServiceImpl class
