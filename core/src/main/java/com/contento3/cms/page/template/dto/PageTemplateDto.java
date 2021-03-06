package com.contento3.cms.page.template.dto;

import com.contento3.common.dto.Dto;


public class PageTemplateDto extends Dto implements Comparable<PageTemplateDto> {
	private Integer pageId;
	
	private Integer templateId;
	
	private Integer sectionTypeId;
	
	private Integer order;

	private String templateName;
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(final Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(final Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getSectionTypeId() {
		return sectionTypeId;
	}

	public void setSectionTypeId(final Integer sectionTypeId) {
		this.sectionTypeId = sectionTypeId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(final Integer order) {
		this.order = order;
	}

	public int compareTo(final PageTemplateDto pageSection) {
	    return order.compareTo(pageSection.getOrder());
	}

}
