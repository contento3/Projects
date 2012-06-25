package com.contento3.cms.article.dto;


import java.util.Collection;
import java.util.Date;
import com.contento3.account.dto.AccountDto;
import com.contento3.cms.site.structure.dto.SiteDto;


public class ArticleDto {

	private Integer articleId;

	private String uuid;
	
	private String head;
	
	private String teaser;
	
	private String body;
	
	private Date dateCreated;
	
	private Date datePosted;
	
	private Collection<SiteDto> site;
	
	private AccountDto account;
	
	private Date lastUpdated;
	
	private Date expiryDate;
	
	
	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(AccountDto account) {
		this.account = account;
	}

	
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
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

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}
	
	public Collection<SiteDto> getSite() {
		return site;
	}

	public void setSite(Collection<SiteDto> site) {
		this.site = site;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	
}
