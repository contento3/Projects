package com.contento3.cms.seo.model;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table( name ="META_TAGS")
public class MetaTag {

	/**
	 * Primary key for SEO
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "META_TAG_ID")
	private Integer metaTagId;
	
	@Column (name = "ATTRIBUTE")
	private String attribute;
	
	@Column (name = "ATTRIBUTE_VALUE")
	private String attributeValue;
	
	@Column (name = "ATTRIBUTE_CONTENT_VALUE")
	private String attributeContent;
	
	@Column (name = "LEVEL")
	private String level;
	
	@Column (name = "ASSOCIATED_ID")
	private Integer associatedId;
	
	@Column (name = "SITE_ID")
	private Integer siteId;

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

	public String getLevel() {
		return level;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public Integer getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(final Integer associatedId) {
		this.associatedId = associatedId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(final Integer siteId) {
		this.siteId = siteId;
	}
	
}
