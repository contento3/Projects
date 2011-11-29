package com.olive.cms.page.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.olive.cms.page.dao.PageDao;
import com.olive.cms.page.dto.PageDto;
import com.olive.cms.page.layout.service.PageLayoutAssembler;
import com.olive.cms.page.model.Page;
import com.olive.cms.page.service.PageService;
import com.olive.cms.site.structure.service.SiteService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageServiceImpl implements PageService {

	private PageDao pageDao;
	private SiteService siteService;
	private PageLayoutAssembler pageLayoutAssembler;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
    public Integer create(final PageDto pageDto){
    	return pageDao.persist(dtoToDomain(pageDto));
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto findPageWithLayout(final Integer pageId){
    	Page page = pageDao.findById(pageId);
    	return domainToDto(page);
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto createAndReturn(final PageDto pageDto){
		Integer id = create(pageDto);
		return findPageWithLayout(id);
	}
	
	public PageServiceImpl(final PageDao pageDao,final SiteService siteService,final PageLayoutAssembler pageLayoutAssembler){
		this.pageDao = pageDao;
		this.siteService = siteService;
		this.pageLayoutAssembler = pageLayoutAssembler;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<PageDto> getPageBySiteId(Integer siteId){
		return domainsToDtos(pageDao.findPageBySiteId(siteId)); 
	}

	public Collection<PageDto> getPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize){
		return domainsToDtos(pageDao.findPageBySiteId(siteId,pageNumber,pageSize)); 
	}

	public Long findTotalPagesForSite(Integer siteId){
		return pageDao.findTotalPagesForSite(siteId);
	}
	
	public Page dtoToDomain(final PageDto dto){
		Page page = new Page();
		page.setPageId(dto.getPageId());
		page.setUri(dto.getUri());
		page.setTitle(dto.getTitle());
		page.setSite(siteService.dtoToDomain(dto.getSite()));
		
		if (null!=dto.getPageLayoutDto()){
			page.setPageLayout(pageLayoutAssembler.dtoToDomainWithPageSections(dto.getPageLayoutDto()));
		}
		return page;
	}

	public PageDto domainToDto(final Page domain){
		PageDto dto = new PageDto();
		dto.setPageId(domain.getPageId());
		dto.setUri(domain.getUri());
		dto.setTitle(domain.getTitle());
		dto.setSite(siteService.domainToDto(domain.getSite()));
		System.out.println("the page is :"+domain.getPageId());
		dto.setPageLayoutDto(pageLayoutAssembler.domainToDto(domain.getPageLayout()));
		return dto;
	}

	public Collection<PageDto> domainsToDtos(final Collection<Page> domains){
		Collection<PageDto> dtos = new ArrayList<PageDto>();
		for (Page page : domains){
			dtos.add(domainToDto(page));
		}
		return dtos;
	}

}
