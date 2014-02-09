package com.contento3.cms.page.layout.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.layout.dao.PageLayoutDao;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.page.layout.service.PageLayoutAssembler;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.section.dao.PageSectionDao;
import com.contento3.cms.page.section.model.PageSection;
import com.contento3.cms.page.section.service.PageSectionAssembler;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageLayoutServiceImpl implements PageLayoutService {

	private PageLayoutDao pageLayoutDao;
	private PageSectionDao pageSectionDao;
	private PageSectionAssembler pageSectionAssembler;
	private PageLayoutAssembler assembler;
	
	public PageLayoutServiceImpl(final PageLayoutDao pageLayoutDao,final PageSectionDao pageSectionDao,final PageLayoutAssembler assembler,final PageSectionAssembler pageSectionAssembler){
		Validate.notNull(pageLayoutDao,"pageLayoutDao cannot be null");
		Validate.notNull(pageSectionDao,"pageSectionDao cannot be null");
		Validate.notNull(assembler,"assembler cannot be null");
		Validate.notNull(pageSectionAssembler,"pageSectionAssembler cannot be null");
		
		this.pageLayoutDao = pageLayoutDao;
		this.pageSectionDao = pageSectionDao;
		this.pageSectionAssembler = pageSectionAssembler;
		this.assembler = assembler;
	}
	
	//@RequiresPermissions("PAGELAYOUT:VIEW")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Collection<PageLayoutDto> findPageLayoutByAccount(final Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		return assembler.domainsToDtos(pageLayoutDao.findPageLayoutByAccount(accountId));
	}
	
	//@RequiresPermissions("PAGELAYOUT:VIEW")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public PageLayoutDto findPageLayoutById(final Integer pageLayoutId){
		Validate.notNull(pageLayoutId,"pageLayoutId cannot be null");
		return assembler.domainToDto(pageLayoutDao.findById(pageLayoutId));
	}
	
	//@RequiresPermissions("PAGELAYOUT:ADD")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Integer create(PageLayoutDto pageLayoutDto){
		Validate.notNull(pageLayoutDto,"pageLayoutDto cannot be null");
		PageLayout pageLayout = assembler.dtoToDomain(pageLayoutDto);
		Integer newLayoutId = pageLayoutDao.persist(pageLayout);
		
		Collection <PageSection> pageSectionList = pageSectionAssembler.dtosToDomains(pageLayoutDto.getPageSections());
		
		for (PageSection pageSection : pageSectionList){
			pageSection.setPageLayout(pageLayout);
			pageLayout.getPageSections().add(pageSection);
			pageSectionDao.persist(pageSection);
		}
		
		return newLayoutId;
	}
	
	//@RequiresPermissions("PAGELAYOUT:DELETE")
	@Override
	public void delete(PageLayoutDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}
}
