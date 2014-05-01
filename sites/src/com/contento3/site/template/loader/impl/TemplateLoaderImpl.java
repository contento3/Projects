package com.contento3.site.template.loader.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.site.page.PageResourceUtil;
import com.contento3.site.template.assembler.Assembler;
import com.contento3.site.template.dto.TemplateContentDto;
import com.contento3.site.template.loader.TemplateLoader;

/**
 * Class that loads the template for different resource type.
 * Here resource type can be a PAGE,ARTICLE,BLOG and also SYSTEM/GLOBAL templates.
 * @author hamakhaa
 *
 */
public class TemplateLoaderImpl implements TemplateLoader {

	private static final Logger LOGGER = Logger.getLogger(TemplateLoaderImpl.class);

	private Assembler assembler;

	private SiteService siteService;
	
	private TemplateService templateService; 
	
	public TemplateLoaderImpl(final Assembler assembler,final SiteService siteService,final TemplateService templateService){
		this.assembler = assembler;
		this.siteService = siteService;
		this.templateService = templateService;
	}

	@Override
	public TemplateContentDto load(final String resourceName, final Integer siteId,final PageDto pageDto) {
		TemplateContentDto dto = null;
		try {
			final String resourceType = fetchTemplateResourceType(resourceName);
			if (resourceType.equals(PageResourceUtil.PAGE)){
//				String[] pageUri = resourceName.split("page/");
//				String pagePath = "";
//				if (resourceName.contains("page/")) {
//					pagePath = pageUri[1];
//				} else {
//					pagePath = pageUri[0];
//					
//					//Split it further to see for any category in the url
//					pagePath = pagePath.split("/")[1];
//				}

				 dto = findTemplate(pageDto);
			}
			else if (resourceType.equals(PageResourceUtil.TEMPLATE)) {
				if(resourceName.startsWith("/template_key") || resourceName.startsWith("template_key") ){
					dto = findDirectTemplateByKey(resourceName,siteId,"template_key/");
				}else{
					dto = findDirectTemplate(resourceName,siteId,"/template");
				}
			}
			else if (resourceType.equals(PageResourceUtil.GLOBAL_TEMPLATE)) {
				if(resourceName.startsWith("/global_template_key") || resourceName.startsWith("global_template_key") ){
					dto = findDirectTemplateByKey(resourceName,siteId,"global_template_key/");
				}else{
					dto = findDirectTemplate(resourceName,siteId,"/global_template");
				}
			}
			else if (resourceType.equals(PageResourceUtil.ARTICLE)) {
				 dto = findArticleDetailTemplate(siteId);
			}
			else {
				dto = findSystemTemplate(SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS,siteId);
			}
		} catch (final IOException e1) {
			LOGGER.info("Unable to find resource name");
		}
		catch (final Exception e1) {
			LOGGER.info(String.format("Unable to find resource name %s for site with siteId [%d].If the resource uses a System Template, please check if system template is required or not.",resourceName,siteId));
		}
		return dto;
	}

	/**
	 * Returns the resource type name based on the url
	 * @return
	 */
	private String fetchTemplateResourceType(final String resourceName){
		if (resourceName.startsWith("article/") || resourceName.startsWith("blog/") || resourceName.startsWith("story/")){
			return PageResourceUtil.ARTICLE;
		}
		else if (resourceName.equals(SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS.getValue())) {
			return SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS.toString();
		}
		else if (resourceName.startsWith("/template") || resourceName.startsWith("template") || resourceName.startsWith("/template_key") || resourceName.startsWith("template_key") ) {
			return PageResourceUtil.TEMPLATE;
		}
		else if (resourceName.startsWith("/global_template") || resourceName.startsWith("global_template") || resourceName.startsWith("/global_template_key") || resourceName.startsWith("global_template_key") ) {
			return PageResourceUtil.GLOBAL_TEMPLATE;
		}
		else return PageResourceUtil.PAGE;
	}
	
	/**
	 * Gets the global system templates;
	 * @param path
	 * @param siteId
	 * @return
	 */
	private TemplateContentDto findSystemTemplate(final SystemTemplateNameEnum path,final Integer siteId){
		TemplateContentDto dto=null;
		final SiteDto siteDto = siteService.findSiteById(siteId);
		final Integer accountId = siteDto.getAccountDto().getAccountId();
		
		try {
			TemplateDto templateDto = templateService.findSystemTemplateForAccount(path, accountId);
			dto = new TemplateContentDto();
			dto.setContent(templateDto.getTemplateText());
		} catch (EntityNotFoundException e) {
			LOGGER.error("Unable to find system template.Lets load the default error template");
			dto = loadErrorTemplate("SIMPLE",path.toString(), siteId);
		}
		return dto;
	}

	private TemplateContentDto findDirectTemplate(String templatePath,Integer siteId,final String splitter) throws Exception  {
		TemplateContentDto dto=null;
		final String[] templateName = templatePath.split(splitter,0);
		final SiteDto site = siteService.findSiteById(siteId);
		final TemplateDto template = templateService.findTemplateByPathAndAccount(templateName[1], site.getAccountDto().getAccountId());
		dto = new TemplateContentDto();
		dto.setContent(template.getTemplateText());
		return dto;
	}


	/**
	 * This method returns thymeleaf template in result of provided 
	 * key for the column template_key 
	 * 
	 * @param resourceName
	 * @param siteId
	 * @return
	 * @throws Exception 
	 */
	private TemplateContentDto findDirectTemplateByKey(String templatePath,
			Integer siteId,final String splitter) throws Exception {
		TemplateContentDto dto=null;
		final String[] templateKey = templatePath.split(splitter);
		final SiteDto site = siteService.findSiteById(siteId);
		final TemplateDto template = templateService.findTemplateByKeyAndAccount(templateKey[1], site.getAccountDto().getAccountId());
		dto = new TemplateContentDto();
		dto.setContent(template.getTemplateText());
		return dto;
	}
	
	private TemplateContentDto findTemplate(final PageDto pageDto) throws IOException {
		TemplateContentDto dto=null;

		 //Otherwise its an actual page
			//A path can be a PAGE path or a TEMPLATE path.
			//It will be a page path when THE MAIN PAGE is requested. 
			//If this is a main page then there can be different cases:
			//			a. The page has a custom layout i.e. a single template.
			//
			//          b. The page can have multiple page section 
			//			with each section having single template.
			//
			//          c. The page can have multiple page section with 
			//			each section having multiple template.
			
			//Page request should have 2 elements with 
			//format: "pageuri:siteid_locale", e.g. /mypage:1_en
			//1. pageuri
			//2. siteid
	
				try {
					dto = assembler.assemble(pageDto);
				} catch (PageNotFoundException e) {
					throw new IOException("Requested page not found",e);
				}
				catch (NumberFormatException e) {
					//throw new NumberFormatException("Requested page with path ["+path+"]does not have the site component");
					return new TemplateContentDto();
				}
		return dto;
	}

	/**
	 * 
	 * @param articleDetail
	 * @param siteId
	 * @return
	 * @throws EntityNotFoundException 
	 */
	private TemplateContentDto findArticleDetailTemplate(final Integer siteId) throws EntityNotFoundException{
		final SiteDto site = siteService.findSiteById(siteId);
		final TemplateDto template = templateService.findSystemTemplateForAccount(SystemTemplateNameEnum.SYSTEM_ARTICLE, site.getAccountDto().getAccountId());
		final TemplateContentDto dto = new TemplateContentDto();
		dto.setContent(template.getTemplateText());
		return dto;
	}
	
	
	@Override
	public TemplateContentDto loadErrorTemplate(final String errorType,final String resourceName,final Integer siteId) {
		// TODO Get error template for a site and for a type
		//Otherwise just return the default error template.
		final TemplateContentDto dto = new TemplateContentDto();
		
		if(null==dto.getContent()){
			dto.setContent("Error occured while trying to access the page");
		}
		
		return dto;
	}

}
