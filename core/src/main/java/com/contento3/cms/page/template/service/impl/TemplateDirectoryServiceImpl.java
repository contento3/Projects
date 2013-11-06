package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateDirectoryAssembler;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.common.exception.EntityAlreadyFoundException;

public class TemplateDirectoryServiceImpl 
			implements TemplateDirectoryService {

	private TemplateDirectoryAssembler assembler;
	private TemplateDirectoryDao directoryDao;

	public TemplateDirectoryServiceImpl(final TemplateDirectoryAssembler assembler,final TemplateDirectoryDao directoryDao){
		Validate.notNull(assembler,"assembler cannot be null");
		Validate.notNull(directoryDao,"directoryDao cannot be null");
		
		this.assembler=assembler;
		this.directoryDao = directoryDao;
	}
	
	@Override
	public Integer create(final TemplateDirectoryDto dto) {
		Validate.notNull(dto,"dto cannot be null");
		return directoryDao.persist(assembler.dtoToDomain(dto));
	}

	@Override
	public TemplateDirectoryDto findById(final Integer id) {
		Validate.notNull(id,"id cannot be null");
		return assembler.domainToDto(directoryDao.findById(id));
	}

	@Override
	public Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal) {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		return assembler.domainsToDtos(directoryDao.findRootDirectories(isGlobal));
	}

	@Override
	public TemplateDirectoryDto findByName(String name, boolean isGlobal) {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		Validate.notNull(name,"name cannot be null");
		return assembler.domainToDto(directoryDao.findByName(name,isGlobal));
	}

	public Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId){
		Validate.notNull(parentId,"parentId cannot be null");
		return assembler.domainsToDtos(directoryDao.findChildDirectories(parentId));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final TemplateDirectoryDto templateDirectoryDto) {
		Validate.notNull(templateDirectoryDto,"templateDto cannot be null");
		directoryDao.update(assembler.dtoToDomain(templateDirectoryDto));
	}

	@Override
	public void delete(TemplateDirectoryDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}
}
