package com.contento3.cms.page.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.service.PageLayoutAssembler;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.cms.site.structure.service.SiteService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageServiceImpl implements PageService {

	private static final Logger LOGGER = Logger.getLogger(PageServiceImpl.class);

	private PageDao pageDao;
	private SiteAssembler siteAssembler;
	private PageLayoutAssembler pageLayoutAssembler;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
    public Integer create(final PageDto pageDto){
    	return pageDao.persist(dtoToDomain(pageDto));
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto findPageWithLayout(final Integer pageId)  throws PageNotFoundException{
    	Page page = pageDao.findById(pageId);
    	return domainToDto(page);
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto createAndReturn(final PageDto pageDto){
		Integer id = create(pageDto);
		PageDto newPageDto = null;
		try {
			newPageDto = findPageWithLayout(id);
		} catch (PageNotFoundException e) {
			LOGGER.error(String.format("We are expecting a new page created with name [%s], but page not found.",pageDto.getTitle()),e);
		}
		return newPageDto;
	}
	
	public PageServiceImpl(final PageDao pageDao,final SiteAssembler siteAssembler,final PageLayoutAssembler pageLayoutAssembler){
		this.pageDao = pageDao;
		this.siteAssembler = siteAssembler;
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
		page.setSite(siteAssembler.dtoToDomain(dto.getSite()));
		
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
		dto.setSite(siteAssembler.domainToDto(domain.getSite()));
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

	@Override
	public PageDto findByPathForSite(String path, Integer siteId) throws PageNotFoundException
	{
		Page page = pageDao.findPageByPathAndSiteId(path, siteId);
		
		if (null==page){
			throw new PageNotFoundException();
		}
		return 	domainToDto(page);
	}

	@Override
	public Collection<PageLayoutDto> getPageByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}


}
