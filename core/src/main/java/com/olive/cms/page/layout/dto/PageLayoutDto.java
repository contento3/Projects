package com.olive.cms.page.layout.dto;

import java.util.Collection;

import com.olive.cms.page.section.dto.PageSectionDto;

public class PageLayoutDto {
	
	private int id;	
	private String name;
	private PageLayoutTypeDto layoutType;
	private Integer accountId;
	private Collection <PageSectionDto> pageSectionDtos;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public PageLayoutTypeDto getLayoutTypeDto() {
		return layoutType;
	}

	public void setLayoutTypeDto(final PageLayoutTypeDto layoutType) {
		this.layoutType = layoutType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public Collection<PageSectionDto> getPageSections() {
		return pageSectionDtos;
	}

	public void setPageSections(Collection<PageSectionDto> pageSectionDtos) {
		this.pageSectionDtos = pageSectionDtos;
	}
}
