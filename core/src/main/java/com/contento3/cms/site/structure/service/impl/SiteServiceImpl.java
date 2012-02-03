package com.contento3.cms.site.structure.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.account.service.AccountAssembler;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.cms.site.structure.service.SiteService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SiteServiceImpl implements SiteService {

	private final SiteDAO siteDao;
	private final AccountAssembler accountAsembler;
	
	public SiteServiceImpl(final AccountAssembler accountAsembler,final SiteDAO siteDao){
		this.siteDao = siteDao;
		this.accountAsembler = accountAsembler;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(SiteDto siteDto){
		siteDao.persist(dtoToDomain(siteDto));
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(SiteDto siteDto){
		siteDao.update(dtoToDomain(siteDto));
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
		site.setAccount(accountAsembler.dtoToDomain(dto.getAccountDto()));
		site.setDafaultLayoutId(dto.getDefaultLayoutId());
		return site;
	}

	public SiteDto domainToDto(final Site domain){
		SiteDto dto = new SiteDto();
		dto.setSiteName(domain.getSiteName());
		dto.setUrl(domain.getUrl());
		dto.setSiteId(domain.getSiteId());
		dto.setAccountDto(accountAsembler.domainToDto(domain.getAccount()));
		dto.setDefaultLayoutId(domain.getDafaultLayoutId());
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
