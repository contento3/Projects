package com.olive.cms.page.template.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.olive.cms.page.model.Page;
import com.olive.cms.page.section.model.PageSectionType;

@Embeddable
public class PageTemplatePK implements Serializable{

	private static final long serialVersionUID = 815973590085201727L;

	@ManyToOne
	@JoinColumn(name = "PAGE_ID")
	private Page page;
	
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID")
	private Template template;
	
	@OneToOne
	@JoinColumn (name="PAGE_SECTION_TYPE_ID")
	private PageSectionType sectionType;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public PageSectionType getSectionType() {
		return sectionType;
	}

	public void setSectionType(PageSectionType sectionType) {
		this.sectionType = sectionType;
	}
}
