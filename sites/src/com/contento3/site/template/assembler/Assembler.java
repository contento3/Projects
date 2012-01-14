package com.contento3.site.template.assembler;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.site.template.dto.TemplateContentDto;

public interface Assembler {
	TemplateContentDto assemble(Integer siteId,String path) throws PageNotFoundException;
}
