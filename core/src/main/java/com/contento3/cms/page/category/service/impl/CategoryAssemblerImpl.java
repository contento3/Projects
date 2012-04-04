package com.contento3.cms.page.category.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.category.service.CategoryAssembler;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class CategoryAssemblerImpl implements CategoryAssembler {

	String parentName=null;
	Stack<String> parentStack = new Stack<String>();
	@Override
	public Category dtoToDomain(CategoryDto dto) {
		Category domain = new Category();
		if(dto.getCategoryId()!=null)
			domain.setCategoryId((dto.getCategoryId()));
		
		domain.setCategoryName(dto.getCategoryName());
		
//		if(dto.getParent()!=null ){
//			//if(!(dto.getParent().getCategoryName().equals(parentName))){
//				domain.setParent(dtoToDomain(dto.getParent()));
//			//}
//		}
//		
		//parentName=dto.getCategoryName();
		//parentStack.push(parentName);
//		if(dto.getChild()!=null){
//			if(!(dto.getChild().isEmpty())){
//				domain.setChild(dtosToDomains(dto.getChild()));
//			}
//		}//end if
		
//		if(!parentStack.empty()){
//			parentStack.pop();
//			if(!parentStack.empty()){
//				parentName = parentStack.pop();
//			}
//				parentStack.push(parentName);	
//		}//end if
		
		return domain;
	}//end dtoToDomain()

	@Override
	public CategoryDto domainToDto(Category domain) {
		
		CategoryDto dto = new CategoryDto();
		dto.setCategoryId(domain.getCategoryId());
		dto.setCategoryName(domain.getCategoryName());
		if(domain.getParent()!=null){
			if(!(domain.getParent().getCategoryName().equals(parentName))){
				dto.setParent(domainToDto(domain.getParent()));
			}
		}

		parentName=domain.getCategoryName();
		parentStack.push(parentName);
		if(domain.getChild()!=null){
			if(!(domain.getChild().isEmpty())){
				dto.setChild(domainsToDtos(domain.getChild()));
			}
		}
		if(!parentStack.empty()){
			parentStack.pop();
			if(!parentStack.empty()){
				parentName = parentStack.pop();
				
			}
			parentStack.push(parentName);
		}
		return dto;	
	}//end domainToDto()

	@Override
	public Collection<CategoryDto> domainsToDtos(Collection<Category> domains) {
		Collection <CategoryDto> dtos = new ArrayList<CategoryDto>();
		
	
		for (Category category : domains){
			dtos.add(domainToDto(category));
		}
		parentStack.clear();
		parentName=null;
		return dtos;
	}//end domainsToDtos()

	@Override
	public Collection<Category> dtosToDomains(Collection<CategoryDto> dtos) {
		Collection <Category> domains = new ArrayList<Category>();
		//parentStack= new Stack<String>();
		for (CategoryDto category : dtos){
			domains.add(dtoToDomain(category));
		}
		parentStack.clear();
		parentName=null;
		return domains;
	}//end dtosToDomains()

}//end class
