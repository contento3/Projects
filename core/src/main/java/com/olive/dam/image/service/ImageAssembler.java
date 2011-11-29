package com.olive.dam.image.service;

import java.util.Collection;

import com.olive.dam.image.dto.ImageDto;
import com.olive.dam.image.model.Image;

public interface ImageAssembler {

	Image dtoToDomain(ImageDto dto);

	ImageDto domainToDto(Image domain);

	Collection<ImageDto> domainsToDtos(Collection<Image> domains);

	Collection<Image> dtosToDomains(Collection<ImageDto> dtos);

	
}
