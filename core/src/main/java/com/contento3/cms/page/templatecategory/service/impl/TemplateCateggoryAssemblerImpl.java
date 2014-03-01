package com.contento3.cms.page.templatecategory.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.templatecategory.model.TemplateCategory;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryAssembler;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;

public class TemplateCateggoryAssemblerImpl implements
		TemplateCategoryAssembler {

	@Override
	public TemplateCategory dtoToDomain(TemplateCategoryDto dto) {
		TemplateCategory domain = new TemplateCategory();
		if(dto.getTemlateCategoryId()!=null)
			domain.setTemlateCategoryId(dto.getTemlateCategoryId());
		
		domain.setTemplateCategoryName(dto.getTemplateCategoryName());
		
		if(dto.getTemplateCategoryDescription()!=null ){
				domain.setTemplateCategoryDescription(dto.getTemplateCategoryDescription());
		}
		return domain;
	}//end dtoToDomain()



	@Override
	public TemplateCategoryDto domainToDto(TemplateCategory domain) {
		TemplateCategoryDto dto = new TemplateCategoryDto();
		dto.setTemlateCategoryId(domain.getTemlateCategoryId());
		dto.setTemplateCategoryName(domain.getTemplateCategoryName());
		
		dto.setTemplateCategoryDescription(domain.getTemplateCategoryDescription());
		return dto;
		
	}

	@Override
	public Collection<TemplateCategoryDto> domainsToDtos(
			Collection<TemplateCategory> domains) {
		Collection <TemplateCategoryDto> dtos = new ArrayList<TemplateCategoryDto>();
		for (TemplateCategory templateCategory : domains){
			dtos.add(domainToDto(templateCategory));
		}
		return dtos;
		
	}

	@Override
	public Collection<TemplateCategory> dtosToDomains(
			Collection<TemplateCategoryDto> dtos) {
		Collection <TemplateCategory> domains = new ArrayList<TemplateCategory>();
		if (!CollectionUtils.isEmpty(dtos))
			for (TemplateCategoryDto category : dtos){
				domains.add(dtoToDomain(category));
			}
		return domains;
	}

		
}
