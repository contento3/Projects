package com.olive.cms.content.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
