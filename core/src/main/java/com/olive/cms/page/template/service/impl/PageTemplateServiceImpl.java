package com.olive.cms.page.template.service.impl;

import java.util.Collection;

import com.olive.cms.page.dao.PageDao;
import com.olive.cms.page.model.Page;
import com.olive.cms.page.section.dao.PageSectionTypeDao;
import com.olive.cms.page.section.model.PageSectionType;
import com.olive.cms.page.template.dao.PageTemplateDao;
import com.olive.cms.page.template.dao.TemplateDao;
import com.olive.cms.page.template.dto.PageTemplateDto;
import com.olive.cms.page.template.model.PageTemplate;
import com.olive.cms.page.template.model.PageTemplatePK;
import com.olive.cms.page.template.model.Template;
import com.olive.cms.page.template.service.PageTemplateAssembler;
import com.olive.cms.page.template.service.PageTemplateService;
import com.olive.common.exception.EnitiyAlreadyFoundException;

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
	public void create(PageTemplateDto dto) throws EnitiyAlreadyFoundException {
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
			dao.persist(pageTemplate);
		}
		else {
			throw new EnitiyAlreadyFoundException("Page template already exist.");
		}
	}
	
	@Override
	public Collection<PageTemplateDto> findByPageAndPageSectionType(final Integer pageId,final Integer pageSectionTypeId) {
		return assembler.domainsToDtos(dao.findByPageAndPageSectionType(pageId, pageSectionTypeId));
	}
	
}
