package com.contento3.cms.page.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.cms.page.template.service.TemplateDirectoryAssembler;

public class TemplateDirectoryAssemblerImpl implements TemplateDirectoryAssembler{

	private AccountAssembler accountAssembler;
	
	public TemplateDirectoryAssemblerImpl(final AccountAssembler accountAssembler) {
		this.accountAssembler = accountAssembler;
	}
	@Override
	public TemplateDirectory dtoToDomain(TemplateDirectoryDto dto) {
		TemplateDirectory domain = new TemplateDirectory();	
		domain.setId(dto.getId());
		domain.setGlobal(dto.isGlobal());
		
		if (dto.getParent()!=null){
			domain.setParent(dtoToDomain(dto.getParent()));
		}
		domain.setDirectoryName(dto.getDirectoryName());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
	}

	@Override
	public TemplateDirectoryDto domainToDto(TemplateDirectory domain) {
		TemplateDirectoryDto dto = new TemplateDirectoryDto();	
		dto.setId(domain.getId());
		dto.setGlobal(domain.isGlobal());
		
		if (domain.getParent()!=null){
			dto.setParent(domainToDto(domain.getParent()));
		}
		
		dto.setDirectoryName(domain.getDirectoryName());
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	}

	@Override
	public Collection<TemplateDirectoryDto> domainsToDtos(
			Collection<TemplateDirectory> domains) {
		Collection <TemplateDirectoryDto> dtos = new ArrayList<TemplateDirectoryDto>();
		
		Iterator<TemplateDirectory> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return dtos;
	}

	@Override
	public Collection<TemplateDirectory> dtosToDomains(
			Collection<TemplateDirectoryDto> dtos) {
		Collection <TemplateDirectory> domains = new ArrayList<TemplateDirectory>();
		
		Iterator<TemplateDirectoryDto> iterator = dtos.iterator();
		while (iterator.hasNext()){
			domains.add(dtoToDomain(iterator.next()));
		}
		
		return domains;
	}

}
