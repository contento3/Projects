package com.contento3.cms.site.structure.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.site.structure.domain.service.SiteDomainAssembler;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.cms.site.structure.service.SiteAssembler;

/**
 * Transform Site to SiteDto and vice versa.
 * @author HAMMAD
 *
 */
public class SiteAssemblerImpl implements SiteAssembler {

	/**
	 * Transform Account to AccountDto and vice versa.
	 */
	private AccountAssembler accountAssembler;

	private SiteDomainAssembler siteDomainAssembler;

	
	public SiteAssemblerImpl(final AccountAssembler accountAssembler,final SiteDomainAssembler siteDomainAssembler){
		this.accountAssembler = accountAssembler;
		this.siteDomainAssembler = siteDomainAssembler;
	}
	
	@Override
	public Site dtoToDomain(SiteDto dto) {
		Site site = new Site();
		site.setSiteId(dto.getSiteId());
		site.setSiteName(dto.getSiteName());
		site.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		site.setDefaultLayoutId(dto.getDefaultLayoutId());
		site.setDefaultPageId(dto.getDefaultPageId());
		
		if(null!=dto.getSiteDomainDto()){
			site.setSiteDomain(siteDomainAssembler.dtosToDomains(dto.getSiteDomainDto()));
		}
		
		site.setLanguage(dto.getLanguage());
		site.setStatus(dto.getStatus());
		return site;
	}

	@Override
	public SiteDto domainToDto(Site domain) {
		SiteDto dto = new SiteDto();
		
		if (null!=domain){
			dto.setSiteName(domain.getSiteName());
			dto.setSiteId(domain.getSiteId());
			dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));
			dto.setDefaultLayoutId(domain.getDefaultLayoutId());
			dto.setSiteDomainDto(siteDomainAssembler.domainsToDtos(domain.getSiteDomain()));
			dto.setLanguage(domain.getLanguage());
			dto.setDefaultPageId(domain.getDefaultPageId());
			dto.setStatus(domain.getStatus());
		}
		return dto;
	}

	@Override
	public Collection<SiteDto> domainsToDtos(Collection<Site> domains) {
		Collection<SiteDto> dtos = new ArrayList<SiteDto>();
		for (Site site : domains){
			dtos.add(domainToDto(site));
		}
		return dtos;
	}

	@Override
	public Collection<Site> dtosToDomains(Collection<SiteDto> dtos) {
		Collection<Site> domains = new ArrayList<Site>();
		
		if (!CollectionUtils.isEmpty(dtos)){
			for (SiteDto siteDto : dtos){
				domains.add(dtoToDomain(siteDto));
			}
		}
		return domains;
	}


}