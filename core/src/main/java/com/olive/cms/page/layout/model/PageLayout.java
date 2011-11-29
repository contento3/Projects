package com.olive.cms.page.layout.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.olive.cms.page.section.model.PageSection;

@Entity
@Table(name = "PAGE_LAYOUT")
public class PageLayout implements Serializable{

	private static final long serialVersionUID = 1431845150196595008L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAGE_LAYOUT_ID")
	private int id;	

	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "PAGE_LAYOUT_TYPE_ID")
	private PageLayoutType layoutType;

	@Column(name = "account_id")
	private Integer accountId;

	@OneToMany 
	@JoinColumn (name="PAGE_LAYOUT_ID")
	private Set<PageSection> pageSections = new HashSet<PageSection>();;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PageLayoutType getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(final PageLayoutType layoutType) {
		this.layoutType = layoutType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public Set<PageSection> getPageSections() {
		return pageSections;
	}

	public void setPageSections(Set<PageSection> pageSections) {
		this.pageSections = pageSections;
	}

}
