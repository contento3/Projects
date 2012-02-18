package com.contento3.site.page.section.resolver;

import java.util.Collection;

import com.contento3.cms.page.section.dto.PageSectionDto;
import com.contento3.cms.page.template.dto.PageTemplateDto;



/**
 * Resolves the html by adding the page's template 
 * sand layout markup
 * @author HAMMAD
 *
 */
public interface HtmlResolver {

	String resolve(StringBuffer sectionMarkUp,PageSectionDto dtos);

	String resolveBody(Collection <PageTemplateDto> templateDtos,PageSectionDto dtos);

}
