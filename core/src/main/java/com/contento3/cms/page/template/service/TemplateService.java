package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.service.Service;

/**
 * Service layer for template.
 * @author HAMMAD
 *
 */
public interface TemplateService extends Service<TemplateDto>{

	/**
	 * Finds the template by directoryName
	 * @param directoryName
	 * @return Collection<TemplateDto> 
	 */
	Collection<TemplateDto> findTemplateByDirectoryName(String directoryName);

	/**
	 * Finds the Template by templateId
	 * @param templateId
	 * @return TemplateDto
	 */
	TemplateDto findTemplateById(Integer templateId);

	void updateTemplate(TemplateDto templateDto);

	TemplateDto findTemplateByPathAndAccount(String templatePath,Integer accountId) throws Exception;

	TemplateDto findTemplateByPathAndSiteId(String templatePath,Integer siteId) throws Exception;

	/**
	 * Finds the SYSTEM template for the account. Each account can have separate template for each category of SYSTEM template.
	 * For e.g an account will define SYSTEM Template for error, SYSTEM Template for user registration etc.
	 * @param category Category/Type of template
	 * @param accountId AccountId of the organisation
	 * @return TemplateDto
	 * @throws EntityNotFoundException 
	 */
	TemplateDto findSystemTemplateForAccount(SystemTemplateNameEnum templateCategory, final Integer accountId) throws EntityNotFoundException;

}