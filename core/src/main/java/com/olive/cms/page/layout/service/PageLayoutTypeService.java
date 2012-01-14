package com.olive.cms.page.layout.service;

import java.util.Collection;

import com.olive.cms.page.layout.dto.PageLayoutTypeDto;
import com.olive.cms.page.layout.model.PageLayoutType;
import com.olive.common.service.Service;

public interface PageLayoutTypeService extends Service <PageLayoutTypeDto>{

	Collection<PageLayoutType> findAllPageLayoutType();
	
	PageLayoutTypeDto findByName(String name);

}
