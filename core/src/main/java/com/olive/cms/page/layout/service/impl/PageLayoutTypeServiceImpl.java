package com.olive.cms.page.layout.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.olive.cms.page.layout.dao.PageLayoutTypeDAO;
import com.olive.cms.page.layout.dto.PageLayoutTypeDto;
import com.olive.cms.page.layout.model.PageLayoutType;
import com.olive.cms.page.layout.service.PageLayoutAssembler;
import com.olive.cms.page.layout.service.PageLayoutTypeAssembler;
import com.olive.cms.page.layout.service.PageLayoutTypeService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PageLayoutTypeServiceImpl implements PageLayoutTypeService {

	private PageLayoutTypeAssembler assembler;
	private PageLayoutTypeDAO layoutTypeDAO;
	
	PageLayoutTypeServiceImpl(final PageLayoutTypeDAO layoutTypeDAO,final PageLayoutTypeAssembler assembler){
		this.layoutTypeDAO = layoutTypeDAO;	
		this.assembler = assembler;
	}
	
	@Override
	public Collection<PageLayoutType> findAllPageLayoutType(){
		return layoutTypeDAO.findAll();	
	}

	@Override
	public PageLayoutTypeDto findByName(final String name){
		return assembler.domainToDto(layoutTypeDAO.findByName(name));
	}
	
	@Override
	public void create(final PageLayoutTypeDto dto){
		layoutTypeDAO.persist(assembler.dtoToDomain(dto));
	}

}
