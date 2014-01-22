package com.contento3.cms.page.layout.service;

import java.util.Collection;

import com.contento3.cms.page.layout.dto.PageLayoutTypeDto;
import com.contento3.cms.page.layout.model.PageLayoutType;
import com.contento3.common.service.StorableService;

public interface PageLayoutTypeService extends StorableService <PageLayoutTypeDto>{

	Collection<PageLayoutType> findAllPageLayoutType();
	
	PageLayoutTypeDto findByName(String name);

}
