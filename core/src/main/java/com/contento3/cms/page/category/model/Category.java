package com.contento3.cms.page.category.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_ID")
	private Integer categoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "PARENT_CATEGORY_ID")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private Collection<Category> child; 
	

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	public 	Collection<Category> getChild() {
		return child;
	}

	public void setChild(final 	Collection<Category> child) {
		this.child = child;
	}
}
