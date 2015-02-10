package com.contento3.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dto.ModuleCategoryDto;
import com.contento3.account.dto.ModuleDto;
import com.contento3.account.model.Module;
import com.contento3.account.model.ModuleCategory;
import com.contento3.account.service.ModuleAssembler;
import com.contento3.account.service.ModuleCategoryAssembler;

public class ModuleAssemblerImpl implements ModuleAssembler {

	private ModuleCategoryAssembler moduleCategoryAssembler;
	
	public ModuleAssemblerImpl (final ModuleCategoryAssembler moduleCategoryAssembler){
		this.moduleCategoryAssembler = moduleCategoryAssembler;
	}
	
	@Override
	public Module dtoToDomain(final ModuleDto dto, final Module domain) {
		domain.setDescription(dto.getDescription());
		domain.setIconName(dto.getIconName());
		domain.setEnabled(dto.isEnabled());
		domain.setListenerClass(dto.getListenerClass());
		domain.setModuleId(dto.getModuleId());
		domain.setModuleName(dto.getModuleName());
		domain.setUuid(dto.getUuid());
		domain.setCategory(moduleCategoryAssembler.dtoToDomain(dto.getCategory(), new ModuleCategory()));
		return domain;
	}

	@Override
	public ModuleDto domainToDto(final Module domain, final ModuleDto dto) {
		dto.setDescription(domain.getDescription());
		dto.setIconName(domain.getIconName());
		dto.setEnabled(domain.isEnabled());
		dto.setListenerClass(domain.getListenerClass());
		dto.setModuleId(domain.getModuleId());
		dto.setModuleName(domain.getModuleName());
		dto.setUuid(domain.getUuid());
		dto.setCategory(moduleCategoryAssembler.domainToDto(domain.getCategory(), new ModuleCategoryDto()));
		return dto;
	}

	@Override
	public Collection<ModuleDto> domainsToDtos(final Collection<Module> domains) {
		final Collection<ModuleDto> dtos = new ArrayList<ModuleDto>();
		for (Module module : domains){
			dtos.add(domainToDto(module,new ModuleDto()));
		}
		return dtos;
	}

	@Override
	public Collection<Module> dtosToDomains(final Collection<ModuleDto> dtos) {
		final Collection<Module> domains = new ArrayList<Module>();
		for (ModuleDto moduleDto : dtos){
			domains.add(dtoToDomain(moduleDto,new Module()));
		}
		return domains;
	}

}
