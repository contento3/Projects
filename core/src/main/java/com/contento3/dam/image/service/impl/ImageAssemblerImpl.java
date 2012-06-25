package com.contento3.dam.image.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.contento3.cms.site.structure.service.SiteAssembler;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.model.Image;
import com.contento3.dam.image.service.ImageAssembler;

public class ImageAssemblerImpl implements ImageAssembler {

	private SiteAssembler siteAssembler;
	
	
	public ImageAssemblerImpl(final SiteAssembler siteAssembler) {
		
		this.siteAssembler = siteAssembler;
	}

	@Override
	public Image dtoToDomain(ImageDto dto) {
		Image domain = new Image();
		
		if (null!=dto.getImageId()){
			domain.setImageId(dto.getImageId());
		}
		domain.setImageUuid(dto.getImageUuid());
		domain.setAltText(dto.getAltText());
		domain.setImage(dto.getImage());
		domain.setName(dto.getName());
		domain.setSites(siteAssembler.dtosToDomains(dto.getSiteDto()));
		return domain;
	}

	@Override
	public ImageDto domainToDto(Image domain) {
		ImageDto dto = new ImageDto();
		dto.setImageId(domain.getImageId());
		dto.setAltText(domain.getAltText());
		dto.setImage(domain.getImage());
		dto.setImageUuid(domain.getImageUuid());
		dto.setName(domain.getName());
		dto.setSiteDto(siteAssembler.domainsToDtos(domain.getSites()));
		return dto;
	}

	@Override
	public Collection<ImageDto> domainsToDtos(Collection<Image> domains) {
		Collection <ImageDto> dtos = new ArrayList<ImageDto>();
		
		Iterator<Image> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		return dtos;
	}

	@Override
	public Collection<Image> dtosToDomains(Collection<ImageDto> dtos) {
		Collection <Image> domains = new ArrayList<Image>();
		
		Iterator<Image> iterator = domains.iterator();
		while (iterator.hasNext()){
			dtos.add(domainToDto(iterator.next()));
		}
		
		return domains;
	}

}
