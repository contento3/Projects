package com.contento3.template.article.dto;

import java.util.Date;

import com.contento3.template.article.service.impl.ArticleImageTemplateDto;

/**
 * Article Dto that is used in freemarker templates
 * @author HAMMAD
 *
 */
public class ArticleTemplateDto {

	/**
	 * id for article
	 */
	private Integer articleId;

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
	 * Object that holds the associated images for an article.
	 */
	private ArticleImageTemplateDto articleImageTemplateDto;
	
	public void setArticleId(final Integer articleId) {
		this.articleId = articleId;
	}

	public void setHead(final String head) {
		this.head = head;
	}

	public void setTeaser(final String teaser) {
		this.teaser = teaser;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDatePosted(final Date datePosted) {
		this.datePosted = datePosted;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public String getHead() {
		return head;
	}

	public String getTeaser() {
		return teaser;
	}

	public String getBody() {
		return body;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public ArticleImageTemplateDto getArticleImageTemplateDto() {
		return articleImageTemplateDto;
	}

}
