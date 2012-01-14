package com.contento3.cms.page.section.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contento3.cms.page.layout.model.PageLayout;

@Entity
@Table(name = "PAGE_SECTION")
public class PageSection {

	@Column(name = "name")
	private String name;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAGE_SECTION_ID")
	private Integer id;
	
	@OneToOne
	@JoinColumn (name="PAGE_LAYOUT_ID")
	private PageLayout pageLayout;

	@Column(name = "TEMPLATE_MARKUP")
	private String templateMarkup;
	
	@ManyToOne
	@JoinColumn (name="PAGE_SECTION_TYPE_ID")
	private PageSectionType sectionType;
	
	@Column(name = "DESCRIPTION")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PageLayout getPageLayout() {
		return pageLayout;
	}

	public void setPageLayout(PageLayout pageLayout) {
		this.pageLayout = pageLayout;
	}

	public String getTemplateMarkup() {
		return templateMarkup;
	}

	public void setTemplateMarkup(String templateMarkup) {
		this.templateMarkup = templateMarkup;
	}

	public PageSectionType getSectionType() {
		return sectionType;
	}

	public void setSectionType(PageSectionType sectionType) {
		this.sectionType = sectionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
