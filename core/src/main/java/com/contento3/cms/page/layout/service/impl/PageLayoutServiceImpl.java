package com.contento3.cms.page.layout.service.impl;

import java.util.Collection;

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
		this.pageLayoutDao = pageLayoutDao;
		this.pageSectionDao = pageSectionDao;
		this.pageSectionAssembler = pageSectionAssembler;
		this.assembler = assembler;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Collection<PageLayoutDto> findPageLayoutByAccount(final Integer accountId){
		return assembler.domainsToDtos(pageLayoutDao.findPageLayoutByAccount(accountId));
	}	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public PageLayoutDto findPageLayoutById(final Integer pageLayoutId){
		return assembler.domainToDto(pageLayoutDao.findById(pageLayoutId));
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void create(PageLayoutDto pageLayoutDto){
		PageLayout pageLayout = assembler.dtoToDomain(pageLayoutDto);
		pageLayoutDao.persist(pageLayout);
		
		Collection <PageSection> pageSectionList = pageSectionAssembler.dtosToDomains(pageLayoutDto.getPageSections());
		
		for (PageSection pageSection : pageSectionList){
			pageSection.setPageLayout(pageLayout);
			pageLayout.getPageSections().add(pageSection);
			pageSectionDao.persist(pageSection);
		}
	}
}
