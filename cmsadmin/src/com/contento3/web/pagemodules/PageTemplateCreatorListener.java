package com.contento3.web.pagemodules;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageCannotCreateException;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.exception.ResourceNotFoundException;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.SessionHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PageTemplateCreatorListener implements ClickListener {

	private static final Logger LOGGER = Logger.getLogger(PageRowContentMapper.class);

	private static final long serialVersionUID = 1L;

	final SiteService siteService;
	
	final UIManagerContext context;
	
	final TemplateService templateService;
	
	final TemplateDirectoryService templateDirectoryService;
	
	final AccountService accountService;

	final String templateKey;
	
	final PageModuleLayoutsEnum pageLayoutName;
	
	final Map <String,TemplateDto> pageLayoutsMap;
	
	final PageTemplateService pageTemplateService;
	
	AccountDto accountDto;
	
	final PageService pageService;
	
	final TextField pageTitle;
	
	CachedTypedProperties properties = null;
	
	final ComboBox siteCombo;
	public PageTemplateCreatorListener (final UIManagerContext context,final PageModuleLayoutsEnum pageLayoutName,
			final ComboBox siteCombo,final TextField pageTitle) {
		this.templateKey = "";
		this.context = context;
		this.pageLayoutName = pageLayoutName;
		this.siteCombo = siteCombo;
		this.pageTitle = pageTitle;
		this.pageLayoutsMap = (Map <String,TemplateDto>)UI.getCurrent().getSession().getAttribute("templateDtoMap");
		templateDirectoryService = (TemplateDirectoryService)context.getHelper().getBean("templateDirectoryService"); 
		siteService = (SiteService)context.getHelper().getBean("siteService");
		templateService = (TemplateService)context.getHelper().getBean("templateService");
		accountService = (AccountService)context.getHelper().getBean("accountService");
		pageTemplateService = (PageTemplateService)context.getHelper().getBean("pageTemplateService");
		pageService = (PageService)context.getHelper().getBean("pageService");

		final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
		accountDto = accountService.findAccountById(accountId);
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		
		if (null==siteCombo.getValue()){
			Notification.show("Page module","Please select site to create page.",Notification.Type.TRAY_NOTIFICATION);
		}
		else if (StringUtils.isEmpty(pageTitle.getValue())){
			Notification.show("Page module","Please enter page title to create page.",Notification.Type.TRAY_NOTIFICATION);
		}
		else {	
			//TODO 4 Create template directory
			//If template directory service does not exists then we 
			//need to make sure template directory is created
			TemplateDirectoryDto existingTemplateDirectory = null;
			Integer existingTemplateDirectoryId = null;
	
			try {
				existingTemplateDirectory = templateDirectoryService.findByName("page-module-generated-templates", false, (Integer)SessionHelper.loadAttribute("accountId"));
			} catch (final EntityNotFoundException e1) {
					LOGGER.debug("Unable to find directory with name [page-module-generated-templates]");
			}
			if (null==existingTemplateDirectory){
				existingTemplateDirectory = new TemplateDirectoryDto();
				existingTemplateDirectory.setAccount(accountDto);
				existingTemplateDirectory.setChildDirectories(null);
				existingTemplateDirectory.setDirectoryName("page-module-generated-templates");
				existingTemplateDirectory.setGlobal(false);
				existingTemplateDirectory.setParent(null);
				existingTemplateDirectoryId = templateDirectoryService.create(existingTemplateDirectory);
			}
			else {
				existingTemplateDirectoryId = existingTemplateDirectory.getId();
			}
		
			TemplateDirectoryDto layoutNameTemplateDirectory = null;
			final String secondLevelDir = pageLayoutName.toString()+"_"+System.currentTimeMillis(); 
			try {
				layoutNameTemplateDirectory = templateDirectoryService.findChildDirectory(existingTemplateDirectoryId,secondLevelDir,(Integer)SessionHelper.loadAttribute("accountId"));
			}
			catch (final EntityNotFoundException e1) {
				LOGGER.debug("Unable to find directory with name [page-module-generated-templates]");
			}
				
			if (null==layoutNameTemplateDirectory){
				layoutNameTemplateDirectory = new TemplateDirectoryDto();
				layoutNameTemplateDirectory.setAccount(accountDto);
				layoutNameTemplateDirectory.setDirectoryName(secondLevelDir);
				layoutNameTemplateDirectory.setGlobal(false);
				layoutNameTemplateDirectory.setParent(existingTemplateDirectory);
				existingTemplateDirectory.getChildDirectories().add(layoutNameTemplateDirectory);
					
				final Integer pageSectionTemplateDirectoryId = templateDirectoryService.create(layoutNameTemplateDirectory);
				layoutNameTemplateDirectory.setId(pageSectionTemplateDirectoryId);
			}
		
			processPageSectionTemplates(layoutNameTemplateDirectory);
			Notification.show("Page module","Template for page created.",Notification.Type.TRAY_NOTIFICATION);
		}
	}

	private void processPageSectionTemplates(final TemplateDirectoryDto parentLayoutDirectory) {
		//TODO 3. Process each template for row
		if (!CollectionUtils.isEmpty(pageLayoutsMap)){
			for (String pageSectionName:pageLayoutsMap.keySet()){
				createDirectoryAndTemplate(pageSectionName,parentLayoutDirectory,pageLayoutsMap.get(pageSectionName));
			}
		}

		//Template that will get assigned to the page and 
		//that will include the other pages
		//using thymeleaf includes.
		final TemplateDto mainTemplate = new TemplateDto();
		mainTemplate.setAccountDto(accountDto);
		mainTemplate.setGlobal(false);
		
		long timestamp = System.currentTimeMillis();
		mainTemplate.setTemplateDirectoryDto(parentLayoutDirectory);
		mainTemplate.setTemplateName(pageLayoutName+"_"+timestamp);

		final TemplateTypeDto templateType = new TemplateTypeDto();
		templateType.setTemplateTypeName("TEXT_FREEMARKER");
		mainTemplate.setTemplateType(templateType);
	
		mainTemplate.setTemplateText(buildTemplateText());
		mainTemplate.setAccountDto(accountDto);
		mainTemplate.setTemplatePath(templateService.buildTemplatePath(mainTemplate));
		Integer mainTemplateId;
		try {
			mainTemplateId = (Integer)templateService.create(mainTemplate);

			final SiteDto siteDto = siteService.findSiteById(Integer.parseInt(siteCombo.getValue().toString()));
			
			//Finally create the page
			final PageDto pageDto = new PageDto();
			pageDto.setIsNavigable(1);
			pageDto.setParent(null);
			pageDto.setSite(siteDto);
			pageDto.setTitle(pageTitle.getValue());
			pageDto.setUri(pageTitle.getValue());
			Integer pageId = pageService.create(pageDto);
			
			PageTemplateDto pageTemplateDto = new PageTemplateDto();
			pageTemplateDto.setOrder(1);
			pageTemplateDto.setPageId(pageId);
			pageTemplateDto.setSectionTypeId(null);
			pageTemplateDto.setTemplateId(mainTemplateId);
			pageTemplateService.create(pageTemplateDto);

		} catch (final EntityAlreadyFoundException e) {
			LOGGER.debug("Unable to create page section");
			Notification.show("Page Module creation failed", "Template with key "+templateKey+" cannot be created.", Notification.Type.TRAY_NOTIFICATION);
		} catch (final EntityNotCreatedException e) {
			LOGGER.debug("Unable to create page section");
			Notification.show("Page Module creation failed", "Template with key "+templateKey+" cannot be created.", Notification.Type.TRAY_NOTIFICATION);
		} catch (final PageCannotCreateException e) {
			LOGGER.debug("Unable to create page section");
			Notification.show("Page Module creation failed", "Template with key "+templateKey+" cannot be created.", Notification.Type.TRAY_NOTIFICATION);
		}
		
	}
	
	
	private void createDirectoryAndTemplate(final String directoryName,final TemplateDirectoryDto parent,final TemplateDto templateDto) {
		final TemplateDirectoryDto layoutNameTemplateDirectory = new TemplateDirectoryDto();
		layoutNameTemplateDirectory.setAccount(accountDto);
		layoutNameTemplateDirectory.setChildDirectories(null);
		layoutNameTemplateDirectory.setDirectoryName(directoryName);
		layoutNameTemplateDirectory.setGlobal(false);
		layoutNameTemplateDirectory.setParent(parent);
		parent.getChildDirectories().add(layoutNameTemplateDirectory);
		
		final Integer pageSectionTemplateDirectoryId = templateDirectoryService.create(layoutNameTemplateDirectory);
		layoutNameTemplateDirectory.setId(pageSectionTemplateDirectoryId);

		try {
			templateDto.setTemplateDirectoryDto(layoutNameTemplateDirectory);
			templateDto.setTemplatePath(templateService.buildTemplatePath(templateDto));
			templateService.create(templateDto);

		} catch (EntityAlreadyFoundException e) {
			LOGGER.debug("Unable to create page section");
			Notification.show("Page Module creation failed", "Template with key "+templateKey+" cannot be created.", Notification.Type.TRAY_NOTIFICATION);
		} catch (EntityNotCreatedException e) {
			LOGGER.debug("Unable to create page section");
			Notification.show("Page Module creation failed", "Template with key "+templateKey+" cannot be created.", Notification.Type.TRAY_NOTIFICATION);
		}
	}

	private String buildTemplateText() {
		String templateString = "";
		String mainPageTemplateKey = null;
		
		try {
			if (null==properties){
				properties = CachedTypedProperties.getInstance("pagemodulesconfig.properties");
				mainPageTemplateKey = properties.getStringProperty("MAIN_PAGE_TEMPLATE_KEY");
				final TemplateDto mainPageTemplate = templateService.findGlobalTemplateByKey(mainPageTemplateKey);
				templateString = mainPageTemplate.getTemplateText();
				
				if (null!=pageLayoutsMap.get(PageSectionTypeEnum.HEADER.toString())){
					templateString = templateString.replaceFirst("@header", pageLayoutsMap.get("HEADER").getTemplatePath());
				}
				if (null!=pageLayoutsMap.get(PageSectionTypeEnum.CONTENT.toString())){
					templateString = templateString.replaceFirst("@content", pageLayoutsMap.get("CONTENT").getTemplatePath());
				}
				if (null!=pageLayoutsMap.get(PageSectionTypeEnum.FOOTER.toString())){
					templateString = templateString.replaceFirst("@footer", pageLayoutsMap.get("FOOTER").getTemplatePath());
				}	
			}
		} catch (final ClassNotFoundException e) {
			LOGGER.debug("Unable to find reource [template] with key"+mainPageTemplateKey);
		} catch (final ResourceNotFoundException e) {
			LOGGER.debug("Unable to find reource [template] with key"+mainPageTemplateKey);
		}
		
		return templateString;
	}
}
