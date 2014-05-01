package com.contento3.site.template.dto;

import com.contento3.cms.page.dto.PageDto;

/**
 * Entity that holds the tempalte text.
 * @author hamakhaa
 *
 */
public class TemplateContentDto {

	private String content;

	/**
	 * Current page for this template
	 */
	private PageDto page;
	
	public void setContent(final String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public PageDto getPage() {
		return page;
	}

	public void setPage(final PageDto page) {
		this.page = page;
	}
	
	
}
