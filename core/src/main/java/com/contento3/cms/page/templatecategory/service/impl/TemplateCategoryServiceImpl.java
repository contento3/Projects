package com.contento3.cms.page.templatecategory.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.amazonaws.services.ec2.model.Tenancy;
import com.contento3.account.model.Account;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.templatecategory.dao.TemplateCategoryDao;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryAssembler;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.content.storage.exception.InvalidStorageException;

public class TemplateCategoryServiceImpl implements TemplateCategoryService {

		private TemplateCategoryAssembler templateCategoryAssembler;
		
		private TemplateCategoryDao templateCategoryDao;
	
	
		TemplateCategoryServiceImpl(final TemplateCategoryAssembler templateCategoryAssembler, final TemplateCategoryDao templateCategoryDao){
			
			Validate.notNull(templateCategoryAssembler,"templateCategoryAssembler can not be null");
			Validate.notNull(templateCategoryDao,"templateCategoryDao can not be null");
			this.templateCategoryAssembler = templateCategoryAssembler;
			this.templateCategoryDao = templateCategoryDao;
			
		}

	@Override
	public Object create(TemplateCategoryDto dto)
			throws EntityAlreadyFoundException, EntityNotCreatedException,
			InvalidStorageException {
		final com.contento3.cms.page.templatecategory.model.TemplateCategory tempCategory  = templateCategoryAssembler.dtoToDomain(dto);
		templateCategoryDao.persist(tempCategory);
		return null;
		
	}

	@Override
	public void delete(TemplateCategoryDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		

	}

	@Override
	public TemplateCategoryDto findCategoryByName(String categoryName, Integer accountId) {
		
		return null;
	}

	@Override
	public Integer create(TemplateCategoryDto categoryDto, Integer parentId)
			throws EntityAlreadyFoundException {
		
		return null;
	}

	@Override
	public TemplateCategoryDto findById(Integer id) {
		
		return null;
	}

	@Override
	public void update(TemplateCategoryDto categoryDto, Integer parentCategroyId) {
		

	}

	@Override
	public Collection<TemplateCategoryDto> findAll() {
		return templateCategoryAssembler.domainsToDtos(templateCategoryDao.findAll());
	}

}
