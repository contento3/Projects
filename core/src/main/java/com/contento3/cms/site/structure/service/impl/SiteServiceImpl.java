package com.contento3.cms.site.structure.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.layout.dao.PageLayoutDao;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.cms.site.structure.service.SiteService;

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
		Validate.notNull(siteAssembler,"siteAssembler cannot be null");
		Validate.notNull(siteDao,"siteAssembler cannot be null");
		Validate.notNull(pageLayoutDao,"siteAssembler cannot be null");
		
		this.siteDao = siteDao;
		this.siteAssembler = siteAssembler;
		this.pageLayoutDao = pageLayoutDao;
	}
	@RequiresPermissions("SITE:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(SiteDto siteDto) {
		Validate.notNull(siteDto,"siteDto cannot be null");
		
		Collection <PageLayout> defaultPageLayout = pageLayoutDao.findPageLayoutByAccountAndLayoutType(siteDto.getAccountDto().getAccountId(), 2);
		PageLayout pageLayout = defaultPageLayout.iterator().next();
		siteDto.setDefaultLayoutId(pageLayout.getId());
		Site site = siteAssembler.dtoToDomain(siteDto);
		return siteDao.persist(site);
	}
	@RequiresPermissions("SITE:EDIT")
	@Transactional(readOnly = false)
	@Override
	public SiteDto update(SiteDto siteDto){
		Validate.notNull(siteDto,"siteDto cannot be null");
		Site site = siteAssembler.dtoToDomain(siteDto);
		siteDao.update(site);
		return siteAssembler.domainToDto(site);
	}
	
	@RequiresPermissions("SITE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteById(Integer siteId){
		Validate.notNull(siteId,"siteId cannot be null");
		return siteAssembler.domainToDto(siteDao.findById(siteId));
	}

	@RequiresPermissions("SITE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDto findSiteByDomain(String domain){
		Validate.notNull(domain,"domain cannot be null");
		return siteAssembler.domainToDto(siteDao.findByDomain(domain));
	}
	
	@RequiresPermissions("SITE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<SiteDto> findSitesByAccountId(Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return siteAssembler.domainsToDtos(siteDao.findByAccount(accountId));
	}
	@RequiresPermissions("SITE:DELETE")
	@Override
	public void delete(SiteDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}

}