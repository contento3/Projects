package com.contento3.site.template.loader;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.site.template.dto.TemplateContentDto;

public interface TemplateLoader {

	TemplateContentDto load (String resourceName,Integer siteId,PageDto pageDto);
	
	TemplateContentDto loadErrorTemplate (String errorType,String resourceName,Integer siteId);
	
	TemplateContentDto loadById(Integer templateId);
}
