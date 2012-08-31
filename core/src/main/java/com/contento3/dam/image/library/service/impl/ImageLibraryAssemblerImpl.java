package com.contento3.dam.image.library.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.service.AccountAssembler;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.image.library.service.ImageLibraryAssembler;

public class ImageLibraryAssemblerImpl implements ImageLibraryAssembler {

	private AccountAssembler accountAssembler;
	public ImageLibraryAssemblerImpl(final AccountAssembler accountAssembler) {
		this.accountAssembler = accountAssembler;
	}
	/**
	 * Transform dto into domain
	 */
	@Override
	public ImageLibrary dtoToDomain(final ImageLibraryDto dto) {
		ImageLibrary domain = new ImageLibrary();
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		return domain;
	}

	/**
	 * Transform domain into dto
	 */
	@Override
	public ImageLibraryDto domainToDto(final ImageLibrary domain) {
		ImageLibraryDto dto = new ImageLibraryDto();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	}

	/**
	 * Transform domain collection into dto collection
	 */
	@Override
	public Collection<ImageLibraryDto> domainsToDtos(final Collection<ImageLibrary> domains) {
		
		Collection<ImageLibraryDto> dtos = new ArrayList<ImageLibraryDto>();
		for(ImageLibrary domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	/**
	 * Transform dto collection into domain collection
	 */
	@Override
	public Collection<ImageLibrary> dtosToDomains(final Collection<ImageLibraryDto> dtos) {
		Collection<ImageLibrary> domains = new ArrayList<ImageLibrary>();
		
		for(ImageLibraryDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
