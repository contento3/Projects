package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.cms.page.template.service.TemplateDirectoryAssembler;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.common.exception.EntityNotFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class TemplateDirectoryServiceImpl implements TemplateDirectoryService {

	private TemplateDirectoryAssembler assembler;
	private TemplateDirectoryDao directoryDao;
	private TemplateDao templateDao;
	public TemplateDirectoryServiceImpl(final TemplateDirectoryAssembler assembler,final TemplateDirectoryDao directoryDao){
		Validate.notNull(assembler,"assembler cannot be null");
		Validate.notNull(directoryDao,"directoryDao cannot be null");
		
		this.assembler=assembler;
		this.directoryDao = directoryDao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:ADD", "TEMPLATE_LIBRARY:ADD" }, logical = Logical.OR) 
	@Override
	public Integer create(final TemplateDirectoryDto dto) {
		Validate.notNull(dto,"dto cannot be null");
		return directoryDao.persist(assembler.dtoToDomain(dto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
	@Override
	public TemplateDirectoryDto findById(final Integer id) {
		Validate.notNull(id,"id cannot be null");
		return assembler.domainToDto(directoryDao.findById(id));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
	@Override
	public Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal,final Integer accountId) {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		return assembler.domainsToDtos(directoryDao.findRootDirectories(isGlobal,accountId));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
	@Override
	public TemplateDirectoryDto findByName(String name, boolean isGlobal,final Integer accountId) throws EntityNotFoundException {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(accountId,"name cannot be null");
		TemplateDirectory directory = directoryDao.findByName(name,isGlobal,accountId);
		
		if (directory==null){
			throw new EntityNotFoundException();
		}
		
		return assembler.domainToDto(directory);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
	public Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId,final Integer accountId){
		Validate.notNull(parentId,"parentId cannot be null");
		return assembler.domainsToDtos(directoryDao.findChildDirectories(parentId,accountId));
	}

	@RequiresPermissions(value = { "TEMPLATE:EDIT", "TEMPLATE_LIBRARY:EDIT" }, logical = Logical.OR) 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final TemplateDirectoryDto templateDirectoryDto) {
		Validate.notNull(templateDirectoryDto,"templateDto cannot be null");
		directoryDao.update(assembler.dtoToDomain(templateDirectoryDto));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:DELETE", "TEMPLATE_LIBRARY:DELETE" }, logical = Logical.OR) 
	@Override
	public void delete(TemplateDirectoryDto dtoToDelete) {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:MOVE", "TEMPLATE_LIBRARY:MOVE" }, logical = Logical.OR) 
	@Override
	public void move(final Integer directoryToMoveId,final Integer destinationParentId) {
		final TemplateDirectory itemToMove = directoryDao.findById(directoryToMoveId);
		final TemplateDirectory selectedParentItem = directoryDao.findById(destinationParentId);
		
		itemToMove.setParent(selectedParentItem);
		itemToMove.setGlobal(selectedParentItem.isGlobal());
		itemToMove.setAccount(selectedParentItem.getAccount());

		final Collection <Template> templates = templateDao.findTemplateByDirectoryId(directoryToMoveId);
		for (Template template: templates){
			template.setGlobal(itemToMove.isGlobal());
			template.setAccount(selectedParentItem.getAccount());
			templateDao.update(template);
		}
		
		directoryDao.update(itemToMove);
	}
	

	public void setTemplateDao(final TemplateDao templateDao){
		this.templateDao = templateDao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(final Integer id) {
		final TemplateDirectory directory = directoryDao.findById(id);
		directoryDao.delete(directory);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
	@Override
	public Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId) {
			Validate.notNull(parentId,"parentId cannot be null");
			return assembler.domainsToDtos(directoryDao.findChildDirectories(parentId));
	}

	@Override
	public TemplateDirectoryDto findChildDirectory(final Integer parentId,final String childDirectoryNameToFind,final Integer accountId) throws EntityNotFoundException {
		Validate.notNull(parentId,"parentId cannot be null");
		Validate.notNull(childDirectoryNameToFind,"childDirectoryNameToFind cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		
		final TemplateDirectory directory = directoryDao.findChildDirectory(parentId, childDirectoryNameToFind, accountId);
		
		TemplateDirectoryDto dto = null;
		if (directory==null){
			throw new EntityNotFoundException();
		}

		dto = assembler.domainToDto(directory);
		return dto;
	}
	
	
}
