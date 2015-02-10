package com.contento3.cms.page.template.service;


import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.exception.ResourceNotFoundException;
import com.contento3.common.service.SimpleService;

/**
 * Service layer for template.
 * @author HAMMAD
 *
 */
public interface TemplateService extends SimpleService<TemplateDto>{

	/**
	 * Finds the template by directoryId
	 * @param directoryId
	 * @return Collection<TemplateDto> 
	 */
	Collection<TemplateDto> findTemplateByDirectoryId(Integer directoryId);

	/**
	 * Finds the Template by templateId
	 * @param templateId
	 * @return TemplateDto
	 */
	TemplateDto findTemplateById(Integer templateId);

	void updateTemplate(TemplateDto templateDto) throws EntityAlreadyFoundException;

	TemplateDto findTemplateByPathAndAccount(String templatePath,Integer accountId) throws Exception;
	
	TemplateDto findTemplateByKeyAndAccount(String templateKey,Integer accountId) throws Exception;

	TemplateDto findTemplateByNameAndSiteId(String templateName,Integer siteId) throws Exception;

	/**
	 * Finds the SYSTEM template for the account. Each account can have separate template for each category of SYSTEM template.
	 * For e.g an account will define SYSTEM Template for error, SYSTEM Template for user registration etc.
	 * @param category Category/Type of template
	 * @param accountId AccountId of the organisation
	 * @return TemplateDto
	 * @throws EntityNotFoundException 
	 */
	TemplateDto findSystemTemplateForAccount(SystemTemplateNameEnum templateCategory, final Integer accountId) throws EntityNotFoundException;

	TemplateDto findTemplateByNameAndAccount(String templateName,
			Integer accountId) throws ResourceNotFoundException;

	TemplateDto findGlobalTemplateByKey(String templateKey) throws ResourceNotFoundException;

	void clearCache(Integer cache);

	/**
	 * Create template path based on template object passed.It gets the template directory from the template object that is passed.
	 * @param template
	 * @return
	 */
	String buildTemplatePath(TemplateDto templateDto);
}
