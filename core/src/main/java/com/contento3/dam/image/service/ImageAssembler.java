package com.contento3.dam.image.service;

import java.util.Collection;

import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.model.Image;

public interface ImageAssembler {

	Image dtoToDomain(ImageDto dto);

	ImageDto domainToDto(Image domain);

	Collection<ImageDto> domainsToDtos(Collection<Image> domains);

	Collection<Image> dtosToDomains(Collection<ImageDto> dtos);

	
}
