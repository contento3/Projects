package com.contento3.cms.page.category.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.category.service.CategoryAssembler;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;

public class CategoryAssemblerImpl implements CategoryAssembler {

	@Override
	public Category dtoToDomain(CategoryDto dto) {
		Category domain = new Category();
	
		if(dto.getParent()!=null){
			domain.setParent(dto.getParent());
		}
		if(dto.getChild()!=null){
			domain.setChild(dto.getChild());
		}
//		if(dto.getCategoryId()!=null){
//			domain.setCategoryId((dto.getCategoryId()));
//		}
		domain.setCategoryId((dto.getCategoryId()));
		domain.setCategoryName(dto.getCategoryName());
		//domain.setParent(dto.getParent());
		//domain.setChild(dto.getChild());
		return domain;
	}//end dtoToDomain()

	@Override
	public CategoryDto domainToDto(Category domain) {
		CategoryDto dto = new CategoryDto();
		
		if(domain.getParent()!=null){
			dto.setParent(domain.getParent());
		}
		if(domain.getChild()!=null){
			dto.setChild(domain.getChild());
		}
//		if(domain.getCategoryId()!=null){
//			dto.setCategoryId(domain.getCategoryId());
//		}
		dto.setCategoryId(domain.getCategoryId());
		dto.setCategoryName(domain.getCategoryName());
		return dto;	
	}//end domainToDto()

	@Override
	public Collection<CategoryDto> domainsToDtos(Collection<Category> domains) {
		Collection <CategoryDto> dtos = new ArrayList<CategoryDto>();
		
		Iterator<Category> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		return dtos;
	
	}//end domainsToDtos()

	@Override
	public Collection<Category> dtosToDomains(Collection<CategoryDto> dtos) {
		Collection <Category> domains = new ArrayList<Category>();

		for (CategoryDto category : dtos){
			domains.add(dtoToDomain(category));
		}
		return domains;
	}//end dtosToDomains()

}//end class
