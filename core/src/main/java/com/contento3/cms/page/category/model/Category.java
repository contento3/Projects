package com.contento3.cms.page.category.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.contento3.account.model.Account;
import com.contento3.common.dto.Dto;


@Entity
@Table(name = "CATEGORY")
public class Category  implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Primary key for category
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_ID")
	private Integer categoryId;

	/**
	 * Name for category
	 */
	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	/**
	 * Parent category
	 */
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "PARENT_CATEGORY_ID")
	private Category parent;

	/**
	 * Child category
	 */
	@OneToMany(mappedBy = "parent")
	private Collection<Category> child; 

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;


	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}


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
		return this.child;
	}
	

	public void setChild(final 	Collection<Category> child) {
		this.child = child;
	}
}