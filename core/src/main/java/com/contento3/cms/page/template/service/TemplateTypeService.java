package com.contento3.cms.page.template.service;

import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.common.service.Service;

/**
 * This class is used to //TODO
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

public interface TemplateTypeService extends Service<TemplateTypeDto> {

	TemplateTypeDto findById(Integer templateTypeId);
	
}
