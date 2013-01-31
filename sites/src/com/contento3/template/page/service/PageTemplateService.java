package com.contento3.template.page.service;

import com.contento3.site.template.service.TemplateService;
import com.contento3.template.page.dto.PageTemplateDto;

public interface PageTemplateService extends TemplateService {

	PageTemplateDto findById(Integer id);
	
	PageTemplateDto findByPath(String path);

}
