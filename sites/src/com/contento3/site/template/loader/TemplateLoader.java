package com.contento3.site.template.loader;

import com.contento3.site.template.dto.TemplateContentDto;

public interface TemplateLoader {

	TemplateContentDto load (String resourceName,Integer siteId);
	
	TemplateContentDto loadErrorTemplate (String errorType,Integer siteId);
}
