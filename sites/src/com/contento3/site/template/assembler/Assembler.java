package com.contento3.site.template.assembler;

import com.contento3.site.template.dto.TemplateContentDto;
import com.olive.cms.page.exception.PageNotFoundException;

public interface Assembler {
	TemplateContentDto assemble(Integer siteId,String path) throws PageNotFoundException;
}
