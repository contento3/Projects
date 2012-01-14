package com.contento3.cms.page.section.dto;


public class PageSectionDto implements Comparable<PageSectionDto>{

	private String name;

	private Integer id;
	
	private String templateMarkup;
	
	private PageSectionTypeDto sectionTypeDto;
	
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

	public String getTemplateMarkup() {
		return templateMarkup;
	}

	public void setTemplateMarkup(String templateMarkup) {
		this.templateMarkup = templateMarkup;
	}

	public PageSectionTypeDto getSectionTypeDto() {
		return sectionTypeDto;
	}

	public void setSectionTypeDto(PageSectionTypeDto sectionTypeDto) {
		this.sectionTypeDto = sectionTypeDto;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int compareTo(PageSectionDto pageSection) {
	    return id.compareTo(pageSection.getId());
	}
}
