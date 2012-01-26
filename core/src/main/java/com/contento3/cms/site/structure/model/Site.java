package com.contento3.cms.site.structure.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;

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

	/**
	 * Returns the siteId
	 * @return Integer
	 */
	public Integer getSiteId() {
		return siteId;
	}
	
	/**
	 * Returns the account of this site
	 * @return Account
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * Sets the account for this site
	 * @param account Represents the account for this site
	 */
	public void setAccount(final Account account) {
		this.account = account;
	}
	
	/**
	 * Sets the siteId
	 * @param siteId id of this site
	 */
	public void setSiteId(final Integer siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * Returns the url
	 * @return String uri 
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url
	 * @param url 
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
	
	/**
	 * Returns the site name
	 * @return String 
	 */
	public String getSiteName() {
		return siteName;
	}
	
	/**
	 * Sets the siteName
	 * @param siteName
	 */
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
}
