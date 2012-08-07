package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.model.PageTemplatePK;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.Service;

public interface PageTemplateService extends Service<PageTemplateDto> {

	Collection <PageTemplateDto> findByPageAndPageSectionType(Integer pageId, Integer pageSectionTypeId);

	Collection <PageTemplateDto> findByPageAndPageSectionType(Integer pageId, PageSectionTypeEnum pageSectionTypeId);

	Collection <PageTemplateDto> findByPageId(Integer pageId);

	PageTemplatePK create(PageTemplateDto dto) throws EntityAlreadyFoundException;

}
