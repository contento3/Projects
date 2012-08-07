package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.TemplateDirectoryAssembler;
import com.contento3.cms.page.template.service.TemplateDirectoryService;

public class TemplateDirectoryServiceImpl 
			implements TemplateDirectoryService {

	private TemplateDirectoryAssembler assembler;
	private TemplateDirectoryDao directoryDao;

	public TemplateDirectoryServiceImpl(final TemplateDirectoryAssembler assembler,final TemplateDirectoryDao directoryDao){
		this.assembler=assembler;
		this.directoryDao = directoryDao;
	}
	
	@Override
	public Integer create(final TemplateDirectoryDto dto) {
		return directoryDao.persist(assembler.dtoToDomain(dto));
	}

	@Override
	public TemplateDirectoryDto findById(final Integer id) {
		return assembler.domainToDto(directoryDao.findById(id));
	}

	@Override
	public Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal) {
		return assembler.domainsToDtos(directoryDao.findRootDirectories(isGlobal));
	}

	@Override
	public TemplateDirectoryDto findByName(String name, boolean isGlobal) {
		return assembler.domainToDto(directoryDao.findByName(name,isGlobal));
	}

	public Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId){
		return assembler.domainsToDtos(directoryDao.findChildDirectories(parentId));
	}
}
