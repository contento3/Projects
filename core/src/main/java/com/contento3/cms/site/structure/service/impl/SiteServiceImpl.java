package com.contento3.cms.site.structure.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.cms.site.structure.service.SiteService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SiteServiceImpl implements SiteService {

	private final SiteDAO siteDao;
	private SiteAssembler siteAssembler;
	
	public SiteServiceImpl(final SiteAssembler siteAssembler,final SiteDAO siteDao){
		this.siteDao = siteDao;
		this.siteAssembler = siteAssembler;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(SiteDto siteDto){
		siteDao.persist(siteAssembler.dtoToDomain(siteDto));
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(SiteDto siteDto){
		siteDao.update(siteAssembler.dtoToDomain(siteDto));
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteById(Integer siteId){
		return siteAssembler.domainToDto(siteDao.findById(siteId));
	}

	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteByDomain(String domain){
		return siteAssembler.domainToDto(siteDao.findByDomain(domain));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<SiteDto> findSitesByAccountId(Integer accountId) {
		return siteAssembler.domainsToDtos(siteDao.findByAccount(accountId));
	}
	
	@Override
	public void create(Object type) {
		// TODO Auto-generated method stub
		
	}

}
