package com.contento3.cms.page.template.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model to represent association between a page and template.
 * A page can have multiple template in a page section.
 * @author HAMMAD
 *
 */
@Entity
@Table(name="PAGE_TEMPLATE_ASSOCIATION")
public class PageTemplate implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Primary key consist of page,template and section type.
	 * This means a recored is representing a template for a 
	 * page which have a particular section type.
	 */
	@Id
	private PageTemplatePK primaryKey;
	
	/**
	 * Order of this template in the page so that the page 
	 * rendering engine would know which one to process when.
	 */
	@Column(name="TEMPLATE_ORDER")
	private Integer templateOrder;

	public PageTemplatePK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimareKey(PageTemplatePK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Integer getTemplateOrder() {
		return templateOrder;
	}

	public void setTemplateOrder(Integer templateOrder) {
		this.templateOrder = templateOrder;
	}

}
