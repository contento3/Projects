package com.contento3.cms.page.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageCannotCreateException;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.page.service.PageAssembler;
import com.contento3.cms.page.service.PageService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.keywords.SystemKeywordsEnum;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageServiceImpl implements PageService {
	
	private static final Logger LOGGER = Logger.getLogger(PageServiceImpl.class);
	
	private PageAssembler pageAssembler;

	private PageDao pageDao;

	public PageServiceImpl(final PageDao pageDao,final PageAssembler pageAssembler){
		Validate.notNull(pageDao,"pageDao cannot be null");
		Validate.notNull(pageAssembler,"pageAssembler cannot be null");
		this.pageDao = pageDao;
		this.pageAssembler = pageAssembler;
	}

	@RequiresPermissions("PAGE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto findById(final Integer pageId)  throws PageNotFoundException{
		Validate.notNull(pageId,"pageId cannot be null");
    	Page page = pageDao.findById(pageId);
    	return pageAssembler.domainToDto(page);
    }
	
	@RequiresPermissions("PAGE:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
    public Integer create(final PageDto pageDto) throws PageCannotCreateException {
		
		Validate.notNull(pageDto,"pageDto cannot be null");

		if(SystemKeywordsEnum.contains(pageDto.getUri())) {
			throw new PageCannotCreateException(PageCannotCreateException.EXCEPTION_MSG_PAGE_CANT_CREATED);
		}
		
    	return pageDao.persist(pageAssembler.dtoToDomain(pageDto));
    }
	
	
	@RequiresPermissions("PAGE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto findPageWithLayout(final Integer pageId)  throws PageNotFoundException{
		Validate.notNull(pageId,"pageId cannot be null");
    	Page page = pageDao.findById(pageId);
    	return pageAssembler.domainToDto(page);
    }
	
	@RequiresPermissions("PAGE:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PageDto createAndReturn(final PageDto pageDto) throws EntityAlreadyFoundException, PageCannotCreateException {
		Validate.notNull(pageDto,"pageDto cannot be null");
		
		if (isPageExists(pageDto)){
			throw new EntityAlreadyFoundException();
		} else if( SystemKeywordsEnum.contains(pageDto.getUri()) ) {
			throw new PageCannotCreateException(PageCannotCreateException.EXCEPTION_MSG_PAGE_CANT_CREATED);
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
	
	@RequiresPermissions("PAGE:VIEW_LISTING")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<PageDto> findPageBySiteId(Integer siteId){
		Validate.notNull(siteId,"siteId cannot be null");
		return pageAssembler.domainsToDtos(pageDao.findPageBySiteId(siteId)); 
	}
	
	@RequiresPermissions("PAGE:VIEW")
	public PageDto findPageBySiteId(final Integer siteId,final Integer pageId){
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(pageId,"pageId cannot be null");
		 return pageAssembler.domainToDto(pageDao.findById(pageId)); 
	 }
	
	@RequiresPermissions("PAGE:VIEW")
	public Long findTotalPagesForSite(Integer siteId){
		Validate.notNull(siteId,"siteId cannot be null");
		return pageDao.findTotalPagesForSite(siteId);
	}
	
	@RequiresPermissions("PAGE:VIEW")
	@Override
	public PageDto findByPathForSite(String path, Integer siteId) throws PageNotFoundException
	{
		Validate.notNull(path,"path cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		
		//If the path has .uncached part then need to 
		//remove before going into the dao
		if(path.indexOf(".uncached") != -1){
			path = path.substring(0,path.indexOf(".uncached"));
		}

		Page page = pageDao.findPageByPathAndSiteId(path, siteId);

		if (null==page){
			throw new PageNotFoundException(String.format("Page not found for path [%s] for site id [%d]",path,siteId));
		}
		return 	pageAssembler.domainToDto(page);
	}
	
	@RequiresPermissions("PAGE:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final PageDto pageDto) throws EntityAlreadyFoundException, PageCannotCreateException {
		
		Validate.notNull(pageDto,"pageDto cannot be null");
		if (isPageExists(pageDto)){
			throw new EntityAlreadyFoundException();
		} else if( SystemKeywordsEnum.contains(pageDto.getUri()) ) {
			throw new PageCannotCreateException(PageCannotCreateException.EXCEPTION_MSG_PAGE_CANT_CREATED);
		}
		
		Page pageToUpdate = pageDao.findById(pageDto.getPageId());
		pageDao.update(pageAssembler.dtoToDomain(pageDto,pageToUpdate));
	}

	/**\
	 * Return true if the title or uri already exists
	 * @param title
	 * @param url
	 * @return
	 */
	@RequiresPermissions("PAGE:VIEW")
	private boolean isPageExists(final PageDto pageDto){
		Validate.notNull(pageDto,"pageDto cannot be null");
		
		Integer siteId = pageDto.getSite().getSiteId();
		Page pageForTitle = pageDao.findPageByTitleAndSiteId(pageDto.getTitle(),siteId);
		boolean isExists = false;
		
		if (null==pageForTitle){
			isExists = false;
		}
		else if (pageDto.getTitle().equals(pageForTitle.getTitle())) {
			isExists = true; 	
		}
		
		Page pageForUrl=null;
		if (!isExists || (pageDto.getPageId()!=null && pageDto.getPageId().equals(pageDto.getPageId()))){ //No need to check the uri as title is already present meaning we cant create this page anyway.
			pageForUrl = pageDao.findPageByPathAndSiteId(pageDto.getUri(),siteId);
			if (null==pageForUrl){
				isExists = false;
			}
			else if (pageDto.getUri().equals(pageForUrl.getUri()) && null==pageDto.getPageId()){
				isExists = true;
			}
		}
		
		if (pageDto.getPageId()!=null && pageForTitle!=null && pageForTitle.getPageId().equals(pageDto.getPageId()) && pageForTitle.getTitle().equals(pageDto.getTitle()) && (pageForUrl==null || (pageForUrl!=null && pageForUrl.getPageId().equals(pageDto.getPageId()) && pageForUrl.getUri().equals(pageDto.getUri())) )){
			return false;
		}
		
		
		return isExists;
	}

	@RequiresPermissions("PAGE:VIEW_LISTING")
	@Override
	public Collection<PageDto> findNavigablePagesBySiteId(final Integer siteId) {
		Validate.notNull(siteId,"siteId cannot be null");
		return pageAssembler.domainsToDtos(pageDao.findNavigablePages(siteId)); 	
	}

	@RequiresPermissions("PAGE:VIEW_LISTING")
	@Override
	public Collection<PageDto> findPagesByCategory(
			Collection<Integer> categoryIds, Integer siteId, Integer accountId) {
		return pageAssembler.domainsToDtos(pageDao.findPagesByCategory(categoryIds, accountId, siteId));
	}
}
