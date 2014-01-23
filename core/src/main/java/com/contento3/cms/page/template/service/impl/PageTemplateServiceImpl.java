package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
		Validate.notNull(dao,"dao cannot be null");
		Validate.notNull(templateDao,"templateDao cannot be null");
		Validate.notNull(sectionTypeDao,"sectionTypeDao cannot be null");
		Validate.notNull(pageDao,"pageDao cannot be null");
		Validate.notNull(assembler,"assembler cannot be null");
		
		this.dao = dao;
		this.assembler = assembler;
		this.templateDao = templateDao;
		this.pageDao = pageDao;
		this.sectionTypeDao = sectionTypeDao;
	}
	@RequiresPermissions("PAGE_TEMPLATE_ASSOCIATION:ADD")
	@Override
	public PageTemplatePK create(PageTemplateDto dto) throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		
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
	@RequiresPermissions("PAGE_TEMPLATE_ASSOCIATION:VIEW")
	@Override
	public Collection<PageTemplateDto> findByPageAndPageSectionType(final Integer pageId,final Integer pageSectionTypeId) {
		Validate.notNull(pageId,"pageId cannot be null");
		Validate.notNull(pageSectionTypeId,"pageSectionTypeId cannot be null");
		return assembler.domainsToDtos(dao.findByPageAndPageSectionType(pageId, pageSectionTypeId));
	}
	@RequiresPermissions("PAGE_TEMPLATE_ASSOCIATION:VIEW")
	@Override
	public Collection<PageTemplateDto> findByPageId(Integer pageId) {
		Validate.notNull(pageId,"pageId cannot be null");
		return assembler.domainsToDtos(dao.findByPageId(pageId));
	}
	@RequiresPermissions("PAGE_TEMPLATE_ASSOCIATION:VIEW")
	@Override
	public Collection<PageTemplateDto> findByPageAndPageSectionType(
			Integer pageId, PageSectionTypeEnum pageSectionType) {
		Validate.notNull(pageId,"pageId cannot be null");
		Validate.notNull(pageSectionType,"pageSectionType cannot be null");
		return assembler.domainsToDtos(dao.findByPageAndPageSectionType(pageId, pageSectionType));
	}
	@RequiresPermissions("PAGE_TEMPLATE_ASSOCIATION:DELETE")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(final PageTemplateDto dto) {
		Validate.notNull(dto,"dto cannot be null");
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
		dao.delete(pageTemplatePresent);
	}
	
	

}
