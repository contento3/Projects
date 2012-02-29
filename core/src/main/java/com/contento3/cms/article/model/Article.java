package com.contento3.cms.article.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.cms.site.structure.model.Site;

@Entity
@Table(name = "ARTICLE")
public class Article {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "ARTICLE_UUID")
	//TODO Change to string based custom uuid genereated.
	private Integer uuid;

	@Column (name = "HEAD")
	private String head;
	
	@Column (name = "TEASER")
	private String teaser;
	
	@Column (name = "BODY")
	private String body;
	
	@Column (name = "DATE_CREATED")
	private Date dateCreated;
	
	@Column (name = "DATE_POSTED")
	private Date datePosted;
	
	/**
	 * articles which are associated to site
	 */
	@ManyToMany
	@JoinTable(name="SITE_ARTICLE",
	joinColumns={@JoinColumn(name="ARTICLE_ID")},
	inverseJoinColumns={@JoinColumn(name="SITE_ID")})
	private Collection<Site> site;

	public Collection<Site> getSite() {
		return site;
	}

	public void setSite(Collection<Site> site) {
		this.site = site;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
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
	
}
