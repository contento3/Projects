package com.contento3.cms.page.section.service;

import com.contento3.cms.page.section.dto.PageSectionTypeDto;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.common.service.StorableService;

public interface PageSectionTypeService extends StorableService<PageSectionTypeDto>  {

	PageSectionTypeDto findByName(PageSectionTypeEnum pageSectionTypeName);

	PageSectionTypeDto findById(Integer id);
}
