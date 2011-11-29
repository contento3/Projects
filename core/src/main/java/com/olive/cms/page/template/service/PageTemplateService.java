package com.olive.cms.page.template.service;

import java.util.Collection;

import com.olive.cms.page.template.dto.PageTemplateDto;
import com.olive.common.service.Service;

public interface PageTemplateService extends Service<PageTemplateDto> {

	Collection <PageTemplateDto> findByPageAndPageSectionType(Integer pageId, Integer pageSectionTypeId);

}
