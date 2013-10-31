package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
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

	void updateTemplate(TemplateDto templateDto) throws EntityAlreadyFoundException;

	TemplateDto findTemplateByPathAndAccount(String templatePath,Integer accountId) throws Exception;

	TemplateDto findTemplateByPathAndSiteId(String templatePath,Integer siteId) throws Exception;

}
