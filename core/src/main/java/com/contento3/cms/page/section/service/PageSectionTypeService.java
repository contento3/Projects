package com.contento3.cms.page.section.service;

import com.contento3.cms.page.section.dto.PageSectionTypeDto;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.common.service.Service;

public interface PageSectionTypeService extends Service<PageSectionTypeDto>  {

	PageSectionTypeDto findByName(PageSectionTypeEnum pageSectionTypeName);

	PageSectionTypeDto findById(Integer id);
}
