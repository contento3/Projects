package com.olive.cms.page.template.service;

import com.olive.cms.page.template.dto.TemplateTypeDto;
import com.olive.common.service.Service;

/**
 * This class is used to //TODO
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

public interface TemplateTypeService extends Service<TemplateTypeDto> {

	TemplateTypeDto findById(Integer templateTypeId);
	
}
