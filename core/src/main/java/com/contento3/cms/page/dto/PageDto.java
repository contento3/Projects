package com.contento3.cms.page.dto;

import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.site.structure.dto.SiteDto;

public class PageDto {

	/**
	 * URI for the page
	 */
	private String uri;
	
	/**
	 * Primary key for the page
	 */
	private Integer pageId;
	
	/**
	 * Page title
	 */
	private String title;
	
	/**
	 * parent page if any for this page
	 */
	private Page parent;
	
	/**
	 * layout assigned to this page
	 */
	private PageLayoutDto pageLayoutDto;

	/**
	 * site to which this page is associated
	 */
	private SiteDto site;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri() {
		return uri;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	public Page getParent() {
		return parent;
	}
	public void setParent(final Page parent) {
		this.parent = parent;
	}
	public SiteDto getSite() {
		return site;
	}
	public void setSite(final SiteDto site) {
		this.site = site;
	}
	public PageLayoutDto getPageLayoutDto() {
		return pageLayoutDto;
	}
	public void setPageLayoutDto(final PageLayoutDto pageLayoutDto) {
		this.pageLayoutDto = pageLayoutDto;
	}

}
