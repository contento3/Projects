package com.contento3.cms.site.structure.dto;

import java.io.Serializable;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.dam.image.dto.ImageDto;

public class SiteDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id used to identify a site
	 */
	private Integer siteId;
	
	/**
	 * name of the site (not the domain) something like Yahoo,Facebook,CricInfo etc
	 */
	private String siteName;
	
	/**
	 * account to which this 
	 * site is associated to
	 */
	private AccountDto accountDto;
	
	/**
	 * layout for site
	 */
	private Integer defaultLayoutId;
	
	/**
	 * sitedomains for site
	 */
	private Collection<SiteDomainDto> siteDomainDto;
	
	
	/**
	 *langugae for the site
	 */
	private String language;
	
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Collection<SiteDomainDto> getSiteDomainDto() {
		return siteDomainDto;
	}
	public void setSiteDomainDto(Collection<SiteDomainDto> siteDomainDto) {
		this.siteDomainDto = siteDomainDto;
	}
	public Integer getDefaultLayoutId() {
		return defaultLayoutId;
	}
	public void setDefaultLayoutId(Integer defaultLayoutId) {
		this.defaultLayoutId = defaultLayoutId;
	}
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
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
}
