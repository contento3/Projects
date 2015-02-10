package com.contento3.web.pagemodules;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.ResourceNotFoundException;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.SessionHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PageRowContentMapper implements ClickListener {

	private static final Logger LOGGER = Logger.getLogger(PageRowContentMapper.class);
	
	private static final long serialVersionUID = 1L;

	final String templateKey;
	
	final Map<Panel, Map<String,Component>> rowMap;
	
	final UIManagerContext context;
	
	final Window containerWindow;
	
	final TemplateService templateService;

	final AccountService accountService;
	
	Map <String,TemplateDto> templateMap;
	
	public PageRowContentMapper (final UIManagerContext context,final String templateKey,
			final Map<Panel, Map<String,Component>> rowMap,final Window window) {
		this.templateKey = templateKey;
		this.rowMap = rowMap;
		this.context = context;
		this.containerWindow = window;
		templateService = (TemplateService)context.getHelper().getBean("templateService");
		accountService = (AccountService)context.getHelper().getBean("accountService");
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		combineDesignContent();
		UI.getCurrent().removeWindow(containerWindow);
	}

	private void combineDesignContent() {
		//TODO 1. Replace placeholders values in each template based on the map
		
		//1. Get all the panels for this section
		final Set<Panel> panelsForThisSection = rowMap.keySet();
		
		final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
		final AccountDto accountDto = accountService.findAccountById(accountId);
		TemplateDto pageSectionTemplateDto = null;
		//2. Iterate each panel and process each template
		for (Panel panel : panelsForThisSection){
			
			//3. Get the template key from this panel
			final String templateKey = (String)panel.getData(); 
			
			//4. Get the template string
			String templateString = null;
			TemplateDto templateDto;
			try {
				String [] templateKeyInDb = templateKey.split("\\.");
				templateDto = templateService.findGlobalTemplateByKey(templateKeyInDb[1]);
				templateString = templateDto.getTemplateText();
				
				final Map <String,Component> panelInfo = rowMap.get(panel);
				String finalString = null;

//				//TODO 3. Process each template for row
				pageSectionTemplateDto = new TemplateDto();
				pageSectionTemplateDto.setAccountDto(null);
				pageSectionTemplateDto.setGlobal(false);
				
				long timestamp = System.currentTimeMillis();
				pageSectionTemplateDto.setTemplateKey(templateKey+"_"+timestamp);
				pageSectionTemplateDto.setTemplateName(templateKey+"_"+timestamp);

				final TemplateTypeDto templateType = new TemplateTypeDto();
				templateType.setTemplateTypeName("TEXT_FREEMARKER");
				
				pageSectionTemplateDto.setTemplateType(templateType);
				pageSectionTemplateDto.setAccountDto(accountDto);
				
				for (String key : panelInfo.keySet()) {
					String replacement = null;
					final Component component = panelInfo.get(key);
					if (component instanceof TextField){
						replacement = ((TextField)component).getValue();
						templateString = StringUtils.replace(templateString,key,replacement);			
					}
					//If there is no text field then this is content selector
					else if (component instanceof Button){
						replacement = (String)((Button)component).getData();
						replacement = "<category:simple key=\"mykey\">";
						templateString = StringUtils.replace(templateString,String.format("<!--%s-->", key),replacement);			
					}
				}

				pageSectionTemplateDto.setTemplateText(templateString);
			}
			catch (final ResourceNotFoundException e2) {
			
			}

			if (null==UI.getCurrent().getSession().getAttribute("templateDtoMap")){
				this.templateMap = new HashMap <String,TemplateDto>();
				UI.getCurrent().getSession().setAttribute("templateDtoMap", templateMap);
			}
			//TODO 4. Display changes in the preview
		}
		templateMap.put(this.templateKey.split("\\.")[0], pageSectionTemplateDto);
	}
}
