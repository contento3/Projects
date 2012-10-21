package com.contento3.cms.page.category.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
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
	 * Data access layer for Account
	 */
	private AccountDao accountDao;

	/**
	 * Assembler for category
	 */
	private CategoryAssembler categoryAssembler;
	
	CategoryServiceImpl(final CategoryAssembler categoryAssembler,final CategoryDao categoryDao,final AccountDao accountDao){
		this.categoryDao=categoryDao;
		this.categoryAssembler=categoryAssembler;
		this.accountDao = accountDao;
	}//end CategoryServiceImpl()
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final CategoryDto categoryDto)  {
		final Account account = accountDao.findById(categoryDto.getAccountId());
		final Category category = categoryAssembler.dtoToDomain(categoryDto);
		category.setAccount(account);
		 return categoryDao.persist(category);
	}//end create()
	

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CategoryDto findCategoryByName(final String categoryName,final Integer accountId) {
		final Category category = categoryDao.findCategoryByName(categoryName,accountId);
		final CategoryDto categoryDto = categoryAssembler.domainToDto(category);
		return categoryDto;
	}//end findCategoryByName()

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<CategoryDto> findNullParentIdCategory(final Integer accountId){
		Collection<Category> categories = categoryDao.findNullParentIdCategory(accountId);
		Collection<CategoryDto> categoryDtos = categoryAssembler.domainsToDtos(categories);
		return categoryDtos;
	}//end findNullParentIdCategory()
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<CategoryDto> findChildCategories(final Integer parentId,final Integer accountId){
		Collection<Category> categories = categoryDao.findChildCategories(parentId,accountId);
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
			 category.setAccount(accountDao.findById(categoryDto.getAccountId()));
			 parent.getChild().add(category);
		 }
		 categoryDao.update(category);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<CategoryDto> findByAccountId(final Integer accountId){
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
		final Collection <Category> categoryChildren = categoryDao.findChildCategories(dtoToDelete.getCategoryId(),dtoToDelete.getAccountId());
		if (CollectionUtils.isEmpty(categoryChildren)){
			categoryDao.delete(categoryAssembler.dtoToDomain(dtoToDelete));
		}
		else {
			throw new EntityCannotBeDeletedException("Category ["+dtoToDelete.getName()+ "] have " +categoryChildren.size()+ " child categories.");
		}
	}
	
	
}//end CategoryServiceImpl class
