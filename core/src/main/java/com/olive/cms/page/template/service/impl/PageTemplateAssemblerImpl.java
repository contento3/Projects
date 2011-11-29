package com.olive.cms.page.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.olive.cms.page.template.dto.PageTemplateDto;
import com.olive.cms.page.template.model.PageTemplate;
import com.olive.cms.page.template.model.PageTemplatePK;
import com.olive.cms.page.template.service.PageTemplateAssembler;
import com.olive.cms.page.template.service.TemplateAssembler;

public class PageTemplateAssemblerImpl implements PageTemplateAssembler  {

	private TemplateAssembler templateAssembler;
	
	
	@Override
	public PageTemplate dtoToDomain(PageTemplateDto dto) {
		PageTemplate domain = new PageTemplate();
		domain.setTemplateOrder(dto.getOrder());
		
		
		return domain;
	}

	@Override
	public PageTemplateDto domainToDto(PageTemplate domain) {
		PageTemplateDto pageTemplateDto = new PageTemplateDto();
		pageTemplateDto.setOrder(domain.getTemplateOrder());
		pageTemplateDto.setTemplateName(domain.getPrimaryKey().getTemplate().getTemplateName());
		return pageTemplateDto;
	}

	@Override
	public Collection<PageTemplateDto> domainsToDtos(
			Collection<PageTemplate> domains) {
		Collection <PageTemplateDto> dtos = new ArrayList<PageTemplateDto>();
		
		Iterator<PageTemplate> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		return dtos;
	}

	@Override
	public Collection<PageTemplate> dtosToDomains(
			Collection<PageTemplateDto> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
