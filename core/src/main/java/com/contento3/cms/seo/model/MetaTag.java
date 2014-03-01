package com.contento3.cms.seo.model;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.cms.site.structure.model.Site;

/**
 * Meta tags for site and pages
 * @author XINEX
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name ="META_TAGS")
public class MetaTag {

	/**
	 * Primary key for Meta Tag
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "META_TAG_ID")
	private Integer metaTagId;
	
	/**
	 * Attribute of meta tag
	 */
	@Column (name = "ATTRIBUTE")
	private String attribute;
	
	/**
	 * Attribute value 
	 */
	@Column (name = "ATTRIBUTE_VALUE")
	private String attributeValue;
	
	/**
	 * Content value for attribute
	 */
	@Column (name = "ATTRIBUTE_CONTENT_VALUE")
	private String attributeContent;
	
	/**
	 * Level of meta tag
	 */
	@Column (name = "LEVEL")
	private String level;
	
	/**
	 *Is associated to Meta tag
	 */
	@Column (name = "ASSOCIATED_ID")
	private Integer associatedId;
	
	/**
	 * Accociated site to Meta tag
	 */
	@OneToOne
	@JoinColumn (name = "SITE_ID")
	private Site site;

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
	
	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}
	
}
