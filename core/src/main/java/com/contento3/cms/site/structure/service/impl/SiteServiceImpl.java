package com.contento3.cms.site.structure.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.layout.dao.PageLayoutDao;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EntityAlreadyFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SiteServiceImpl implements SiteService {

	/**
	 * Site data access object 
	 */
	private final SiteDAO siteDao;
	
	/**
	 * Assembler to convert Site dto to Site domain and vice versa
	 */
	private SiteAssembler siteAssembler;
	
	/**
	 * Data access object for PageLayout
	 */
	private PageLayoutDao pageLayoutDao;
	
	public SiteServiceImpl(final SiteAssembler siteAssembler,final SiteDAO siteDao,final PageLayoutDao pageLayoutDao){
		this.siteDao = siteDao;
		this.siteAssembler = siteAssembler;
		this.pageLayoutDao = pageLayoutDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(SiteDto siteDto) {
		Collection <PageLayout> defaultPageLayout = pageLayoutDao.findPageLayoutByAccountAndLayoutType(siteDto.getAccountDto().getAccountId(), 2);
		PageLayout pageLayout = defaultPageLayout.iterator().next();
		siteDto.setDefaultLayoutId(pageLayout.getId());
		Site site = siteAssembler.dtoToDomain(siteDto);
		siteDao.persist(site);
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
	public void create(Object dto) throws EntityAlreadyFoundException {
		// TODO Auto-generated method stub
		
	}
	

}
