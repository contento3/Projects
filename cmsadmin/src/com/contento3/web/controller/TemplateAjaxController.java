package com.contento3.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;

@Controller
public class TemplateAjaxController {

	private static final Logger LOGGER = Logger.getLogger(TemplateAjaxController.class);

	private final TemplateService templateService;
	
	@Autowired
    public TemplateAjaxController(TemplateService templateService) {
        this.templateService = templateService;
    }
	
   /**
    * Handles and retrieves the AJAX Add page
    */
    @RequestMapping(value = "/jsp/addTemplate.ajax", method = RequestMethod.POST)
    public @ResponseBody String addTemplate(@RequestParam (value="text",required=true) String text,
    		@RequestParam (value="templateName",required=true) String templateName,
    		@RequestParam (value="templateId",required=false) Integer templateId,
    		@RequestParam (value="directoryId",required=true) Integer directoryId,
    		@RequestParam (value="templateTypeId",required=false) Integer templateTypeId,
    		@RequestParam (value="accountId",required=true) Integer accountId,
    		Model model) {
    
    	TemplateDto templateDto = new TemplateDto();
    	templateDto.setTemplateName(templateName);
    	templateDto.setTemplateText(text);

    	TemplateTypeDto templateTypeDto = new TemplateTypeDto();
    	templateTypeDto.setTemplateTypeId(templateTypeId);
    	templateTypeDto.setTemplateTypeName("TEXT_FREEMARKER");
    	templateDto.setTemplateType(templateTypeDto);

    	TemplateDirectoryDto templateDirectoryDto = new TemplateDirectoryDto();
    	templateDirectoryDto.setId(directoryId);
		templateDto.setTemplateDirectoryDto(templateDirectoryDto);

		AccountDto accountDto = new AccountDto();
		accountDto.setAccountId(accountId);
		templateDto.setAccountDto(accountDto);
		
    	if (null != templateId){
    		templateDto.setTemplateId(templateId);
        	templateService.updateTemplate(templateDto);
    	}
    	else {
        	try {
				templateService.create(templateDto);
			} catch (EntityAlreadyFoundException e) {
				LOGGER.error(String.format("Error occured. Template with name [%s] cannot be created ", templateDto.getTemplateName()),e);
			}
			catch (EntityNotCreatedException e) {
				e.printStackTrace();
			}
    	}
	    return "TEMPLATE_CREATED";
    	}


    /**
     * Handles and retrieves the AJAX Add page
     */
     @RequestMapping(value = "/jsp/loadTemplate.ajax", method = RequestMethod.GET)
     public @ResponseBody String loadTemplate(@RequestParam (value="templateId",required=true) Integer templateId,Model model) {
    	    
      	if (null != templateId) {
     		TemplateDto templateDto = templateService.findTemplateById(templateId);
    	 		return templateDto.getTemplateText();
   	 	}
		return "";
   	}
}
