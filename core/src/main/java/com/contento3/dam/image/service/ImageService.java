package com.contento3.dam.image.service;

import com.contento3.common.service.Service;
import com.contento3.dam.image.dto.ImageDto;

public interface ImageService extends Service<ImageDto> {

	
	ImageDto findImageById(String imageId);
	
}
