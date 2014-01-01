package com.contento3.cms.site.structure.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.account.model.Account;
import com.contento3.cms.site.structure.domain.model.SiteDomain;

/**
 * A model class that represents a site entity
 * @author Hammad Afridi
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
	 * layout for site
	 */
	@Column(name="DEFAULT_LAYOUT_ID")
	private Integer defaultLayoutId;

	/**
	 * layout for site
	 */
	@Column(name="DEFAULT_PAGE_ID")
	private Integer defaultPageId;

	/**
	 * siteDomains which site contains
	 * @return
	 */
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="SITE_ID",nullable=false)
	private Collection<SiteDomain> siteDomain;
	
	/**
	 * laguage for the site
	 */
	@Column(name = "LANGUAGE")
	private String language;
	


	public String getLanguage() {
		return language;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public Collection<SiteDomain> getSiteDomain() {
		return siteDomain;
	}

	public void setSiteDomain(final Collection<SiteDomain> siteDomain) {
		this.siteDomain = siteDomain;
	}

	/**
	 * Returns LayoutId
	 * @return Integer
	 */
	
	public Integer getDefaultLayoutId() {
		return defaultLayoutId;
	}

	public void setDefaultLayoutId(final Integer defaultLayoutId) {
		this.defaultLayoutId = defaultLayoutId;
	}

	/**
	 * Returns LayoutId
	 * @return Integer
	 */
	
	public Integer getDefaultPageId() {
		return defaultPageId;
	}

	public void setDefaultPageId(final Integer defaultPageId) {
		this.defaultPageId = defaultPageId;
	}

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