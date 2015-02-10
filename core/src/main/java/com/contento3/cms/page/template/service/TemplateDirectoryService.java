package com.contento3.cms.page.template.service;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.service.StorableService;

public interface TemplateDirectoryService extends StorableService <TemplateDirectoryDto>{

	Integer create(TemplateDirectoryDto type);

	TemplateDirectoryDto findById(Integer id);

	TemplateDirectoryDto findByName(String name,boolean isGlobal,Integer accountId) throws EntityNotFoundException;

	Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal,Integer accountId);

	Collection<TemplateDirectoryDto> findChildDirectories(Integer parentId,Integer accountId);

	Collection<TemplateDirectoryDto> findChildDirectories(Integer parentId);

	TemplateDirectoryDto findChildDirectory(Integer parentId,String childDirectoryNameToFind,Integer accountId) throws EntityNotFoundException;

	void update(TemplateDirectoryDto templateDirectoryDto);

	void move (Integer directoryToMoveId,Integer destinationParentId);
	
	void delete (Integer id) throws EntityCannotBeDeletedException;
}
