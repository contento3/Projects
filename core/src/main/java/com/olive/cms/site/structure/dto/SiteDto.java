package com.olive.cms.site.structure.dto;

import com.olive.account.dto.AccountDto;

public class SiteDto {

	private static final long serialVersionUID = 1L;

	/**
	 * Id used to identify a site
	 */
	private Integer siteId;
	
	/**
	 * Url/domain for this site
	 */
	private String url;
	
	/**
	 * name of the site (not the domain) something like Yahoo,Facebook,CricInfo etc
	 */
	private String siteName;
	
	/**
	 * account to which this 
	 * site is associated to
	 */
	private AccountDto accountDto;
	
	public AccountDto getAccountDto() {
		return accountDto;
	}
	public void setAccountDto(final AccountDto accountDto) {
		this.accountDto = accountDto;
	}
	
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(final Integer siteId) {
		this.siteId = siteId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(final String url) {
		this.url = url;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
}
