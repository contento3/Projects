package com.contento3.cms.page.template.service.impl;
import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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
		Validate.notNull(templateTypeAssembler,"templateTypeAssembler cannot be null");
		Validate.notNull(templateTypeDao,"templateTypeDao cannot be null");
		
		this.templateTypeDao = templateTypeDao;
		this.templateTypeAssembler = templateTypeAssembler;
	}
	@RequiresPermissions("TEMPLATE_TYPE:ADD")
	@Override
	public Integer create(TemplateTypeDto type) {
		Validate.notNull(type,"type cannot be null");
		// TODO Auto-generated method stub
		return null;
	}
	@RequiresPermissions("TEMPLATE_TYPE:VIEW")
	public TemplateTypeDto findById(Integer templateTypeId){
		Validate.notNull(templateTypeId,"templateTypeId cannot be null");
		TemplateType templateType = templateTypeDao.findById(templateTypeId);
		return templateTypeAssembler.domainToDto(templateType);
	}
	@RequiresPermissions("TEMPLATE_TYPE:DELETE")
	@Override
	public void delete(TemplateTypeDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete ,"dtoToDelete cannot be null");
	}

	
}
