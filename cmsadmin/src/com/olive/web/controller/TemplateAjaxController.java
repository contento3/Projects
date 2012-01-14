package com.olive.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.olive.cms.page.template.dto.TemplateDirectoryDto;
import com.olive.cms.page.template.dto.TemplateDto;
import com.olive.cms.page.template.dto.TemplateTypeDto;
import com.olive.cms.page.template.service.TemplateService;
import com.olive.common.exception.EnitiyAlreadyFoundException;

@Controller
public class TemplateAjaxController {
	  
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
    		@RequestParam (value="templateId",required=false) String templateId,
    		@RequestParam (value="directoryId",required=false) String directoryId,
    		@RequestParam (value="templateTypeId",required=false) Integer templateTypeId,
    		@RequestParam (value="accountId",required=true) String accountId,
    		Model model) {
    
    	TemplateDto templateDto = new TemplateDto();
    	templateDto.setTemplateName(templateName);
    	templateDto.setTemplateText(text);
    	
    	TemplateTypeDto templateTypeDto = new TemplateTypeDto();
    	templateTypeDto.setTemplateTypeId(templateTypeId);
    	templateTypeDto.setTemplateTypeName("TEXT_FREEMARKER");
    	templateDto.setTemplateType(templateTypeDto);

    	if (null != templateId && null!=directoryId){
    		templateDto.setTemplateId(Integer.parseInt(templateId));
    		
        	TemplateDirectoryDto templateDirectoryDto = new TemplateDirectoryDto();
        	templateDirectoryDto.setId(Integer.parseInt(directoryId));
    		templateDto.setTemplateDirectoryDto(templateDirectoryDto);
        	templateService.updateTemplate(templateDto);
    	}
    	else {
        	try {//TODO
				templateService.create(templateDto);
			} catch (EnitiyAlreadyFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	    return "dfasdasdsad";
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
