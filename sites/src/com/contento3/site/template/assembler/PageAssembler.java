package com.contento3.site.template.assembler;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.contento3.site.template.dto.TemplateContentDto;
import com.olive.cms.page.dto.PageDto;
import com.olive.cms.page.exception.PageNotFoundException;
import com.olive.cms.page.layout.dto.PageLayoutDto;
import com.olive.cms.page.section.dto.PageSectionDto;
import com.olive.cms.page.section.model.PageSectionTypeEnum;
import com.olive.cms.page.service.PageService;
import com.olive.cms.page.template.dto.PageTemplateDto;
import com.olive.cms.page.template.dto.TemplateDto;
import com.olive.cms.page.template.service.PageTemplateService;
import com.olive.cms.page.template.service.TemplateService;

public class PageAssembler implements Assembler {

	private PageService pageService;
	
	private PageTemplateService pageTemplateService;
	
	private TemplateService templateService;
	
	@Override
	public TemplateContentDto assemble(final Integer siteId,final String path) throws PageNotFoundException{
		
		PageDto pageDto = pageService.findByPathForSite(path, siteId);
		
		PageLayoutDto layoutDto = pageDto.getPageLayoutDto();
		TemplateContentDto templateContentDto = new TemplateContentDto();
		
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
		else {
			templateContentDto.setContent(buildPageTemplate((List)layoutDto.getPageSections()));
		}
			
		return templateContentDto;
	}

	/**
	 * Builds the page based on page sections present for this page.
	 * @param pageSectionDtos
	 * @return
	 */
	private String buildPageTemplate(List<PageSectionDto> pageSectionDtos){
		Collections.sort(pageSectionDtos);
		
		String body = "";
		String header = "";
		String footer = "";
		String navigation = "";
		
		for (PageSectionDto dto : pageSectionDtos){
			String pageSectionName = dto.getSectionTypeDto().getName();
			if (pageSectionName.equals(PageSectionTypeEnum.BODY.toString())){
				body = dto.getTemplateMarkup();
			}
			else if (pageSectionName.equals(PageSectionTypeEnum.HEADER.toString())){
				header = dto.getTemplateMarkup();
			}
			else if (pageSectionName.equals(PageSectionTypeEnum.FOOTER.toString())){
				footer = dto.getTemplateMarkup();
			}
			else if (pageSectionName.equals(PageSectionTypeEnum.LEFT_NAVIGATION.toString()) || pageSectionName.equals(PageSectionTypeEnum.RIGHT_NAVIGATION.toString())){
				navigation = dto.getTemplateMarkup();
			}
		}
		
		return String.format("%s%s%s%s",header,navigation,body,footer);
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setPageTemplateService(PageTemplateService pageTemplateService) {
		this.pageTemplateService = pageTemplateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
}
