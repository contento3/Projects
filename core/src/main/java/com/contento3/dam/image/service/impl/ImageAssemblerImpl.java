package com.contento3.dam.image.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.service.ImageLibraryAssembler;
import com.contento3.dam.image.model.Image;
import com.contento3.dam.image.service.ImageAssembler;

public class ImageAssemblerImpl implements ImageAssembler {

	private SiteAssembler siteAssembler;
	private ImageLibraryAssembler imageLibraryAssembler;
	
	public ImageAssemblerImpl(final SiteAssembler siteAssembler,final  ImageLibraryAssembler imageLibraryAssembler) {
		
		this.siteAssembler = siteAssembler;
		this.imageLibraryAssembler = imageLibraryAssembler;
	}

	@Override
	public Image dtoToDomain(final ImageDto dto) {
		Image domain = new Image();
		
		if (null!=dto.getId()){
			domain.setImageId(dto.getId());
		}
		domain.setImageUuid(dto.getImageUuid());
		domain.setAltText(dto.getAltText());
		domain.setImage(dto.getImage());
		domain.setName(dto.getName());
		domain.setSites(siteAssembler.dtosToDomains(dto.getSiteDto()));
		domain.setImageLibrary(imageLibraryAssembler.dtoToDomain(dto.getImageLibraryDto()));
		return domain;
	}

	@Override
	public ImageDto domainToDto(final Image domain) {
		ImageDto dto = new ImageDto();
		dto.setId(domain.getImageId());
		dto.setAltText(domain.getAltText());
		dto.setImage(domain.getImage());
		dto.setImageUuid(domain.getImageUuid());
		dto.setName(domain.getName());
		dto.setSiteDto(siteAssembler.domainsToDtos(domain.getSites()));
		dto.setImageLibraryDto(imageLibraryAssembler.domainToDto(domain.getImageLibrary()));
		return dto;
	}

	@Override
	public Collection<ImageDto> domainsToDtos(final Collection<Image> domains) {
		Collection <ImageDto> dtos = new ArrayList<ImageDto>();
		
		Iterator<Image> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		return dtos;
	}

	@Override
	public Collection<Image> dtosToDomains(final Collection<ImageDto> dtos) {
		Collection <Image> domains = new ArrayList<Image>();
		
		Iterator<Image> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return domains;
	}

}
