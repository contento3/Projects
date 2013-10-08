package com.contento3.template.page.service;

import org.apache.commons.lang.Validate;

import com.contento3.template.page.dto.PageTemplateDto;


public class PageTemplateServiceImpl implements PageTemplateService {

	public PageTemplateDto findById(Integer id){
		Validate.notNull(id,"id cannot be null");
		return null;
	}
	
	public PageTemplateDto findByPath(String path){
		Validate.notNull(path,"path cannot be null");
		return null;
	}
	
	
}
