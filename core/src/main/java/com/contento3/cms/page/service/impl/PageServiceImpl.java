package com.contento3.cms.page.service.impl;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.service.PageAssembler;
import com.contento3.cms.page.service.PageService;
import com.contento3.common.exception.EntityAlreadyFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageServiceImpl implements PageService {

	private static final Logger LOGGER = Logger.getLogger(PageServiceImpl.class);

	private PageAssembler pageAssembler;
	private PageDao pageDao;

	public PageServiceImpl(final PageDao pageDao,final PageAssembler pageAssembler){
		this.pageDao = pageDao;
		this.pageAssembler = pageAssembler;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
    public Integer create(final PageDto pageDto){
    	return pageDao.persist(pageAssembler.dtoToDomain(pageDto));
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto findPageWithLayout(final Integer pageId)  throws PageNotFoundException{
    	Page page = pageDao.findById(pageId);
    	return pageAssembler.domainToDto(page);
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto createAndReturn(final PageDto pageDto) throws EntityAlreadyFoundException{
		
		if (isPageExists(pageDto)){
			throw new EntityAlreadyFoundException();
		}

		Integer id = create(pageDto);
		PageDto newPageDto = null;
		try {
			newPageDto = findPageWithLayout(id);
		} catch (PageNotFoundException e) {
			LOGGER.error(String.format("We are expecting a new page created with name [%s], but page not found.",pageDto.getTitle()),e);
		}
		return newPageDto;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<PageDto> findPageBySiteId(Integer siteId){
		return pageAssembler.domainsToDtos(pageDao.findPageBySiteId(siteId)); 
	}

	public Collection<PageDto> findPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize){
		return pageAssembler.domainsToDtos(pageDao.findPageBySiteId(siteId,pageNumber,pageSize)); 
	}

	public Long findTotalPagesForSite(Integer siteId){
		return pageDao.findTotalPagesForSite(siteId);
	}
	

	@Override
	public PageDto findByPathForSite(String path, Integer siteId) throws PageNotFoundException
	{
		Page page = pageDao.findPageByPathAndSiteId(path, siteId);
		
		if (null==page){
			throw new PageNotFoundException();
		}
		return 	pageAssembler.domainToDto(page);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final PageDto pageDto) throws EntityAlreadyFoundException {
		
		if (isPageExists(pageDto)){
			throw new EntityAlreadyFoundException();
		}
		
		Page pageToUpdate = pageDao.findById(pageDto.getPageId());
		pageDao.update(pageAssembler.dtoToDomain(pageDto,pageToUpdate));
	}

	/**
	 * Return true if the title or uri already exists
	 * @param title
	 * @param url
	 * @return
	 */
	private boolean isPageExists(final PageDto pageDto){
		Page pageForTitle = pageDao.findPageByTitle(pageDto.getTitle());
		if (null==pageForTitle){
			return false;
		}
		else if (pageDto.getTitle().equals(pageForTitle.getTitle())) {
			return true;
		}
		
		Page pageForUrl = pageDao.findPageByUri(pageDto.getUri());
		if (null==pageForUrl){
			return false;
		}
		else if (pageDto.getUri().equals(pageForUrl.getUri())){
			return true;
		}
		
		return false;
	}
}
