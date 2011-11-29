package com.olive.cms.page.section.service;

import com.olive.cms.page.section.dto.PageSectionTypeDto;
import com.olive.cms.page.section.model.PageSectionTypeEnum;
import com.olive.common.service.Service;

public interface PageSectionTypeService extends Service<PageSectionTypeDto>  {

	PageSectionTypeDto findByName(PageSectionTypeEnum pageSectionTypeName);

	PageSectionTypeDto findById(Integer id);
}
