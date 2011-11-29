package com.olive.cms.site.structure.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.olive.account.model.Account;

/**
 * A model class that represents a site entity
 * @author Hammad Afridi
 *
 */
@Entity
@Table(name = "SITE")
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id used to identify a site
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "site_id")
	private Integer siteId;
	
	/**
	 * Url/domain for this site
	 */
	@Column(name = "url")
	private String url;
	
	/**
	 * name of the site (not the domain) something like Yahoo,Facebook,CricInfo etc
	 */
	@Column(name = "site_name")
	private String siteName;
	
	/**
	 * account the site registered to.
	 * @return
	 */
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	public Integer getSiteId() {
		return siteId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(final Account account) {
		this.account = account;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
