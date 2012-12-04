package com.contento3.cms.article.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.contento3.account.model.Account;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.site.structure.model.Site;


@Entity
@Table(name = "ARTICLE")
public class Article implements Serializable  {

	private static final long serialVersionUID = 1L;
	/**
	 * Primary key for article
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "ARTICLE_ID")

	private Integer articleId;

	/**
	 * unique random UUID
	 */
	@Column(columnDefinition="TEXT", length = 100, name = "ARTICLE_UUID",
			unique=true, nullable=false)
    private String uuid = UUID.randomUUID().toString();

	/**
	 * Head section of article
	 */
	@Column (name = "HEAD")
	private String head;
	/**
	 * Teaser section of article
	 */
	@Column (name = "TEASER")
	private String teaser;

	/**
	 * Body section of article
	 */
	@Column (name = "BODY")
	private String body;

	/**
	 * Article created date
	 */
	@Column (name = "DATE_CREATED")
	private Date dateCreated;

	/**
	 * Article posted date
	 */
	@Column (name = "DATE_POSTED")
	private Date datePosted;

	/**
	 * Article last updated date
	 */
	@Column (name = "LAST_UPDATED")
	private Date lastUpdated;
	/**
	 * Expiry date for article
	 */
	@Column (name = "EXPIRY_DATE")
	private Date expiryDate;
	/**
	 * Article visibility
	 */
	@Column (name = "IS_VISIBLE")
	private Integer isVisible;


	/**
	 * articles which are associated to site
	 */
	@ManyToMany //uni directional
	@JoinTable(name="SITE_ARTICLE",
	joinColumns={@JoinColumn(name="ARTICLE_ID")},
	inverseJoinColumns={@JoinColumn(name="SITE_ID")})
	private Collection<Site> site;

	/**
	 * account on which articles are created
	 */
	@ManyToOne //uni directional many-to-one(foreign-key)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	/**
	 * Related articles which are associate to article
	 */
	@OneToMany(cascade=CascadeType.ALL,mappedBy="article")
	private Collection<RelatedArticle> relatedArticles;

	/**
	 * Categories for this pages.
	 */
	@ManyToMany
	@JoinTable(name="ARTICLE_CATEGORY_ASSOCIATION",
		joinColumns={@JoinColumn(name="ARTICLE_ID",unique=true)},
		inverseJoinColumns={@JoinColumn(name="CATEGORY_ID")})
	private Collection<Category> categories;


	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(final Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Collection<RelatedArticle> getRelatedArticles() {
		return relatedArticles;
	}

	public void setRelatedArticles(final Collection<RelatedArticle> relatedArticles) {
		this.relatedArticles = relatedArticles;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public Collection<Site> getSite() {
		return site;
	}

	public void setSite(final Collection<Site> site) {
		this.site = site;
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

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(final Date datePosted) {
		this.datePosted = datePosted;
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

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	public Collection<Category> getCategories() {
		return categories;
	}
}