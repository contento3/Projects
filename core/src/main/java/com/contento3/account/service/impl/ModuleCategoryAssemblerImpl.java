package com.contento3.account.service.impl;

import java.util.Collection;

import com.contento3.account.dto.ModuleCategoryDto;
import com.contento3.account.model.ModuleCategory;
import com.contento3.account.service.ModuleCategoryAssembler;

public class ModuleCategoryAssemblerImpl implements ModuleCategoryAssembler {

	@Override
	public ModuleCategory dtoToDomain(final ModuleCategoryDto dto,final ModuleCategory domain) {
		domain.setCategoryDescription(dto.getCategoryDescription());
		domain.setCategoryName(dto.getCategoryName());
		domain.setModuleCategoryId(dto.getModuleCategoryId());
		return domain;
	}

	@Override
	public ModuleCategoryDto domainToDto(final ModuleCategory domain,final ModuleCategoryDto dto) {
		dto.setCategoryDescription(domain.getCategoryDescription());
		dto.setCategoryName(domain.getCategoryName());
		dto.setModuleCategoryId(domain.getModuleCategoryId());
		return dto;
	}

	@Override
	public Collection<ModuleCategoryDto> domainsToDtos(final Collection<ModuleCategory> domains) {
		return null;
	}

	@Override
	public Collection<ModuleCategory> dtosToDomains(final Collection<ModuleCategoryDto> dtos) {
		return null;
	}

}
