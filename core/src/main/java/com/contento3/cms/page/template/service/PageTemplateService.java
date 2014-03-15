package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.model.PageTemplatePK;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.StorableService;

public interface PageTemplateService extends StorableService<PageTemplateDto> {

	Collection <PageTemplateDto> findByPageAndPageSectionType(Integer pageId, Integer pageSectionTypeId);

	Collection <PageTemplateDto> findByPageAndPageSectionType(Integer pageId, PageSectionTypeEnum pageSectionTypeId);

	Collection <PageTemplateDto> findByPageId(Integer pageId);

	Collection <PageTemplateDto> findByTemplateId(Integer templateId);

	PageTemplatePK create(PageTemplateDto dto) throws EntityAlreadyFoundException;
	
	/**
	 * delete page template
	 * @param dto
	 */
	public void delete(final PageTemplateDto dto);

}
