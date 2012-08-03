package com.contento3.dam.image.library.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.image.library.service.ImageLibraryAssembler;

public class ImageLibraryAssemblerImpl implements ImageLibraryAssembler {

	@Override
	public ImageLibrary dtoToDomain(final ImageLibraryDto dto) {
		ImageLibrary domain = new ImageLibrary();
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		return domain;
	}

	@Override
	public ImageLibraryDto domainToDto(final ImageLibrary domain) {
		ImageLibraryDto dto = new ImageLibraryDto();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		return dto;
	}

	@Override
	public Collection<ImageLibraryDto> domainsToDtos(final Collection<ImageLibrary> domains) {
		
		Collection<ImageLibraryDto> dtos = new ArrayList<ImageLibraryDto>();
		for(ImageLibrary domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<ImageLibrary> dtosToDomains(final Collection<ImageLibraryDto> dtos) {
		Collection<ImageLibrary> domains = new ArrayList<ImageLibrary>();
		
		for(ImageLibraryDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
