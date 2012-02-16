package com.contento3.site.template.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.section.dto.PageSectionDto;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.site.page.section.resolver.HtmlResolver;
import com.contento3.site.template.dto.TemplateContentDto;

public class PageAssembler implements Assembler {

	private PageService pageService;
	
	private PageTemplateService pageTemplateService;
	
	private TemplateService templateService;
	
	private HtmlResolver htmlResolver;
	
	@Override
	public TemplateContentDto assemble(final Integer siteId,final String path) throws PageNotFoundException {
		
		PageDto pageDto = pageService.findByPathForSite(path, siteId);
		
		PageLayoutDto layoutDto = pageDto.getPageLayoutDto();
		TemplateContentDto templateContentDto = new TemplateContentDto();
		
		//If the page has a CUSTOM layout or 
		//in other words there is no layout_id for this page.
		if (null==layoutDto)
		{ 
			Collection<PageTemplateDto> pageTemplateDtos = pageTemplateService.findByPageAndPageSectionType(pageDto.getPageId(), PageSectionTypeEnum.CUSTOM);
			if (pageTemplateDtos.size()==1){
				Iterator<PageTemplateDto> iterator = pageTemplateDtos.iterator();
				PageTemplateDto dto = iterator.next();
				TemplateDto templateDto = templateService.findTemplateById(dto.getTemplateId());
				templateContentDto.setContent(templateDto.getTemplateText());
			}
		}
		//else there is a layout attached to this page.
		else {
			templateContentDto.setContent(buildPageTemplate((List)layoutDto.getPageSections(),pageDto,layoutDto));
		}
			
		return templateContentDto;
	}

	@Override
	public TemplateContentDto assembleInclude(final Integer siteId,final String path) throws PageNotFoundException {
		TemplateContentDto dto = new TemplateContentDto();
		dto = new TemplateContentDto();
		try {
			dto.setContent(templateService.findTemplateByPathAndSiteId(path,siteId).getTemplateText());
		} catch (Exception e) {
			throw new PageNotFoundException();
		}
		return dto;
		
	}
	
	/**
	 * Builds the page based on page sections present for this page.
	 * @param pageSectionDtos
	 * @return
	 */
	private String buildPageTemplate(final List<PageSectionDto> pageSectionDtos,final PageDto pageDto,final PageLayoutDto layoutDto){
		Collections.sort(pageSectionDtos);
		
		StringBuffer body = new StringBuffer();
		StringBuffer header = new StringBuffer();
		StringBuffer footer = new StringBuffer();
		StringBuffer navigation = new StringBuffer();
		
		for (PageSectionDto pageSectionDto : pageSectionDtos){
			String pageSectionName = pageSectionDto.getSectionTypeDto().getName();
			List<PageTemplateDto> pageTemplateDtos = (ArrayList)pageTemplateService.findByPageAndPageSectionType(pageDto.getPageId(), pageSectionDto.getSectionTypeDto().getId());
			Collections.sort(pageTemplateDtos);

			if (pageSectionName.equals(PageSectionTypeEnum.BODY.toString())){
				body.append(buildBodyTemplates(pageTemplateDtos,pageSectionDto));
			}
			else {
				header.append(buildPageSectionTemplates(pageTemplateDtos,pageSectionDto));
			}
		}
		
		return String.format("%s%s%s%s",header,navigation,body,footer);
	}

	/**
	 * Used to build the text for each page section.All the markup for all 
	 * template for a page section is combined and then passed to the html 
	 * resolver to get the final string based on resolver. 
	 * @param pageTemplateDtos
	 * @param pageSection
	 * @param pageSectionDto
	 * @return
	 */
	private String buildPageSectionTemplates(final Collection <PageTemplateDto> pageTemplateDtos,
			final PageSectionDto pageSectionDto){
		StringBuffer pageSectionString = new StringBuffer();
		for (PageTemplateDto pageTemplate : pageTemplateDtos){
			TemplateDto templateDto = templateService.findTemplateById(pageTemplate.getTemplateId());
			pageSectionString.append(templateDto.getTemplateText());
		}
		
		return 	htmlResolver.resolve(pageSectionString, pageSectionDto);
	}

	/**
	 * Used to build the text for main body page.All the mark up for all 
	 * template for a page section is combined and then passed to the html 
	 * resolver to get the final string based on resolver. 
	 * @param pageTemplateDtos
	 * @param pageSection
	 * @param pageSectionDto
	 * @return
	 */
	private String buildBodyTemplates(final Collection <PageTemplateDto> pageTemplateDtos,
			final PageSectionDto pageSectionDto){
		return 	htmlResolver.resolveBody(pageTemplateDtos, pageSectionDto);
	}

	/**
	 * Sets the pageService object.
	 * @param pageService
	 */
	public void setPageService(final PageService pageService) {
		this.pageService = pageService;
	}

	/**
	 * Sets the pageTemplateService
	 * @param pageTemplateService
	 */
	public void setPageTemplateService(final PageTemplateService pageTemplateService) {
		this.pageTemplateService = pageTemplateService;
	}

	/**
	 * Sets the template service
	 * @param templateService
	 */
	public void setTemplateService(final TemplateService templateService) {
		this.templateService = templateService;
	}

	/**
	 * Sets the html resolver.
	 * @param resolver
	 */
	public void setHtmlResolver(final HtmlResolver resolver){
		this.htmlResolver = resolver;
	}

}
