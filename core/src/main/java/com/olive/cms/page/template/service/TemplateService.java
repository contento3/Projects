package com.olive.cms.page.template.service;

import java.util.Collection;

import com.olive.cms.page.template.dto.TemplateDto;
import com.olive.common.service.Service;

public interface TemplateService extends Service<TemplateDto>{

	Collection<TemplateDto> findTemplateByDirectoryName(String directoryName);

	TemplateDto findTemplateById(Integer templateId);

	void updateTemplate(TemplateDto templateDto);

}
