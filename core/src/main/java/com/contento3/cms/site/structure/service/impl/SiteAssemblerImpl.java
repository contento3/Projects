package com.contento3.cms.site.structure.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.service.AccountAssembler;
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
	
	public SiteAssemblerImpl(final AccountAssembler accountAssembler){
		this.accountAssembler = accountAssembler;
	}
	
	@Override
	public Site dtoToDomain(SiteDto dto) {
		Site site = new Site();
		site.setSiteId(dto.getSiteId());
		site.setSiteName(dto.getSiteName());
		site.setUrl(dto.getUrl());
		site.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		site.setDafaultLayoutId(dto.getDefaultLayoutId());
		return site;
	}

	@Override
	public SiteDto domainToDto(Site domain) {
		SiteDto dto = new SiteDto();
		dto.setSiteName(domain.getSiteName());
		dto.setUrl(domain.getUrl());
		dto.setSiteId(domain.getSiteId());
		dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));
		dto.setDefaultLayoutId(domain.getDafaultLayoutId());
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
		for (SiteDto siteDto : dtos){
			domains.add(dtoToDomain(siteDto));
		}
		return domains;
	}


}
