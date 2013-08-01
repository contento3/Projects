package com.contento3.site.template.assembler;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.site.template.dto.TemplateContentDto;

public interface Assembler {
	TemplateContentDto assemble(Integer siteId,String path) throws PageNotFoundException;

	TemplateContentDto assembleInclude(Integer siteId, String path)
			throws PageNotFoundException;
	
	/**
	 * Fetches the default template for specific type of content other than the page itself.
	 * For e.g. an article have a default global template to render the article.
	 * @param type
	 * @param id
	 * @return
	 * @throws PageNotFoundException 
	 */
	TemplateContentDto fetchDefaultTemplate (String type) throws PageNotFoundException;
}
