package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
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

	@RequiresPermissions("TEMPLATE:ADD")
	@Override
	public Integer create(final TemplateDirectoryDto dto) {
		Validate.notNull(dto,"dto cannot be null");
		return directoryDao.persist(assembler.dtoToDomain(dto));
	}
	
	@RequiresPermissions("TEMPLATE:VIEW")
	@Override
	public TemplateDirectoryDto findById(final Integer id) {
		Validate.notNull(id,"id cannot be null");
		return assembler.domainToDto(directoryDao.findById(id));
	}
	
	@RequiresPermissions("TEMPLATE:VIEW")
	@Override
	public Collection<TemplateDirectoryDto> findRootDirectories(boolean isGlobal,final Integer accountId) {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		return assembler.domainsToDtos(directoryDao.findRootDirectories(isGlobal,accountId));
	}
	
	@RequiresPermissions("TEMPLATE:VIEW")
	@Override
	public TemplateDirectoryDto findByName(String name, boolean isGlobal,final Integer accountId) {
		Validate.notNull(isGlobal ,"isGlobal cannot be null");
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(accountId,"name cannot be null");
		return assembler.domainToDto(directoryDao.findByName(name,isGlobal,accountId));
	}
	@RequiresPermissions("TEMPLATE:VIEW")
	public Collection<TemplateDirectoryDto> findChildDirectories(final Integer parentId,final Integer accountId){
		Validate.notNull(parentId,"parentId cannot be null");
		return assembler.domainsToDtos(directoryDao.findChildDirectories(parentId,accountId));
	}

	@RequiresPermissions("TEMPLATE:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final TemplateDirectoryDto templateDirectoryDto) {
		Validate.notNull(templateDirectoryDto,"templateDto cannot be null");
		directoryDao.update(assembler.dtoToDomain(templateDirectoryDto));
	}
	
	@RequiresPermissions("TEMPLATE:DELETE")
	@Override
	public void delete(TemplateDirectoryDto dtoToDelete) {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}

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

	@Override
	public void delete(final Integer id) {
		final TemplateDirectory directory = directoryDao.findById(id);
		directoryDao.delete(directory);
	}
}
