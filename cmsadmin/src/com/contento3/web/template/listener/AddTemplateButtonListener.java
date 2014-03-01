package com.contento3.web.template.listener;

import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.template.TemplateForm;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;

public class AddTemplateButtonListener implements ClickListener {

	/**
     * Logger for Template
     */ 
	private static final Logger LOGGER = Logger.getLogger(AddTemplateButtonListener.class);
	
	private static final long serialVersionUID = 1L;

    private TemplateService templateService;
    
    private TemplateDto templateDto;
    
    private TemplateForm templateForm;
    
	public AddTemplateButtonListener (final SpringContextHelper helper,final TemplateForm templateForm,final TemplateDto templateDto){
		this.templateService = (TemplateService)helper.getBean("templateService");
		this.templateForm = templateForm;
		this.templateDto = templateDto;
	}
	
	@Override
	public void click(final ClickEvent event) {
		if (null==templateDto){
			try {
				final TemplateDto templateDto = new TemplateDto();
				templateDto.setTemplateName(templateForm.getTemplateNameTxtFld().getValue());
				templateDto.setTemplatePath(templateForm.getTemplatePathTxtFld().getValue());
				templateDto.setTemplateText(templateForm.getEditor().getValue());
				templateDto.setGlobal(templateForm.getIsGlobal().getValue());
				
				final TemplateDirectoryDto directory = new TemplateDirectoryDto();
				directory.setId(Integer.parseInt(templateForm.getDirectoryId().getValue()));
				templateDto.setTemplateDirectoryDto(directory);
				
				final TemplateTypeDto templateType = new TemplateTypeDto();
				templateType.setTemplateTypeName("TEXT_FREEMARKER");
				templateDto.setTemplateType(templateType);
				
				final AccountDto accountDto = new AccountDto();
				accountDto.setAccountId((Integer)SessionHelper.loadAttribute("accountId"));
				templateDto.setAccountDto(accountDto);
				templateService.create(templateDto);
				Notification.show("Template Creation","Template created successfully",Notification.Type.TRAY_NOTIFICATION);
			} 
			catch (final EntityAlreadyFoundException e) {
				LOGGER.info("");
				Notification.show("not crearedd","",Notification.Type.TRAY_NOTIFICATION);
			} 
			catch (final EntityNotCreatedException e) {
				LOGGER.info("");
				Notification.show("not crearedd","",Notification.Type.TRAY_NOTIFICATION);
			}
		}
		else {
			try {
				templateDto.setTemplateName(templateForm.getTemplateNameTxtFld().getValue());
				templateDto.setTemplatePath(templateForm.getTemplatePathTxtFld().getValue());
				templateDto.setTemplateText(templateForm.getEditor().getValue());
				templateDto.setGlobal(templateForm.getIsGlobal().getValue());
				
				templateService.updateTemplate(templateDto);
				Notification.show("Template Edit","Template updated successfully",Notification.Type.TRAY_NOTIFICATION);
			} 
			catch (final EntityAlreadyFoundException e) {
				Notification.show("Template Edit","Template not updated successfully",Notification.Type.TRAY_NOTIFICATION);
			}
		}
	}

}
