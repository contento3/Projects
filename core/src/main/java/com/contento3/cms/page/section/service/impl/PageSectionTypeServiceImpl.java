package com.contento3.cms.page.section.service.impl;

import com.contento3.cms.page.section.dao.PageSectionTypeDao;
import com.contento3.cms.page.section.dto.PageSectionTypeDto;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.section.service.PageSectionTypeAssembler;
import com.contento3.cms.page.section.service.PageSectionTypeService;

public class PageSectionTypeServiceImpl implements PageSectionTypeService{
	
	private PageSectionTypeDao pageSectionTypeDao;
	
	private PageSectionTypeAssembler assembler;

	public PageSectionTypeServiceImpl (final PageSectionTypeDao  pageSectionTypeDao, 
									   final PageSectionTypeAssembler assembler ) {
		
		this.pageSectionTypeDao = pageSectionTypeDao;
		this.assembler = assembler;
	}

	@Override
	public PageSectionTypeDto findByName(final PageSectionTypeEnum pageSectionTypeName) {
		String section = null;
		if (pageSectionTypeName==PageSectionTypeEnum.HEADER){
			section = "HEADER";
		}
		else if (pageSectionTypeName==PageSectionTypeEnum.FOOTER){
			section = "FOOTER";
		}
		else if (pageSectionTypeName==PageSectionTypeEnum.BODY){
			section = "BODY";
		}
		else if (pageSectionTypeName==PageSectionTypeEnum.CUSTOM){
			section = "CUSTOM";
		}
		
		return assembler.domainToDto(pageSectionTypeDao.findByName(section));
	}

	@Override
	public PageSectionTypeDto findById(final Integer id) {
		return assembler.domainToDto(pageSectionTypeDao.findById(id));
	}

	public void create(PageSectionTypeDto pageSectionTypeDto){}
}
