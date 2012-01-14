package com.olive.cms.page.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.olive.cms.page.layout.model.PageLayout;
import com.olive.cms.page.template.model.TemplateDirectory;
import com.olive.cms.site.structure.model.Site;

@Entity
@Table(name = "PAGE")
public class Page {

	/**
	 * URI for the page
	 */
	@Column(name = "PAGE_URI")
	private String uri;
	
	/**
	 * Primary key for the page
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAGE_ID")
	private Integer pageId;
	
	/**
	 * Page title
	 */
	@Column(name = "PAGE_TITLE")
	private String title;
	
	/**
	 * parent page if any for this page
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Page parent;

	@OneToMany(mappedBy="parent")
	private Collection<TemplateDirectory> childPages;


	public Collection<TemplateDirectory> getChildPages() {
		return childPages;
	}

	public void setChildPages(Collection<TemplateDirectory> childPages) {
		this.childPages = childPages;
	}

	/**
	 * site to which this page is associated
	 */
	@ManyToOne
	@JoinColumn(name = "SITE_ID")
	private Site site;
	
	/**
	 * layout assigned to this page
	 */
	@OneToOne
	@JoinColumn(name = "PAGE_LAYOUT_ID")
	private PageLayout pageLayout;
	
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
	public Site getSite() {
		return site;
	}
	public void setSite(final Site site) {
		this.site = site;
	}

	public PageLayout getPageLayout() {
		return pageLayout;
	}

	public void setPageLayout(PageLayout pageLayout) {
		this.pageLayout = pageLayout;
	}
}
