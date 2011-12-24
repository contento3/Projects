package com.olive.cms.page.template.service;

import java.util.Collection;

import com.olive.cms.page.template.dto.TemplateDirectoryDto;
import com.olive.common.service.Service;

public interface TemplateDirectoryService extends Service <TemplateDirectoryDto>{

	void create(TemplateDirectoryDto type);

	TemplateDirectoryDto findById(Integer id);

	TemplateDirectoryDto findByName(String name,boolean isGlobal);

	Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal);

	Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId);

}
