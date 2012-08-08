package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.section.dao.PageSectionTypeDao;
import com.contento3.cms.page.section.model.PageSectionType;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.dao.PageTemplateDao;
import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.model.PageTemplate;
import com.contento3.cms.page.template.model.PageTemplatePK;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.service.PageTemplateAssembler;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.common.exception.EntityAlreadyFoundException;

public class PageTemplateServiceImpl implements PageTemplateService {

	private PageTemplateDao dao;
	
	private PageTemplateAssembler assembler;
	
	private TemplateDao templateDao;
	
	private PageSectionTypeDao sectionTypeDao;
	
	private PageDao pageDao;
	
	PageTemplateServiceImpl(final PageTemplateDao dao,final TemplateDao templateDao,final PageSectionTypeDao sectionTypeDao,
			final PageDao pageDao,final PageTemplateAssembler assembler){
		this.dao = dao;
		this.assembler = assembler;
		this.templateDao = templateDao;
		this.pageDao = pageDao;
		this.sectionTypeDao = sectionTypeDao;
	}
	
	@Override
	public PageTemplatePK create(PageTemplateDto dto) throws EntityAlreadyFoundException {
		PageTemplate pageTemplate = assembler.dtoToDomain(dto);
		PageSectionType pageSectionType;
		
		if (null==dto.getSectionTypeId()){
			pageSectionType = sectionTypeDao.findByName("CUSTOM");
		}
		else {
			pageSectionType = sectionTypeDao.findById(dto.getSectionTypeId());
		}
		
		Template template = templateDao.findById(dto.getTemplateId());
		Page page = pageDao.findById(dto.getPageId());
		
		PageTemplatePK pk = new PageTemplatePK();
		pk.setPage(page);
		pk.setSectionType(pageSectionType);
		pk.setTemplate(template);

		PageTemplate pageTemplatePresent = dao.findById(pk);
		
		if (null==pageTemplatePresent) {
			pageTemplate.setPrimareKey(pk);
			return dao.persist(pageTemplate);
		}
		else {
			throw new EntityAlreadyFoundException("Page template already exist.");
		}
	}
	
	@Override
	public Collection<PageTemplateDto> findByPageAndPageSectionType(final Integer pageId,final Integer pageSectionTypeId) {
		return assembler.domainsToDtos(dao.findByPageAndPageSectionType(pageId, pageSectionTypeId));
	}

	@Override
	public Collection<PageTemplateDto> findByPageId(Integer pageId) {
		return assembler.domainsToDtos(dao.findByPageId(pageId));
	}

	@Override
	public Collection<PageTemplateDto> findByPageAndPageSectionType(
			Integer pageId, PageSectionTypeEnum pageSectionType) {
		return assembler.domainsToDtos(dao.findByPageAndPageSectionType(pageId, pageSectionType));
	}

}
