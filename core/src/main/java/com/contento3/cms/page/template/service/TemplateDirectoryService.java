package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.common.service.Service;

public interface TemplateDirectoryService extends Service <TemplateDirectoryDto>{

	Integer create(TemplateDirectoryDto type);

	TemplateDirectoryDto findById(Integer id);

	TemplateDirectoryDto findByName(String name,boolean isGlobal);

	Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal);

	Collection<TemplateDirectoryDto> findChildDirectories(Integer parentId);

	void update(TemplateDirectoryDto templateDirectoryDto);

}
