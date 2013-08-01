package com.contento3.cms.page.category.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.category.service.CategoryAssembler;

public class CategoryAssemblerImpl implements CategoryAssembler {

	@Override
	public Category dtoToDomain(CategoryDto dto) {
		Category domain = new Category();
		if(dto.getCategoryId()!=null)
			domain.setCategoryId((dto.getCategoryId()));
		
		domain.setCategoryName(dto.getName());
		
		if(dto.getParent()!=null ){
				domain.setParent(dtoToDomain(dto.getParent()));
		}
		return domain;
	}//end dtoToDomain()

	@Override
	public CategoryDto domainToDto(Category domain) {
		CategoryDto dto = new CategoryDto();
		dto.setCategoryId(domain.getCategoryId());
		dto.setName(domain.getCategoryName());
		
		dto.setAccountId(domain.getAccount().getAccountId());
		
//		if(domain.getParent()!=null){
//			dto.setParent(domainToDto(domain.getParent()));
//		}
		
		Collection <Category> children = domain.getChild();
		Collection <CategoryDto> categoryChildren = new ArrayList<CategoryDto>();
		for (Category child : children){
			categoryChildren.add(domainToDto(child));
		}
		
		dto.setChild(categoryChildren);
		return dto;	
	}//end domainToDto()

	@Override
	public Collection<CategoryDto> domainsToDtos(Collection<Category> domains) {
		Collection <CategoryDto> dtos = new ArrayList<CategoryDto>();
		for (Category category : domains){
			dtos.add(domainToDto(category));
		}
		return dtos;
	}//end domainsToDtos()

	@Override
	public Collection<Category> dtosToDomains(Collection<CategoryDto> dtos) {
		Collection <Category> domains = new ArrayList<Category>();
		if (!CollectionUtils.isEmpty(dtos))
			for (CategoryDto category : dtos){
				domains.add(dtoToDomain(category));
			}
		return domains;
	}//end dtosToDomains()
	
}//end class
