package com.contento3.cms.article.dto;


import java.util.Collection;
import java.util.Date;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.dto.Dto;


public class ArticleDto extends Dto {
	/**
	 * id for article
	 */
	private Integer articleId;

	/**
	 * unique random UUID
	 */
	private String uuid;

	/**
	 * Head section of article
	 */
	private String head;

	/**
	 * Teaser section of article
	 */
	private String teaser;

	/**
	 * Body section of article
	 */
	private String body;

	/**
	 * Article created date
	 */
	private Date dateCreated;

	/**
	 * Article posted date
	 */
	private Date datePosted;

	/**
	 * Article last updated date
	 */
	private Date lastUpdated;

	/**
	 * Expiry date for article
	 */
	private Date expiryDate;

	/**
	 * articles which are associated to site
	 */
	private Collection<SiteDto> site;

	/**
	 * account on which articles are created
	 */
	private AccountDto account;

	/**
	 * Associated categories for this article.
	 */
	private Collection<CategoryDto> categoryDtos;
	/**
	 * Article visibility
	 */
	private Integer isVisible;


	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(final Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(final Integer articleId) {
		this.articleId = articleId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(final AccountDto account) {
		this.account = account;
	}


	public String getHead() {
		return head;
	}

	public void setHead(final String head) {
		this.head = head;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(final String teaser) {
		this.teaser = teaser;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(final Date datePosted) {
		this.datePosted = datePosted;
	}

	public Collection<SiteDto> getSite() {
		return site;
	}

	public void setSite(final Collection<SiteDto> site) {
		this.site = site;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setCategoryDtos(final Collection<CategoryDto> categoryDtos) {
		this.categoryDtos = categoryDtos;
	}

	public Collection<CategoryDto> getCategoryDtos() {
		return categoryDtos;
	}


}