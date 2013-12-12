package com.contento3.cms.page.dto;

import java.util.Collection;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.dto.Dto;

public class PageDto extends Dto {

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
	
	/**
	 * categories to which this page is associated
	 */
	private Collection<CategoryDto> categories;


	public Collection<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(Collection<CategoryDto> categories) {
		this.categories = categories;
	}

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



    @Override
	public Integer getId() {
		return pageId;
	}

	@Override
	public String getName() {
		return title;
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
