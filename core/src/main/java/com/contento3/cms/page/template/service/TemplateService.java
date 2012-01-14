package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.service.Service;

public interface TemplateService extends Service<TemplateDto>{

	Collection<TemplateDto> findTemplateByDirectoryName(String directoryName);

	TemplateDto findTemplateById(Integer templateId);

	void updateTemplate(TemplateDto templateDto);

	TemplateDto findTemplateByPathAndAccount(String templatePath,Integer accountId) throws Exception;

}
