package com.contento3.cms.page.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.cms.site.structure.model.Site;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PAGE")
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * Page visibility in navigation
	 */
	@Column (name = "IS_NAVIGABLE")
	private Integer isNavigable;

	/**
	 * parent page if any for this page
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Page parent;

	/**
	 * Child page for this page.
	 */
	@OneToMany(mappedBy="parent")
	private Collection<TemplateDirectory> childPages;

	/**
	 * Returns the Collection of child pages.
	 * @return
	 */
	public Collection<TemplateDirectory> getChildPages() {
		return childPages;
	}

	/**
	 * Sets the childPage Collection
	 * @param childPages
	 */
	public void setChildPages(final Collection<TemplateDirectory> childPages) {
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
	
	/**
	 * Categories for this pages.
	 */
	@ManyToMany
	@JoinTable(name="PAGE_CATEGORY_ASSOCIATION",
		joinColumns={@JoinColumn(name="PAGE_ID",unique=true)},
		inverseJoinColumns={@JoinColumn(name="CATEGORY_ID")})
	private Collection<Category> categories;

	/**
	 * Returns the collection of categories for this page.
	 * @return Collection
	 */
	public Collection<Category> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories for the page
	 * @param categories
	 */
	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	public Integer getIsNavigable() {
		return isNavigable;
	}

	public void setIsNavigable(final Integer isNavigable) {
		this.isNavigable = isNavigable;
	}
	/**
	 * Returns the title of this page.
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of this page
	 * @param title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
	
	/**
	 * Sets the uri of this page.
	 * @param uri
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}
	
	/**
	 * Returns the uri of this page.
	 * @return uri 
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * Returns the id of this page.
	 * @return
	 */
	public Integer getPageId() {
		return pageId;
	}
	
	/**
	 * Sets the id of this page.
	 * @param pageId
	 */
	public void setPageId(final Integer pageId) {
		this.pageId = pageId;
	}
	
	/**
	 * Returns the parent of this page.
	 * @return Page page of a site
	 */
	public Page getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent page of this page.
	 * @param parent
	 */
	public void setParent(final Page parent) {
		this.parent = parent;
	}
	
	/**
	 * Returns the site of to which this site belongs to.
	 * @return
	 */
	public Site getSite() {
		return site;
	}
	
	/**
	 * Sets the site of this page.
	 * @param site
	 */
	public void setSite(final Site site) {
		this.site = site;
	}

	/**
	 * Return the layout of this page
	 * @return PageLayout
	 */
	public PageLayout getPageLayout() {
		return pageLayout;
	}

	/**
	 * Sets the {@link PageLayout} of this page
	 * @param pageLayout
	 */
	public void setPageLayout(final PageLayout pageLayout) {
		this.pageLayout = pageLayout;
	}
	
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return new HashCodeBuilder(23,31)
                  .append(this.pageId)
                  .append(this.title)
                  .append(this.uri)
                  .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
      boolean isEqual;
      
      if(obj == null) {
        isEqual = false;
      } else if(this == obj) {
    	  isEqual = true;
      } else if(obj instanceof Page) {
    	  final Page page = (Page)obj;
    	  isEqual = true;//PTVHelper.equalsSafe(this.id, log.id);
      } else {
        isEqual = false;
      }
      return isEqual;
    }


}
