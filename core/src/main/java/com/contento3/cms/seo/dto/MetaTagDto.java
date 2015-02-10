package com.contento3.cms.seo.dto;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.contento3.cms.page.model.Page;
import com.contento3.cms.seo.model.MetaTagLevelEnum;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.dto.Dto;

public class MetaTagDto extends Dto {

	/**
	 * Primary key for Meta Tag
	 */
	private Integer metaTagId;
	
	/**
	 * Attribute of meta tag
	 */
	private String attribute;
	
	/**
	 * Attribute value 
	 */
	private String attributeValue;
	
	/**
	 * Content value for attribute
	 */
	private String attributeContent;
	
	/**
	 * Level of meta tag
	 */
	private MetaTagLevelEnum level;
	
	/**
	 *Is associated to Meta tag
	 */
	private Integer associatedId;
	
	/**
	 * Accociated site to Meta tag
	 */
	private SiteDto site;
	
	
	public Integer getMetaTagId() {
		return metaTagId;
	}

	public void setMetaTagId(final Integer metaTagId) {
		this.metaTagId = metaTagId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(final String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeContent() {
		return attributeContent;
	}

	public void setAttributeContent(final String attributeContent) {
		this.attributeContent = attributeContent;
	}

	public MetaTagLevelEnum getLevel() {
		return level;
	}

	public void setLevel(final MetaTagLevelEnum level) {
		this.level = level;
	}

	public Integer getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(final Integer associatedId) {
		this.associatedId = associatedId;
	}

	public SiteDto getSite() {
		return site;
	}

	public void setSite(final SiteDto site) {
		this.site = site;
	}

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return new HashCodeBuilder(23,31)
                  .append(this.attributeValue)
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
      } else if(obj instanceof MetaTagDto) {
    	  final MetaTagDto metaTag = (MetaTagDto)obj;
    	  if (this.getAttributeValue().equals(((MetaTagDto) obj).getAttributeValue()))
    		  isEqual = true;
    	  else
    		  isEqual = false;
      } else {
        isEqual = false;
      }
      return isEqual;
    }

}
