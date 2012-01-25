package com.contento3.cms.page.category.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.category.dao.CategoryDao;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.category.service.CategoryAssembler;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateAssembler;
import com.contento3.common.exception.EnitiyAlreadyFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class CategoryServiceImpl implements CategoryService {

	
	private CategoryDao categoryDao;
	private CategoryAssembler categoryAssembler;
	
	CategoryServiceImpl(final CategoryAssembler categoryAssembler,final CategoryDao categoryDao){
		this.categoryDao=categoryDao;
		this.categoryAssembler=categoryAssembler;
	}//end CategoryServiceImpl()
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(final CategoryDto categoryDto)  {
		 categoryDao.persist(dtoToDomain(categoryDto));
	}//end create()
	
	
	public Category dtoToDomain(final CategoryDto dto){
		Category category = categoryAssembler.dtoToDomain(dto);
		return category;
	}//end dtoToDomain()

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CategoryDto findCategoryByName(final String categoryName) {
		Category category = categoryDao.findCategoryByName(categoryName);
		return categoryAssembler.domainToDto(category);
	}//end findCategoryByName()

	
}//end CategoryServiceImpl class
