package com.olive.cms.site.structure.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.olive.account.service.AccountService;
import com.olive.cms.site.structure.dao.SiteDAO;
import com.olive.cms.site.structure.dto.SiteDto;
import com.olive.cms.site.structure.model.Site;
import com.olive.cms.site.structure.service.SiteService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SiteServiceImpl implements SiteService {

	private final SiteDAO siteDao;
	private final AccountService accountService;
	
	public SiteServiceImpl(final AccountService accountService,final SiteDAO siteDao){
		this.siteDao = siteDao;
		this.accountService = accountService;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(SiteDto siteDto){
		siteDao.persist(dtoToDomain(siteDto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteById(Integer siteId){
		return domainToDto(siteDao.findById(siteId));
	}

	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteByDomain(String domain){
		return domainToDto(siteDao.findByDomain(domain));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<SiteDto> findSiteByAccountId(Integer accountId) {
		return domainsToDtos(siteDao.findByAccount(accountId));
	}
	
	public Site dtoToDomain(final SiteDto dto){
		Site site = new Site();
		site.setSiteId(dto.getSiteId());
		site.setSiteName(dto.getSiteName());
		site.setUrl(dto.getUrl());
		site.setAccount(accountService.dtoToDomain(dto.getAccountDto()));
		return site;
	}

	public SiteDto domainToDto(final Site domain){
		SiteDto dto = new SiteDto();
		dto.setSiteName(domain.getSiteName());
		dto.setUrl(domain.getUrl());
		dto.setSiteId(domain.getSiteId());
		dto.setAccountDto(accountService.domainToDto(domain.getAccount()));
		return dto;
	}

	public Collection<SiteDto> domainsToDtos(final Collection<Site> domains){
		Collection<SiteDto> dtos = new ArrayList<SiteDto>();
		for (Site site : domains){
			dtos.add(domainToDto(site));
		}
		return dtos;
	}

	@Override
	public void create(Object type) {
		// TODO Auto-generated method stub
		
	}

}
