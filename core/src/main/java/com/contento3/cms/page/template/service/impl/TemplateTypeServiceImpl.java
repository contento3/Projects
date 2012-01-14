package com.contento3.cms.page.template.service.impl;
import com.contento3.cms.page.template.dao.TemplateTypeDao;
import com.contento3.cms.page.template.dto.TemplateTypeDto;
import com.contento3.cms.page.template.model.TemplateType;
import com.contento3.cms.page.template.service.TemplateTypeAssembler;
import com.contento3.cms.page.template.service.TemplateTypeService;


/**
 * This class is used to //TODO
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

public class TemplateTypeServiceImpl implements TemplateTypeService {

	private TemplateTypeDao templateTypeDao;
	private TemplateTypeAssembler templateTypeAssembler;

	public TemplateTypeServiceImpl(final TemplateTypeAssembler templateTypeAssembler,final TemplateTypeDao templateTypeDao){
		this.templateTypeDao = templateTypeDao;
		this.templateTypeAssembler = templateTypeAssembler;
	}
	
	@Override
	public void create(TemplateTypeDto type) {
		// TODO Auto-generated method stub
		
	}

	public TemplateTypeDto findById(Integer templateTypeId){
		TemplateType templateType = templateTypeDao.findById(templateTypeId);
		return templateTypeAssembler.domainToDto(templateType);
	}

	
}
