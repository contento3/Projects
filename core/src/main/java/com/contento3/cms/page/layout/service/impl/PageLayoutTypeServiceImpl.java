package com.contento3.cms.page.layout.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.layout.dao.PageLayoutTypeDAO;
import com.contento3.cms.page.layout.dto.PageLayoutTypeDto;
import com.contento3.cms.page.layout.model.PageLayoutType;
import com.contento3.cms.page.layout.service.PageLayoutTypeAssembler;
import com.contento3.cms.page.layout.service.PageLayoutTypeService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageLayoutTypeServiceImpl implements PageLayoutTypeService {

	private PageLayoutTypeAssembler assembler;
	private PageLayoutTypeDAO layoutTypeDAO;
	
	PageLayoutTypeServiceImpl(final PageLayoutTypeDAO layoutTypeDAO,final PageLayoutTypeAssembler assembler){
		Validate.notNull(layoutTypeDAO,"layoutTypeDAO cannot be null");
		Validate.notNull(assembler,"assembler cannot be null");
		this.layoutTypeDAO = layoutTypeDAO;	
		this.assembler = assembler;
	}
	
	@Override
	public Collection<PageLayoutType> findAllPageLayoutType(){
		return layoutTypeDAO.findAll();	
	}

	@Override
	public PageLayoutTypeDto findByName(final String name){
		Validate.notNull(name,"name cannot be null");
		return assembler.domainToDto(layoutTypeDAO.findByName(name));
	}
	
	@Override
	public Integer create(final PageLayoutTypeDto dto){
		Validate.notNull(dto,"dto cannot be null");
		return layoutTypeDAO.persist(assembler.dtoToDomain(dto));
	}

	@Override
	public void delete(PageLayoutTypeDto dtoToDelete) {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		// TODO Auto-generated method stub
		
	}

}
